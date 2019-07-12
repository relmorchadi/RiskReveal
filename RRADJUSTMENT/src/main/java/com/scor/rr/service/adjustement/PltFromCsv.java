package com.scor.rr.service.adjustement;

public class PltFromCsv {



//    public List<ErrorExcelFile> checkFileExcel(String fileName) {
//        List<ErrorExcelFile> cellErrors = new ArrayList<>();
//        Voof.fromFile(fileName, PLTLossDataExcelModel.class, (o, errors) -> {
//            errors.forEach(t -> cellErrors.add(new ErrorExcelFile(o.getRowIndex()-1, Integer.valueOf(t.getIndex()), t.getCause(), t.getValue())));
//        });
//        return cellErrors;
//    }
//
//    public void saveExcelFile(String fileName, PLTLossDataExcelModel natCatMetaEstimate) {
//        Voof.fromFile(fileName, PLTLossDataExcelModel.class, (o, errors) -> {
//            PLTLossData pltLossData = new PLTLossData();
//            mapper.map(o, pltLossData);
//        });
//    }
//
//    public List<PLTLossData> getFileExcelRows(String fileName) {
//        List<PLTLossData> pltLossDataList = Collections.synchronizedList(new ArrayList<>());
//        Voof.fromFile(fileName, PLTLossDataExcelModel.class).parallelStream().forEach((e) -> {
//            PLTLossData pltLossData = new PLTLossData();
//            mapper = new DozerBeanMapper();
//            mapper.map(e, pltLossData);
//            pltLossDataList.add(pltLossData);
//        });
//        return pltLossDataList;
//    }

}
