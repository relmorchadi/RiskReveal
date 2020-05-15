package com.scor.rr.repository;

import com.scor.rr.domain.riskLink.RLPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RLPortfolioRepository extends JpaRepository<RLPortfolio, Long>, JpaSpecificationExecutor<RLPortfolio> {


    RLPortfolio findByEdmIdAndEdmNameAndRlIdAndProjectId(Long edmId, String edmName, Long portfolioId, Long projectId);

    List<RLPortfolio> findByRlModelDataSourceRlModelDataSourceId(Long rlModelDataSourceId);

    @Query(value = "select rlp from RLPortfolio rlp where rlp.number= :name and rlp.rlModelDataSource.rlModelDataSourceId= :edmId")
    List<RLPortfolio> findByEdmIdAndNumber(@Param("edmId") Long edmId, @Param("name") String name);

    List<RLPortfolio> findByEdmIdAndProjectId(Long rlModelDataSourceId, Long portfolioIds);

    @Modifying
    @Transactional(transactionManager = "theTransactionManager")
    @Query("delete from RLPortfolio where rlId =:rLPortfolioId and projectId=:projectId")
    void deleteByRLPortfolioId(@Param("rLPortfolioId") Long rLPortfolioId, @Param("projectId") Long projectId);

}
