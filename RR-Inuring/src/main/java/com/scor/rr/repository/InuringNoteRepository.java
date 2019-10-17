package com.scor.rr.repository;

import com.scor.rr.entity.InuringNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InuringNoteRepository extends JpaRepository<InuringNote, Integer> {
}
