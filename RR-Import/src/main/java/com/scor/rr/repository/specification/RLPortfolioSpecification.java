package com.scor.rr.repository.specification;

import com.scor.rr.domain.dto.RLPortfolioDto;
import com.scor.rr.domain.riskLink.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;

import static java.util.Optional.ofNullable;

@Service
public class RLPortfolioSpecification extends BaseSpecification<RLPortfolio, RLPortfolioDto> {
    @Override
    public Specification<RLPortfolio> getFilter(RLPortfolioDto filter) {
        return Specification.where(ofNullable(filter.getRlId()).map(rlId -> evaluateSpecialChars(RLPortfolio_.rlId, String.valueOf(rlId)) ).orElse(null) )
                .and(ofNullable(filter.getName()).map(name -> evaluateSpecialChars(RLPortfolio_.name, name) ).orElse(null) )
                .and(ofNullable(filter.getDescription()).map(desc -> evaluateSpecialChars(RLPortfolio_.description, desc)).orElse(null))
                .and(ofNullable(filter.getNumber()).map(engineType -> evaluateSpecialChars(RLPortfolio_.number, engineType)).orElse(null))
                .and(ofNullable(filter.getType()).map(type -> evaluateSpecialChars(RLPortfolio_.type, type)).orElse(null))
                .and(ofNullable(filter.getPeril()).map(peril -> evaluateSpecialChars(RLPortfolio_.peril, peril)).orElse(null))
                .and(ofNullable(filter.getAgCurrency()).map(currency -> evaluateSpecialChars(RLPortfolio_.agCurrency, currency)).orElse(null))
                .and(ofNullable(filter.getAgCedent()).map(cedant -> evaluateSpecialChars(RLPortfolio_.agCedent, cedant)).orElse(null))
                .and(ofNullable(filter.getAgSource()).map(agSource -> evaluateSpecialChars(RLPortfolio_.agSource, agSource)).orElse(null));
    }

    public Specification<RLPortfolio> getFilter(RLPortfolioDto filter, Long rlModelDataSourceId) {
        return ofNullable(filter)
                .map(f -> getFilter(f).and(joinPortfolioModelDataSource(rlModelDataSourceId)))
                .orElse(null);
    }

    private Specification<RLPortfolio> joinPortfolioModelDataSource(Long modelDataSourceId){
        return (root, query, cb) -> {
            Join<RLPortfolio, RLModelDataSource> join = root.join("rlModelDataSource");
            return cb.equal(join.get(RLModelDataSource_.RL_MODEL_DATA_SOURCE_ID), modelDataSourceId);
        };
    }
}
