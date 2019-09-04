package com.scor.rr.service.adjustement;


import com.scor.rr.domain.AdjustmentNodeOrderEntity;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeOrderRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentNodeOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.scor.rr.exceptions.ExceptionCodename.ORDEREXIST;

@Service
public class AdjustmentNodeOrderService {

    @Autowired
    AdjustmentNodeOrderRepository adjustmentNodeOrderRepository;

    @Autowired
    AdjustmentNodeService nodeService;

    @Autowired
    AdjustmentThreadService threadService;

    public List<AdjustmentNodeOrderEntity> findAll() {
        return adjustmentNodeOrderRepository.findAll();
    }

    public AdjustmentNodeOrderEntity getAdjustmentOrderByThreadIdAndNodeId(Integer threadId,Integer nodeId){
        return adjustmentNodeOrderRepository.getAdjustmentOrderByThreadIdAndNodeId(threadId,nodeId);
    }

    public void saveNodeOrder(AdjustmentNodeOrderRequest orderRequest) {
        AdjustmentNodeOrderEntity orderEntity = new AdjustmentNodeOrderEntity();
        if(adjustmentNodeOrderRepository.getAdjustmentOrderByThreadIdAndNodeId(orderRequest.getAdjustmentThreadId(),orderRequest.getOrder()) == null) {
            orderEntity.setAdjustmentNode(nodeService.getAdjustmentNode(orderRequest.getAdjustmentNodeId()));
            orderEntity.setAdjustmentThread(threadService.findOne(orderRequest.getAdjustmentThreadId()));
            orderEntity.setOrderNode(orderRequest.getOrder());
            adjustmentNodeOrderRepository.save(orderEntity);
        } else {
            throwException(ORDEREXIST,HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public AdjustmentNodeOrderEntity updateOrder(Integer nodeId,Integer sequence,Integer threadId) {
        AdjustmentNodeOrderEntity orderEntity = adjustmentNodeOrderRepository.getAdjustmentNodeOrderEntityByAdjustmentNode_AdjustmentNodeId(nodeId);
        List<AdjustmentNodeOrderEntity> orderEntities = adjustmentNodeOrderRepository.getAdjustmentOrderByThread(threadId);
        orderEntities = orderEntities.stream().sorted(Comparator.comparing(AdjustmentNodeOrderEntity::getOrderNode)).collect(Collectors.toList());
        if(!orderEntities.isEmpty()) {
            if(orderEntities.size() != 1) {
                if (sequence < orderEntity.getOrderNode()) {
                    for (int i = sequence-1; i < orderEntity.getOrderNode()-1 ; i++) {
                        orderEntities.get(i).setOrderNode(orderEntities.get(i).getOrderNode() + 1);
                        adjustmentNodeOrderRepository.save(orderEntities.get(i));
                    }
                } else {
                    for(int i=orderEntity.getOrderNode();i<sequence;i++) {
                            orderEntities.get(i).setOrderNode(orderEntities.get(i).getOrderNode()- 1);
                            adjustmentNodeOrderRepository.save(orderEntities.get(i));
                    }
                }
            }
            orderEntity.setOrderNode(sequence);
            return adjustmentNodeOrderRepository.save(orderEntity);
            } else {
                orderEntity.setOrderNode(sequence);
                return adjustmentNodeOrderRepository.save(orderEntity);
            }
    }

    void deleteByNodeId(Integer nodeId,Integer threadId) {
        AdjustmentNodeOrderEntity orderEntity = adjustmentNodeOrderRepository.getAdjustmentNodeOrderEntityByAdjustmentNode_AdjustmentNodeId(nodeId);
        List<AdjustmentNodeOrderEntity> orderEntities = adjustmentNodeOrderRepository.getAdjustmentOrderByThread(threadId);
        for(int i=orderEntity.getOrderNode();i<orderEntities.size();i++){
            orderEntities.get(i).setOrderNode(orderEntities.get(i).getOrderNode()- 1);
            adjustmentNodeOrderRepository.save(orderEntities.get(i));
        }
        adjustmentNodeOrderRepository.deleteByAdjustmentNode_AdjustmentNodeId(nodeId);
    }

    public void deleteByThread(Integer threadId) {
        adjustmentNodeOrderRepository.deleteByAdjustmentThread_AdjustmentThreadId(threadId);
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }

}
