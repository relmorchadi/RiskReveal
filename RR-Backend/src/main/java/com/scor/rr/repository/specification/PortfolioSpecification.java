package com.scor.rr.repository.specification;

import com.scor.rr.domain.PortfolioView;
import com.scor.rr.domain.PortfolioView_;
import com.scor.rr.domain.dto.PortfolioFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;

/**
 * private String id;
 *     private Integer dataSourceId;
 *     private String dataSourceName;
 *     private Timestamp creationDate;
 *     private String descriptionType;
 */
@Component
public class PortfolioSpecification extends BaseSpecification<PortfolioView, PortfolioFilter> {
    @Override
    public Specification<PortfolioView> getFilter(PortfolioFilter request) {
        return Specification.where(ofNullable(request.getId()).map(id -> AttributeContains(PortfolioView_.id, id)).orElse(null))
                .and(ofNullable(request.getDataSourceId()).map(dataSourceId -> AttributeContainsInteger(PortfolioView_.dataSourceId, valueOf(dataSourceId) )).orElse(null))
                .and(ofNullable(request.getDataSourceName()).map(datasourceName -> AttributeContains(PortfolioView_.dataSourceName, datasourceName)).orElse(null))
                .and(ofNullable(request.getCreationDate()).map(creationDate -> AttributeEquals(PortfolioView_.creationDate, creationDate)).orElse(null))
                .and(ofNullable(request.getDescriptionType()).map(descriptionType -> AttributeContains(PortfolioView_.descriptionType, descriptionType)).orElse(null))
                .and(ofNullable(request.getEdmId()).map(edmId -> AttributeEquals(PortfolioView_.edmId, edmId)).orElse(null))
                .and(ofNullable(request.getEdmName()).map(edmName -> AttributeEquals(PortfolioView_.edmName, edmName)).orElse(null))
                .and(ofNullable(request.getDescriptionType()).map(agCedent -> AttributeContains(PortfolioView_.agCedent, agCedent)).orElse(null))
                .and(ofNullable(request.getDescriptionType()).map(agCurrency -> AttributeContains(PortfolioView_.agCurrency, agCurrency)).orElse(null))
                .and(ofNullable(request.getDescriptionType()).map(agSource -> AttributeContains(PortfolioView_.agSource, agSource)).orElse(null))
                .and(ofNullable(request.getDescriptionType()).map(number -> AttributeContains(PortfolioView_.number, number)).orElse(null))
                .and(ofNullable(request.getDescriptionType()).map(peril -> AttributeContains(PortfolioView_.peril, peril)).orElse(null))
                .and(ofNullable(request.getDescriptionType()).map(portfolioId -> AttributeContains(PortfolioView_.portfolioId, portfolioId)).orElse(null))
                .and(ofNullable(request.getDescriptionType()).map(type -> AttributeContains(PortfolioView_.type, type)).orElse(null));
    }
}
