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

import static com.scor.rr.exceptions.ExceptionCodename.ORDER_EXIST;

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
        return adjustmentNodeOrderRepository.findByAdjustmentThreadAdjustmentThreadIdAndAdjustmentNodeAdjustmentNodeId(threadId,nodeId);
    }

    public void saveNodeOrder(AdjustmentNodeOrderRequest orderRequest) {
        AdjustmentNodeOrderEntity orderEntity = new AdjustmentNodeOrderEntity();
        if(adjustmentNodeOrderRepository.findByAdjustmentThreadAdjustmentThreadIdAndAdjustmentNodeAdjustmentNodeId(orderRequest.getAdjustmentThreadId(),orderRequest.getOrder()) == null) {
            orderEntity.setAdjustmentNode(nodeService.getAdjustmentNode(orderRequest.getAdjustmentNodeId()));
            orderEntity.setAdjustmentThread(threadService.findOne(orderRequest.getAdjustmentThreadId()));
            orderEntity.setAdjustmentOrder(orderRequest.getOrder());
            adjustmentNodeOrderRepository.save(orderEntity);
        } else {
            throwException(ORDER_EXIST,HttpStatus.NOT_ACCEPTABLE);
        }
    }

    public AdjustmentNodeOrderEntity updateOrder(Integer nodeId,Integer sequence,Integer threadId) {
        AdjustmentNodeOrderEntity orderEntity = adjustmentNodeOrderRepository.findByAdjustmentNodeAdjustmentNodeId(nodeId);
        List<AdjustmentNodeOrderEntity> orderEntities = adjustmentNodeOrderRepository.findByAdjustmentThreadAdjustmentThreadId(threadId);
        orderEntities = orderEntities.stream().sorted(Comparator.comparing(AdjustmentNodeOrderEntity::getAdjustmentOrder)).collect(Collectors.toList());
        if(!orderEntities.isEmpty()) {
            if(orderEntities.size() != 1) {
                if (sequence < orderEntity.getAdjustmentOrder()) {
                    for (int i = sequence-1; i < orderEntity.getAdjustmentOrder()-1 ; i++) {
                        orderEntities.get(i).setAdjustmentOrder(orderEntities.get(i).getAdjustmentOrder() + 1);
                        adjustmentNodeOrderRepository.save(orderEntities.get(i));
                    }
                } else {
                    for(int i = orderEntity.getAdjustmentOrder(); i<sequence; i++) {
                            orderEntities.get(i).setAdjustmentOrder(orderEntities.get(i).getAdjustmentOrder()- 1);
                            adjustmentNodeOrderRepository.save(orderEntities.get(i));
                    }
                }
            }
            orderEntity.setAdjustmentOrder(sequence);
            return adjustmentNodeOrderRepository.save(orderEntity);
            } else {
                orderEntity.setAdjustmentOrder(sequence);
                return adjustmentNodeOrderRepository.save(orderEntity);
            }
    }

    void deleteByNodeId(Integer nodeId,Integer threadId) {
        AdjustmentNodeOrderEntity orderEntity = adjustmentNodeOrderRepository.findByAdjustmentNodeAdjustmentNodeId(nodeId);
        List<AdjustmentNodeOrderEntity> orderEntities = adjustmentNodeOrderRepository.findByAdjustmentThreadAdjustmentThreadId(threadId);
        for(int i = orderEntity.getAdjustmentOrder(); i<orderEntities.size(); i++){
            orderEntities.get(i).setAdjustmentOrder(orderEntities.get(i).getAdjustmentOrder()- 1);
            adjustmentNodeOrderRepository.save(orderEntities.get(i));
        }
        adjustmentNodeOrderRepository.deleteByAdjustmentNodeAdjustmentNodeId(nodeId);
    }

    public void deleteByThread(Integer threadId) {
        adjustmentNodeOrderRepository.deleteByAdjustmentThreadAdjustmentThreadId(threadId);
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }

}
