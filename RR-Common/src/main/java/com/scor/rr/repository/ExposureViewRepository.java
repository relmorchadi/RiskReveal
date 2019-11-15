package com.scor.rr.repository;


import com.scor.rr.domain.ExposureView;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExposureViewRepository extends JpaRepository<ExposureView, Long> {

    ExposureView findByDefaultView(boolean defaultView);
}
