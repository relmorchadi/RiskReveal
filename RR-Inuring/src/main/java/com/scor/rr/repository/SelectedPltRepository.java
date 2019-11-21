package com.scor.rr.repository;


import com.scor.rr.views.SelectedPLTView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectedPltRepository extends JpaRepository<SelectedPLTView, Integer> {

    SelectedPLTView findByPltId(int pltId);
}
