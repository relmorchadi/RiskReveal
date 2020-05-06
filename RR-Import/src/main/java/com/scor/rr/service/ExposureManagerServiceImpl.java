package com.scor.rr.service;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.ProjectConfigurationForeWriter;
import com.scor.rr.domain.dto.ExposureManagerData;
import com.scor.rr.domain.dto.ExposureManagerDto;
import com.scor.rr.domain.dto.ExposureManagerParamsDto;
import com.scor.rr.domain.dto.ExposureManagerRefDto;
import com.scor.rr.repository.*;
import com.scor.rr.service.abstraction.DivisionService;
import com.scor.rr.service.abstraction.ExposureManagerService;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.*;

import static java.util.stream.Collectors.toMap;


/**
 * @author Ayman IKAR
 * @created 24/02/2020
 */

@Service
public class ExposureManagerServiceImpl implements ExposureManagerService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Autowired
    private FinancialPerspectiveRepository financialPerspectiveRepository;

    @Autowired
    private ProjectConfigurationForeWriterDivisionRepository projectConfigurationForeWriterDivisionRepository;

    @Autowired
    private ProjectConfigurationForeWriterRepository projectConfigurationForeWriterRepository;

    @Autowired
    private ExposureViewDefinitionRepository exposureViewDefinitionRepository;

    @Autowired
    private ExposureSummaryDataRepository exposureSummaryDataRepository;

    @Autowired
    private DivisionService divisionService;

    @Autowired
    private ModelPortfolioRepository modelPortfolioRepository;


    @Override
    public ExposureManagerRefDto getRefForExposureManager(Long projectId) {
        ExposureManagerRefDto exposureManagerRefDto = new ExposureManagerRefDto();

        exposureManagerRefDto.setCurrencies(Arrays.asList("USD", "CAD", "GBP", "EUR", "SGD"));
        exposureManagerRefDto.setFinancialPerspectives(financialPerspectiveRepository.findSelectableCodes());
        ProjectConfigurationForeWriter pcfw = projectConfigurationForeWriterRepository.findByProjectId(projectId);
        exposureManagerRefDto.setDivisions(divisionService.getDivisions(pcfw != null ? pcfw.getCaRequestId() : null));
        exposureManagerRefDto.setSummariesDefinitions(exposureViewDefinitionRepository.findExposureViewDefinitionsAliases());
        exposureManagerRefDto.setPortfolios(modelPortfolioRepository.findPortfolioNamesByProjectId(projectId));

        List<Map<String, Object>> portfolioAndCurrencyByDivision = modelPortfolioRepository.findPortfolioNamesAndCurrencyAndDivisionByProjectId(projectId);
        List<Integer> divisions = modelPortfolioRepository.getDivisionsInProject(projectId);
        Map<Integer, Map<String, String>> portfoliosAndCurrenciesByDivision = new HashMap<>();

        for (Integer division : divisions) {
            Map<String, String> portfolioCurrency = new HashMap<>();
            portfolioAndCurrencyByDivision.stream().filter(e -> e.get("DivisionNumber").equals(division))
                    .forEach(fe -> {
                        portfolioCurrency.put((String) fe.get("ModelPortfolioName"), (String) fe.get("Currency"));
                        portfoliosAndCurrenciesByDivision.put(division, portfolioCurrency);
                    });
        }

        exposureManagerRefDto.setPortfoliosAndCurrenciesByDivision(portfoliosAndCurrenciesByDivision);
        return exposureManagerRefDto;
    }

    @Override
    public ExposureManagerDto getExposureManagerData(ExposureManagerParamsDto params) {

        ExposureManagerDto exposureManagerDto = new ExposureManagerDto();
        List<ExposureManagerData> data = new ArrayList<>();

        List<Map<String, Object>> values = exposureSummaryDataRepository.getExposureData(params.getProjectId(),
                params.getPortfolioName(), params.getSummaryType(), params.getDivision(), params.getCurrency(), params.getFinancialPerspective(),
                params.getPage(), params.getPageSize(), params.getRegionPerilFilter(), true, params.getType());

        if (params.getRequestTotalRow()) {
            Map<String, Object> totalRow = exposureSummaryDataRepository.getTotalRowExposureData(params.getProjectId(),
                    params.getPortfolioName(), params.getSummaryType(), params.getDivision(), params.getCurrency(), params.getFinancialPerspective(), params.getType());

            ExposureManagerData exposureManagerData = new ExposureManagerData();

            exposureManagerData.setAdmin1("TOTAL");
            exposureManagerData.setCountry("TOTAL");
            exposureManagerData.setExpectedTiv((BigDecimal) totalRow.get("expectedTIV"));
            exposureManagerData.setTotalTiv((BigDecimal) totalRow.get("Unmapped"));
            exposureManagerData.setTivDiff(exposureManagerData.getExpectedTiv() != null ?
                    exposureManagerData.getExpectedTiv().subtract(exposureManagerData.getTotalTiv()) : null);
            Map<String, Object> map = new HashMap<>(totalRow);
            map.remove("Unmapped");
            map.values().removeAll(Collections.singleton(null));
            exposureManagerData.setRegionPerils(map);

            exposureManagerDto.setFrozenRow(exposureManagerData);
            map = map
                    .entrySet()
                    .stream()
                    .sorted(this.valueComparator().reversed())
                    .collect(
                            toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                    LinkedHashMap::new));
            exposureManagerDto.setColumns(new ArrayList<>(map.keySet()));
        }

        if (values != null && !values.isEmpty()) {
            for (Map<String, Object> entry : values) {
                ExposureManagerData exposureManagerData = new ExposureManagerData();

                exposureManagerData.setTotalTiv((BigDecimal) entry.get("Unmapped"));
                exposureManagerData.setCountry((String) entry.get("CountryCode"));
                exposureManagerData.setAdmin1((String) entry.get("Admin1Code"));
                exposureManagerData.setExpectedTiv((BigDecimal) entry.get("expectedTIV"));
                exposureManagerData.setTivDiff(exposureManagerData.getExpectedTiv() != null ?
                        exposureManagerData.getExpectedTiv().subtract(exposureManagerData.getTotalTiv()) : null);

                Map<String, Object> map = new HashMap<>(entry);
                map.remove("Unmapped");
                map.remove("expectedTIV");
                map.remove("CountryCode");
                map.remove("Admin1Code");

                if (exposureManagerDto.getColumns() == null) {
                    exposureManagerDto.setColumns(new ArrayList<>(map.keySet()));
                }

                map.values().removeAll(Collections.singleton(null));
                exposureManagerData.setRegionPerils(map);

                data.add(exposureManagerData);
            }

            exposureManagerDto.setData(data);
        }
        return exposureManagerDto;
    }

    @Override
    public byte[] exportExposureManagerData(ExposureManagerParamsDto params) {

        String userName = null;
        if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null)
            userName = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getLastName() + " " +
                    ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser().getFirstName();

        List<Map<String, Object>> values = exposureSummaryDataRepository.getExposureData(params.getProjectId(),
                params.getPortfolioName(), params.getSummaryType(), params.getDivision(), params.getCurrency(), params.getFinancialPerspective(),
                params.getPage(), params.getPageSize(), params.getRegionPerilFilter(), false, params.getType());

        Map<String, Object> totalRow = exposureSummaryDataRepository.getTotalRowExposureData(params.getProjectId(),
                params.getPortfolioName(), params.getSummaryType(), params.getDivision(), params.getCurrency(), params.getFinancialPerspective(), params.getType());

        totalRow = totalRow
                .entrySet()
                .stream()
                .sorted(this.valueComparator().reversed())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

        if (values != null && !values.isEmpty()) {
            try {

                Map<Integer, String> fixedValues = new HashMap<>();
                int index = 0;

                XSSFWorkbook wb = new XSSFWorkbook();

                XSSFSheet sheet = wb.createSheet("Parameters");

                int rowCount = 0;
                int columnCount = 0;

                XSSFRow row = sheet.createRow(rowCount++);
                sheet.setDefaultColumnWidth(20);

                Cell cell = row.createCell(columnCount++);
                cell.setCellValue("Project Id");

                cell = row.createCell(columnCount++);
                cell.setCellValue(params.getProjectId());
                columnCount = 0;


                row = sheet.createRow(rowCount++);
                cell = row.createCell(columnCount++);
                cell.setCellValue("Portfolio Name");

                cell = row.createCell(columnCount++);
                cell.setCellValue(params.getPortfolioName());
                columnCount = 0;


                row = sheet.createRow(rowCount++);
                cell = row.createCell(columnCount++);
                cell.setCellValue("Division");

                cell = row.createCell(columnCount++);
                cell.setCellValue(params.getDivision());
                columnCount = 0;


                row = sheet.createRow(rowCount++);
                cell = row.createCell(columnCount++);
                cell.setCellValue("Currency");

                cell = row.createCell(columnCount++);
                cell.setCellValue(params.getCurrency());
                columnCount = 0;


                row = sheet.createRow(rowCount++);
                cell = row.createCell(columnCount++);
                cell.setCellValue("Summary Definition");

                cell = row.createCell(columnCount++);
                cell.setCellValue(params.getSummaryType());
                columnCount = 0;


                row = sheet.createRow(rowCount++);
                cell = row.createCell(columnCount++);
                cell.setCellValue("Financial Perspective");

                cell = row.createCell(columnCount++);
                cell.setCellValue(params.getFinancialPerspective());
                columnCount = 0;


                row = sheet.createRow(rowCount++);
                cell = row.createCell(columnCount++);
                cell.setCellValue("Exported By");

                cell = row.createCell(columnCount++);
                cell.setCellValue(userName);
                columnCount = 0;

                /**********************/

                sheet = wb.createSheet("Data");
                sheet.setDefaultColumnWidth(20);

                sheet.createFreezePane(0, 2);

                rowCount = 0;

                XSSFRow firstDataRow = sheet.createRow(rowCount++);

                XSSFCell dataCell = firstDataRow.createCell(columnCount++);
                dataCell.setCellValue("CountryCode");
                fixedValues.put(index++, "CountryCode");

                dataCell = firstDataRow.createCell(columnCount++);
                dataCell.setCellValue("Admin1Code");
                fixedValues.put(index++, "Admin1Code");

                dataCell = firstDataRow.createCell(columnCount++);
                dataCell.setCellValue("Total");
                fixedValues.put(index++, "Unmapped");

                dataCell = firstDataRow.createCell(columnCount++);
                dataCell.setCellValue("Expected");
                fixedValues.put(index++, "expectedTIV");


                for (String fieldName : totalRow.keySet()) {
                    if (!fixedValues.containsValue(fieldName) && !fieldName.equalsIgnoreCase("Unmapped")) {
                        dataCell = firstDataRow.createCell(columnCount++);
                        dataCell.setCellValue(fieldName);
                        fixedValues.put(index++, fieldName);
                    }
                }

                XSSFFont font = wb.createFont();
                font.setFontHeightInPoints((short) 10);
                font.setFontName("Arial");
                font.setColor(IndexedColors.WHITE.getIndex());
                font.setBold(true);
                font.setItalic(false);


                XSSFCellStyle style = wb.createCellStyle();
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setFont(font);

                for (int i = 0; i < firstDataRow.getLastCellNum(); i++) {
                    firstDataRow.getCell(i).setCellStyle(style);
                }

                values.add(0, totalRow);
                for (Map<String, Object> t : values) {
                    XSSFRow dataRow = sheet.createRow(rowCount++);
                    for (columnCount = 0; columnCount < fixedValues.size(); columnCount++) {
                        dataCell = dataRow.createCell(columnCount);
                        Object value = t.get(fixedValues.get(columnCount));
                        if (value != null) {
                            if (value instanceof String) {
                                dataCell.setCellValue((String) value);
                            } else if (value instanceof Long) {
                                dataCell.setCellValue((Long) value);
                            } else if (value instanceof BigDecimal) {
//                                dataCell.setCellValue(value.toString());
                                dataCell.setCellValue(((BigDecimal) value).doubleValue());
                            } else if (value instanceof Integer) {
                                dataCell.setCellValue((Integer) value);
                            } else if (value instanceof Double) {
                                dataCell.setCellValue((Double) value);
                            } else if (value instanceof Date) {
                                dataCell.setCellValue((Date) value);
                            }
                            dataCell.getCellStyle().setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
                        }
                    }
                }

                ByteArrayOutputStream bos = new ByteArrayOutputStream();

                wb.write(bos);
                bos.close();
                wb.close();

                return bos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    private Comparator<Map.Entry> valueComparator() {
        return Comparator.comparing(e -> ((BigDecimal) e.getValue()));
    }

    @Override
    public String exposureManagerDataExport(ExposureManagerParamsDto params) {

        return "ProjectID_" +
                params.getProjectId() +
                "_PortfolioName_" +
                params.getPortfolioName() +
                "_Division_" +
                params.getDivision() +
                "_Currency_" +
                params.getCurrency() +
                "_FinancialPerspective_" +
                params.getFinancialPerspective() +
                ".xlsx";
    }
}
