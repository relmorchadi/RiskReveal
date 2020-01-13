package com.scor.rr.service.adjustement;


import com.scor.rr.domain.AdjustmentNode;
import com.scor.rr.domain.AdjustmentNodeOrder;
import com.scor.rr.domain.dto.adjustement.AdjustmentNodeOrderRequest;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.AdjustmentNodeOrderRepository;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.ORDER_EXIST;

@Service
public class AdjustmentNodeOrderService {
    private static final Logger log = LoggerFactory.getLogger(AdjustmentNodeOrderService.class);

    @Autowired
    AdjustmentNodeOrderRepository adjustmentNodeOrderRepository;

    @Autowired
    AdjustmentNodeService nodeService;

    @Autowired
    AdjustmentThreadService threadService;

    public List<AdjustmentNodeOrder> findAll() {
        return adjustmentNodeOrderRepository.findAll();
    }

    public AdjustmentNodeOrder getAdjustmentOrderByThreadIdAndNodeId(Integer threadId, Integer nodeId){
        return adjustmentNodeOrderRepository.findByAdjustmentThreadAdjustmentThreadIdAndAdjustmentNodeAdjustmentNodeId(threadId, nodeId);
    }

    public AdjustmentNodeOrder findByAdjustmentNode(AdjustmentNode node){
        return adjustmentNodeOrderRepository.findByAdjustmentNode(node);
    }

    public void saveNodeOrder(AdjustmentNodeOrderRequest orderRequest) {
        AdjustmentNodeOrder orderEntity = new AdjustmentNodeOrder();
        if (adjustmentNodeOrderRepository.findByAdjustmentThreadAdjustmentThreadIdAndAdjustmentOrder(orderRequest.getAdjustmentThreadId(), orderRequest.getOrder()) == null) {
            orderEntity.setAdjustmentNode(nodeService.getAdjustmentNode(orderRequest.getAdjustmentNodeId()));
            orderEntity.setAdjustmentThread(threadService.findOne(orderRequest.getAdjustmentThreadId()));
            orderEntity.setAdjustmentOrder(orderRequest.getOrder());
            adjustmentNodeOrderRepository.save(orderEntity);
        } else {
            throwException(ORDER_EXIST,HttpStatus.NOT_ACCEPTABLE);
        }
    }

//    public AdjustmentNodeOrderEntity updateOrder(Long nodeId, Integer sequence, Long threadId) {
//        AdjustmentNodeOrderEntity orderEntity = adjustmentNodeOrderRepository.findByAdjustmentNodeAdjustmentNodeId(nodeId);
//        List<AdjustmentNodeOrderEntity> orderEntities = adjustmentNodeOrderRepository.findByAdjustmentThreadAdjustmentThreadId(threadId);
//        orderEntities = orderEntities.stream().sorted(Comparator.comparing(AdjustmentNodeOrderEntity::getAdjustmentOrder)).collect(Collectors.toList());
//        if (!orderEntities.isEmpty()) {
//            if (orderEntities.size() != 1) {
//                if (sequence < orderEntity.getAdjustmentOrder()) {
//                    for (int i = sequence - 1; i < orderEntity.getAdjustmentOrder() - 1 ; i++) {
//                        orderEntities.get(i).setAdjustmentOrder(orderEntities.get(i).getAdjustmentOrder() + 1);
//                        adjustmentNodeOrderRepository.save(orderEntities.get(i));
//                    }
//                } else {
//                    for(int i = orderEntity.getAdjustmentOrder(); i<sequence; i++) {
//                            orderEntities.get(i).setAdjustmentOrder(orderEntities.get(i).getAdjustmentOrder()- 1);
//                            adjustmentNodeOrderRepository.save(orderEntities.get(i));
//                    }
//                }
//            }
//            orderEntity.setAdjustmentOrder(sequence);
//            return adjustmentNodeOrderRepository.save(orderEntity);
//        } else {
//            orderEntity.setAdjustmentOrder(sequence);
//            return adjustmentNodeOrderRepository.save(orderEntity);
//        }
//    }

