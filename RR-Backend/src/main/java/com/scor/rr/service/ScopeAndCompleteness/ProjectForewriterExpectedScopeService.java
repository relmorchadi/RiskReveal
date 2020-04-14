package com.scor.rr.service.ScopeAndCompleteness;

import com.scor.rr.domain.entities.ScopeAndCompleteness.ForeWriterExpectedScope;
import com.scor.rr.domain.entities.ScopeAndCompleteness.ProjectForewriterExpectedScope;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.ScopeAndCompleteness.ExpectedScopeFileCorruptedException;
import com.scor.rr.exceptions.ScopeAndCompleteness.ExpectedScopeFileNotFoundException;
import com.scor.rr.exceptions.ScopeAndCompleteness.ExpectedScopeFileNotSupportedException;
import com.scor.rr.repository.ScopeAndCompleteness.ProjectForewriterExpectedScopeRepository;
import org.apache.commons.io.FilenameUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

@Service
public class ProjectForewriterExpectedScopeService {

    @Autowired
    private ProjectForewriterExpectedScopeRepository projectForewriterExpectedScopeRepository;

    private ModelMapper modelMapper = new ModelMapper();

    //@Value(value = "${application.expectedScope.path}")
    private String path;
   // @Value(value = "${application.expectedScope.separator}")
    private String separator;

    public List<String> getFileNames() {
        File file = new File(path);
        List<String> fileNames = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(file.listFiles())) {
            System.out.println(fileEntry.getName());
            fileNames.add(fileEntry.getName());
        }

        return fileNames;
    }

    public List<ForeWriterExpectedScope> readForewriterFile(String fileName) throws RRException {
        File file = new File(path + separator + fileName);
        if (!file.exists())
            throw new ExpectedScopeFileNotFoundException();
        if (!"txt".equalsIgnoreCase(FilenameUtils.getExtension(file.getName())))
            throw new ExpectedScopeFileNotSupportedException();

        try {
            List<ForeWriterExpectedScope> listOfExpectedScopes = new ArrayList<>();
            Scanner sc = new Scanner(new FileReader(file));
            sc.useDelimiter("\\r\\n|\\t|\\n");
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
            while (sc.hasNext()) {

                String accntNum = String.valueOf(sc.next()); //AccountNumber
                if (!accntNum.equals("")) {
                    int year = Integer.parseInt(sc.next()); //Year
                    int order = Integer.parseInt(sc.next()); // Order
                    int endorNum = Integer.parseInt(sc.next()); // Endor_Num
                    int division = Integer.parseInt(sc.next()); //Division
                    String country = String.valueOf(sc.next()); //Country
                    String state = String.valueOf(sc.next()); //State
                    double tiv = Double.parseDouble(sc.next()); //TIV
                    String analysisName = String.valueOf(sc.next()); //Analysis_Name
                    String currency = String.valueOf(sc.next()); //CURR
                    String perils = String.valueOf(sc.next()); // Perils


                    String[] chunkedState = state.split("-");
                    String finalState = "";
                    if (chunkedState.length == 1) {
                        chunkedState = state.split("_");
                        if (chunkedState.length == 1) {
                            finalState = state;
                        } else {
                            finalState = chunkedState[1];
                        }
                    } else {
                        finalState = chunkedState[1];
                    }

                    listOfExpectedScopes.add(new ForeWriterExpectedScope(accntNum,
                            year,
                            endorNum,
                            order,
                            analysisName,
                            division,
                            country,
                            finalState,
                            perils,
                            tiv,
                            currency));
                }
            }

            return listOfExpectedScopes;
        } catch (IOException | NoSuchElementException e) {
            throw new ExpectedScopeFileCorruptedException();
        } catch (NumberFormatException e) {
            throw new NumberFormatException(e.getMessage());
        }


    }

    public void storeTheExpectedScopeFac(String carName) throws RRException {

        List<String> namesOfFiles = getFileNames();
        if (!namesOfFiles.isEmpty()) {
            for (String fileName : namesOfFiles) {
                if (fileName.split("_")[10].equals(carName)) {
                    List<ForeWriterExpectedScope> listScope = readForewriterFile(fileName);
                    Long projectID = getProjectId(fileName.split("_")[10]);
                    List<ProjectForewriterExpectedScope> finalList = new ArrayList<>();
                    if (!listScope.isEmpty()) {
                        for (ForeWriterExpectedScope scope : listScope) {
                            ProjectForewriterExpectedScope projectForewriterExpectedScope = modelMapper.map(scope, ProjectForewriterExpectedScope.class);
                            projectForewriterExpectedScope.setEntity(1);
                            projectForewriterExpectedScope.setProjectId(projectID);
                            finalList.add(projectForewriterExpectedScope);
                        }
                        projectForewriterExpectedScopeRepository.saveAll(finalList);
                    }
                }

            }

        }


    }

    public Long getProjectId(String carId) {
        return projectForewriterExpectedScopeRepository.getProjectIdByCarRequestId(carId);
    }
}
