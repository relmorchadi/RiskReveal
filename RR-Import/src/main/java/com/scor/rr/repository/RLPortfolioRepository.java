package com.scor.rr.repository;

import com.scor.rr.domain.EdmPortfolio;
import com.scor.rr.domain.riskLink.RLAnalysis;
import com.scor.rr.domain.riskLink.RLPortfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RLPortfolioRepository extends JpaRepository<RLPortfolio, Long>, JpaSpecificationExecutor<RLPortfolio> {

    RLPortfolio findByRlId(Long portfolioId);

    @Modifying
    @Transactional(transactionManager = "rrTransactionManager")
    @Query(value = "exec dbo.usp_DeleteRLModelPortfolioByRLModelDataSourceId @ModelDataSourceId =:rlModelDataSourceId", nativeQuery = true)
    void deleteByRlModelDataSourceRlModelDataSourceId(@Param("rlModelDataSourceId") Long rlModelDataSourceId);

    @Modifying()
    @Transactional(transactionManager = "rrTransactionManager")
    @Query("UPDATE RLPortfolio rp" +
            " SET rp.tiv =:#{#portfolio.tiv}," +
            " rp.number =:#{#portfolio.number}" +
            " WHERE " +
            " rp.projectId=:projectId AND " +
            " rp.edmId= :#{#portfolio.edmId} AND " +
            " rp.edmName= :#{#portfolio.edmName} AND " +
            " rp.rlId= :#{#portfolio.portfolioId} AND " +
            " rp.name= :#{#portfolio.name}")
    void updatePortfolioById(@Param("projectId") Long projectId, @Param("portfolio") EdmPortfolio portfolio);

    RLPortfolio findByEdmIdAndEdmNameAndRlIdAndProjectId(Long edmId, String edmName, Long portfolioId, Long projectId);

    List<RLPortfolio> findByRlModelDataSourceRlModelDataSourceId(Long rlModelDataSourceId);

    @Query(value = "select rlp from RLPortfolio rlp where rlp.number= :name and rlp.rlModelDataSource.rlModelDataSourceId= :edmId")
    List<RLPortfolio> findByEdmIdAndNumber(@Param("edmId") Long edmId, @Param("name") String name);

}