    public AdjustmentNodeOrder createNodeOrder(AdjustmentNode node, Integer sequence) {
        log.info("---------- start createNodeOrder ----------");

        AdjustmentNodeOrder order = new AdjustmentNodeOrder();
        order.setAdjustmentNode(node);
        order.setAdjustmentThread(node.getAdjustmentThread());
        if (sequence == null || sequence == 0) {
            throw new IllegalStateException("---------- createNodeOrder, sequence null or 0, wrong ----------");
        }

        List<AdjustmentNodeOrder> orderEntities = adjustmentNodeOrderRepository.findByAdjustmentThreadAdjustmentThreadId(node.getAdjustmentThread().getAdjustmentThreadId());
        if (orderEntities == null || orderEntities.isEmpty()) {
            if (sequence == null || sequence == 1) {
                order.setAdjustmentOrder(1);
            } else {
                throw new IllegalStateException("---------- updateNodeOrder, no node before but sequence greater than 1, wrong ----------");
            }
        } else  {
            if (sequence > orderEntities.size() + 1) {
                throw new IllegalStateException("---------- updateNodeOrder, sequence greater than list nodes size + 1, wrong ----------");
            } else {
                order.setAdjustmentOrder(sequence);
                for (AdjustmentNodeOrder nodeOrder : orderEntities) {
                    if (nodeOrder.getAdjustmentOrder() >= sequence) {
                        nodeOrder.setAdjustmentOrder(nodeOrder.getAdjustmentOrder() + 1);
                        adjustmentNodeOrderRepository.save(nodeOrder);
                    }
                }
            }
        }

        adjustmentNodeOrderRepository.save(order);
        return order;
    }


    public AdjustmentNodeOrder updateNodeOrder(Integer nodeId, Integer sequence, Integer threadId) {
        log.info("---------- start updateNodeOrder ----------");

        AdjustmentNodeOrder order = adjustmentNodeOrderRepository.findByAdjustmentNodeAdjustmentNodeId(nodeId);
        if (order == null) {
            throw new IllegalStateException("---------- updateNodeOrder, node order null, wrong ----------");
        }
        List<AdjustmentNodeOrder> orderEntities = adjustmentNodeOrderRepository.findByAdjustmentThreadAdjustmentThreadId(threadId);
        if (orderEntities == null) {
            throw new IllegalStateException("---------- updateNodeOrder, list node order null, wrong ----------");
        } else {
            if (orderEntities.isEmpty()) {
                throw new IllegalStateException("---------- updateNodeOrder, list node order empty, wrong ----------");
            }
        }

        if (sequence == null) {
            throw new IllegalStateException("---------- updateNodeOrder, sequence null, wrong ----------");
        }

        if (sequence > orderEntities.size() + 1) {
            throw new IllegalStateException("---------- updateNodeOrder, sequence greater than list nodes size + 1, wrong ----------");
        }

//        orderEntities = orderEntities.stream().sorted(Comparator.comparing(AdjustmentNodeOrder::getAdjustmentOrder)).collect(Collectors.toList());

        if (sequence > orderEntities.size()) {
            order.setAdjustmentOrder(sequence);
        } else {
            for (AdjustmentNodeOrder nodeOrder : orderEntities) {
                if (nodeOrder.getAdjustmentOrder() >= sequence) {
                    nodeOrder.setAdjustmentOrder(nodeOrder.getAdjustmentOrder() + 1);
                    adjustmentNodeOrderRepository.save(nodeOrder);
                }
            }
        }

        return order;
    }

    void deleteByNodeIdAndReorder(Integer nodeId, Integer threadId) {
        AdjustmentNodeOrder orderEntity = adjustmentNodeOrderRepository.findByAdjustmentNodeAdjustmentNodeId(nodeId);
        if (orderEntity != null) {
            List<AdjustmentNodeOrder> orderEntities = adjustmentNodeOrderRepository.findByAdjustmentThreadAdjustmentThreadId(threadId);
            for (AdjustmentNodeOrder adjustmentNodeOrder : orderEntities){
                if (adjustmentNodeOrder.getAdjustmentOrder() > orderEntity.getAdjustmentOrder()) {
                    adjustmentNodeOrder.setAdjustmentOrder(adjustmentNodeOrder.getAdjustmentOrder() - 1);
                    adjustmentNodeOrderRepository.save(adjustmentNodeOrder);
                }
            }
            adjustmentNodeOrderRepository.deleteByAdjustmentNodeAdjustmentNodeId(nodeId);
        }
    }

    public void deleteByThread(Long threadId) {
        adjustmentNodeOrderRepository.deleteByAdjustmentThreadAdjustmentThreadId(threadId);
    }

    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }

}
