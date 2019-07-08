package com.scor.rr;

import java.io.IOException;

public class MainApplication {


//   static List<PLTLossData> getPltLossDataFromFile(String fileName){
//       PltFromCsv pltFromCsv = new PltFromCsv();
//       if(pltFromCsv.checkFileExcel(fileName).isEmpty()) {
//           return pltFromCsv.getFileExcelRows(fileName);
//       }
//       return null;
//   }
//
//   static List<PLTLossData> lineaireAdjustement(List<PLTLossData> pltLossData, double lmf, boolean cap) {
//       CalculAdjustement calculAdjustement = new CalculAdjustement();
//       return calculAdjustement.lineaireAdjustement(pltLossData, lmf, cap);
//   }
//
//    static List<PLTLossData> nonLineaireEventPeriodDrivenAdjustment(List<PLTLossData> pltLossData, boolean cap,List<PEATData> peatDatas) {
//        CalculAdjustement calculAdjustement = new CalculAdjustement();
//        return calculAdjustement.nonLineaireEventPeriodDrivenAdjustment(pltLossData, cap,peatDatas);
//    }
//    static List<PLTLossData> nonLineaireEventDrivenAdjustment(List<PLTLossData> pltLossData, boolean cap,List<PEATData> peatDatas) {
//        CalculAdjustement calculAdjustement = new CalculAdjustement();
//        return calculAdjustement.nonLineaireEventDrivenAdjustment(pltLossData, cap,peatDatas);
//    }
//
//    static List<PLTLossData> eefFrequency(List<PLTLossData> pltLossData, boolean cap, double rpmf, double periodConstante) {
//        CalculAdjustement calculAdjustement = new CalculAdjustement();
//        return calculAdjustement.eefFrequency(pltLossData, cap,rpmf,periodConstante);
//    }
//
//    static List<PLTLossData> eefReturnPeriodBanding(List<PLTLossData> pltLossData, boolean cap, List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings, double periodConstante) {
//        CalculAdjustement calculAdjustement = new CalculAdjustement();
//        return calculAdjustement.eefReturnPeriodBanding(pltLossData, cap,adjustmentReturnPeriodBendings,periodConstante);
//    }
//
//    static List<PLTLossData> oepReturnPeriodBanding(List<PLTLossData> pltLossData, boolean cap, List<AdjustmentReturnPeriodBending> adjustmentReturnPeriodBendings, double periodConstante) {
//        CalculAdjustement calculAdjustement = new CalculAdjustement();
//        return calculAdjustement.oepReturnPeriodBanding(pltLossData, cap,adjustmentReturnPeriodBendings,periodConstante);
//    }



    public static void main(String[] args) throws IOException {
//       List<PLTLossData> pltLossDataList = getPltLossDataFromFile("C:\\Users\\u008208\\Desktop\\adjustment\\Pure-input.xlsx");
////        ExcelUtil.writeToExcel("C:\\Users\\u008208\\Desktop\\adjustment\\lineaire.xlsx",lineaireAdjustement(pltLossDataList,0.93,false));
////        ExcelUtil.writeToExcel("C:\\Users\\u008208\\Desktop\\adjustment\\nonLineaireEventPeriodDrivenAdjustment.xlsx", nonLineaireEventPeriodDrivenAdjustment(pltLossDataList,false,new ArrayList<PEATData>(){{
////            add(new PEATData(1,1,1,1));
////        }}));
////       ExcelUtil.writeToExcel("C:\\Users\\u008208\\Desktop\\adjustment\\nonLineaireEventDrivenAdjustment.xlsx",nonLineaireEventDrivenAdjustment(pltLossDataList,false,new ArrayList<PEATData>(){{
////           add(new PEATData(1,1,1,1));
////       }}));
//        ExcelUtil.writeToExcel("C:\\Users\\u008208\\Desktop\\adjustment\\oepReturnPeriodBanding.xlsx",oepReturnPeriodBanding(pltLossDataList,false,new ArrayList<AdjustmentReturnPeriodBending>(){{
//            add(new AdjustmentReturnPeriodBending(500,0.87));
//            add(new AdjustmentReturnPeriodBending(750,0.9));
//            add(new AdjustmentReturnPeriodBending(10000,0.93));
//            add(new AdjustmentReturnPeriodBending(20000,0.97));
//        }},100000));
//        //ExcelUtil.writeToExcel("C:\\Users\\u008208\\Desktop\\adjustment\\eefFrequency.xlsx",eefFrequency(pltLossDataList,false,1.3,100000));

    }
}
