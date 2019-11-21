package com.scor.rr.repository;

import com.scor.rr.entity.InuringNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InuringNoteRepository extends JpaRepository<InuringNote, Long> {
    InuringNote findByInuringNoteId(long inuringNoteId);
    List<InuringNote> findByInuringPackageId(long inuringPackageId);
}
