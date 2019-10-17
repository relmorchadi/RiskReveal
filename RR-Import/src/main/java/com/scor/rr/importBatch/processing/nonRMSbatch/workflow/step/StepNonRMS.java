package com.scor.rr.importBatch.processing.nonRMSbatch.workflow.step;

/**
 * Created by U005342 on 19/07/2018.
 */
public class StepNonRMS {
    public static String getStepNameFromStepIdForAnalysis(int stepId){
        switch (stepId){
            case 1: return "LOAD_LOSS_DATA_FILE";
            case 2: return "CONVERT_LOSS_DATA_TO_SCOR_FORMAT";
            case 3: return "CALCULATE_EPC_EPS_FOR_SOURCE_PLT";
            case 4: return "CONFORM_PLT";
            case 5: return "ADJUST_DEFAULT";
            case 6: return "CREATE_THREAD_AND_PERSIST_PLT";
            default: return null;
        }
    }
}
