package com.scor.rr.service.epMetrics;

import com.scor.rr.configuration.security.UserPrincipal;
import com.scor.rr.domain.SummaryStatisticHeaderEntity;
import com.scor.rr.domain.UserRPEntity;
import com.scor.rr.domain.UserRrEntity;
import com.scor.rr.domain.dto.SaveOrDeleteListOfRPsRequest;
import com.scor.rr.domain.dto.ValidateEpMetricResponse;
import com.scor.rr.domain.enums.CurveType;
import com.scor.rr.repository.DefaultReturnPeriodRepository;
import com.scor.rr.repository.PltHeaderRepository;
import com.scor.rr.repository.SummaryStatisticHeaderRepository;
import com.scor.rr.repository.UserRPRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    PltHeaderRepository pltHeaderRepository;

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

    public ResponseEntity<?> getEpMetrics(String workspaceContextCode, Integer uwYear, CurveType curveType, String screen) {
        try {
            UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            return ResponseEntity.ok(this.defaultReturnPeriodRepository.findEpMetricsByWorkspaceAndUserAndCurveType(user.getUserId(), workspaceContextCode, uwYear, curveType.getCurveType(), screen));
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
            UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            return ResponseEntity.ok(
                    this.userRPRepository.saveAll(
                            request.getRps()
                                    .stream()
                                    .map(rp ->  {
                                        UserRPEntity userRP= new UserRPEntity(rp, user.getUserId(), request.getScreen());
                                        Optional<UserRPEntity> deletedUserRPOpt= this.userRPRepository.findByUserIdAndRpAndScreen(userRP.getUserId(), userRP.getRp(), userRP.getScreen());
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
            UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
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
                userRPRepository.deleteByUserIdAndRpAndScreen(user.getUserId(), rp, request.getScreen());
            });

            toBeSaved.forEach( rp -> {

                UserRPEntity userRPEntity;
                Optional<UserRPEntity> userRPOpt = userRPRepository.findByUserIdAndRpAndScreen(user.getUserId(), rp, request.getScreen());

                if(userRPOpt.isPresent()) {
                    userRPEntity = userRPOpt.get();

                    userRPEntity.setIsDeleted(true);
                    userRPRepository.save(userRPEntity);
                } else {
                    userRPEntity = new UserRPEntity();

                    userRPEntity.setUserId(user.getUserId());
                    userRPEntity.setRp(rp);
                    userRPEntity.setScreen(request.getScreen());
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

    public ResponseEntity<?> getSinglePLTEpMetrics(Long pltHeaderId, CurveType curveType, String screen) {
        try {
            UserRrEntity user = ((UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
            return ResponseEntity.ok(this.defaultReturnPeriodRepository.findSinglePLTEpMetricsUserAndCurveType(user.getUserId(), pltHeaderId, curveType.getCurveType(), screen));
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Optional<SummaryStatisticHeaderEntity> getSinglePLTSummaryStats(Long pltHeaderId) {
        Long summaryStatId= pltHeaderRepository.getSummaryStatHeaderIdById(pltHeaderId);
        return this.summaryStatisticHeaderRepository.findById(summaryStatId);
    }
}
