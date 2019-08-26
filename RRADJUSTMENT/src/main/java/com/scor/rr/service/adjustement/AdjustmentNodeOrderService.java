package com.scor.rr.service.adjustement;


import com.scor.rr.domain.AdjustmentNodeOrderEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeOrderRequest;
import com.scor.rr.repository.AdjustmentNodeOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdjustmentNodeOrderService {

    @Autowired
    AdjustmentNodeOrderRepository adjustmentNodeOrderRepository;

    @Autowired
    AdjustmentNodeService nodeService;

    @Autowired
    AdjustmentThreadService threadService;

    AdjustmentNodeOrderEntity getAdjustmentOrderByThreadIdAndNodeId(Integer threadId,Integer nodeId){
        return adjustmentNodeOrderRepository.getAdjustmentOrderByThreadIdAndNodeId(threadId,nodeId);
    }

    AdjustmentNodeOrderEntity saveNodeOrder(AdjustmentNodeOrderRequest orderRequest) {
        AdjustmentNodeOrderEntity orderEntity = new AdjustmentNodeOrderEntity();
        orderEntity.setAdjustmentNode(nodeService.getAdjustmentNode(orderRequest.getAdjustmentNodeId()));
        orderEntity.setAdjustmentThread(threadService.findOne(orderRequest.getAdjustmentThreadId()));
        orderEntity.setOrderNode(orderRequest.getOrder());
        return adjustmentNodeOrderRepository.save(orderEntity);

    }
}
