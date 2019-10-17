package com.scor.rr.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

/**
 * JPA Specification Repository
 *
 * @author HADDINI Zakariyae
 *
 */
public interface JpaSpecificationRepository/*<T, ID extends Serializable>
		extends PagingAndSortingRepository<T, ID>, JpaSpecificationExecutor<T> */{

}
