package com.scor.rr.service.epMetrics;

import com.scor.rr.domain.DefaultReturnPeriodEntity;
import com.scor.rr.domain.UserRPEntity;
import com.scor.rr.domain.dto.SaveOrDeleteListOfRPsRequest;
import com.scor.rr.domain.dto.ValidateEpMetricResponse;
import com.scor.rr.domain.enums.CurveType;
import com.scor.rr.repository.DefaultReturnPeriodRepository;
import com.scor.rr.repository.SummaryStatisticHeaderRepository;
import com.scor.rr.repository.UserRPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EpMetricsService {

    @Autowired
    DefaultReturnPeriodRepository defaultReturnPeriodRepository;

    @Autowired
    UserRPRepository userRPRepository;

    @Autowired
    SummaryStatisticHeaderRepository summaryStatisticHeaderRepository;

    public ResponseEntity<?> validateEpMetric(Integer rp) {
        return ResponseEntity.ok(
                this.getValidateEpMetricResponse(
                        this.defaultReturnPeriodRepository.validateRP(rp)
                )
        );
    }

    private ValidateEpMetricResponse getValidateEpMetricResponse(List<Map<String, Object>> input) {
        if( input.size() > 0 ) {
            Map<String, Object> tmp = input.get(0);
            return new ValidateEpMetricResponse(
                    (Integer) tmp.get("lowerBound"),
                    (Integer) tmp.get("upperBound"),
                    (Boolean) tmp.get("isValid")
            );

        } else return new ValidateEpMetricResponse(null, null, null);
    }

    public ResponseEntity<?> getEpMetrics(Integer userId, String workspaceContextCode, Integer uwYear, CurveType curveType) {
        try {
            return ResponseEntity.ok(this.defaultReturnPeriodRepository.findEpMetricsByWorkspaceAndUserAndCurveType(userId, workspaceContextCode, uwYear, curveType.getCurveType()));
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<?> getSingleEpMetric(String workspaceContextCode, Integer uwYear, CurveType curveType, Integer rp) {
        try {
            return ResponseEntity.ok(this.defaultReturnPeriodRepository.findSingleEpMetricsByWorkspaceAndCurveTypeAndRP(workspaceContextCode, uwYear, curveType.getCurveType(), rp));
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<?> getDefaultReturnPeriods() {
        try {
            return ResponseEntity.ok(this.defaultReturnPeriodRepository.findByIsTableRPOrderByReturnPeriodAsc());
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<?> saveListOfRPs(SaveOrDeleteListOfRPsRequest request) {
        try {
            return ResponseEntity.ok(
                    this.userRPRepository.saveAll(
                            request.getRps()
                                    .stream()
                                    .map(rp ->  {
                                        UserRPEntity userRP= new UserRPEntity(rp, request.getUserId());
                                        Optional<UserRPEntity> deletedUserRPOpt= this.userRPRepository.findByUserIdAndRp(userRP.getUserId(), userRP.getRp());
                                        if(deletedUserRPOpt.isPresent()) {
                                            UserRPEntity deletedUserRP = deletedUserRPOpt.get();
                                            deletedUserRP.setIsDeleted(false);
                                            return deletedUserRP;
                                        } else {
                                            userRP.setIsDeleted(false);
                                            return userRP;
                                        }

                                    })
                                    .collect(Collectors.toList())
                    )
            );
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<?> deleteRP(SaveOrDeleteListOfRPsRequest request) {
        try {

            List<Integer> defaultRPs = defaultReturnPeriodRepository.findByIsTableRPOrderByReturnPeriodAsc();

            List<Integer> toBeDeleted= new ArrayList<>();
            List<Integer> toBeSaved= new ArrayList<>();

            request.getRps().forEach( rp -> {

                if(this.isDefaultRP(defaultRPs, rp)) {
                    toBeSaved.add(rp);
                } else {
                    toBeDeleted.add(rp);
                }
            });

            toBeDeleted.forEach( rp -> {
                userRPRepository.deleteByUserIdAndRp(request.getUserId(), rp);
            });

            toBeSaved.forEach( rp -> {

                UserRPEntity userRPEntity;
                Optional<UserRPEntity> userRPOpt = userRPRepository.findByUserIdAndRp(request.getUserId(), rp);

                if(userRPOpt.isPresent()) {
                    userRPEntity = userRPOpt.get();

                    userRPEntity.setIsDeleted(true);
                    userRPRepository.save(userRPEntity);
                } else {
                    userRPEntity = new UserRPEntity();

                    userRPEntity.setUserId(request.getUserId());
                    userRPEntity.setRp(rp);
                    userRPEntity.setIsDeleted(true);
                }

                userRPRepository.save(userRPEntity);
            });


            return ResponseEntity.ok().build();

        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    Boolean isDefaultRP( List<Integer> defaultRPs, Integer rp) {
        return defaultRPs.contains(rp);
    }

    public ResponseEntity<?> getSinglePLTEpMetrics(Integer userId, Long pltHeaderId, CurveType curveType) {
        try {
            return ResponseEntity.ok(this.defaultReturnPeriodRepository.findSinglePLTEpMetricsUserAndCurveType(userId, pltHeaderId, curveType.getCurveType()));
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<?> getSinglePLTSummaryStats(Long pltHeaderId) {
        try {
            return ResponseEntity.ok(this.summaryStatisticHeaderRepository.findByLossDataIdAndLossDataType(pltHeaderId, "PLT"));
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
