package com.scor.rr.service.adjustement;


import com.scor.rr.domain.AdjustmentNodeOrderEntity;
import com.scor.rr.repository.AdjustmentNodeOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdjustmentNodeOrderService {

    @Autowired
    AdjustmentNodeOrderRepository adjustmentNodeOrderRepository;

    AdjustmentNodeOrderEntity getAdjustmentOrderByThreadIdAndNodeId(Integer threadId,Integer nodeId){
        return adjustmentNodeOrderRepository.getAdjustmentOrderByThreadIdAndNodeId(threadId,nodeId);
    }
}
