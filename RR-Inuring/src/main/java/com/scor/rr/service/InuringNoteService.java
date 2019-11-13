package com.scor.rr.service;

import com.scor.rr.entity.InuringNote;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringNodeNotFoundException;
import com.scor.rr.exceptions.inuring.InuringNoteNotFoundException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.InuringNoteRepository;
import com.scor.rr.repository.InuringPackageRepository;
import com.scor.rr.request.InuringNoteCreationRequest;
import com.scor.rr.request.InuringNoteUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class InuringNoteService {
    @Autowired
    private InuringNoteRepository inuringNoteRepository;
    @Autowired
    private InuringPackageRepository inuringPackageRepository;
    @Autowired
    private InuringEdgeService inuringEdgeService;


    public void createNote(InuringNoteCreationRequest request) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(request.getInuringPackageId());
        if (inuringPackage == null) throw new InuringPackageNotFoundException(request.getInuringPackageId());

        if (request.getInuringObjectType() != null) {
            if (!inuringEdgeService.checkInuringNodeExisting(request.getInuringObjectType(), request.getInuringObjectId()))
                throw new InuringNodeNotFoundException(request.getInuringObjectType().toString(), request.getInuringObjectId());

            InuringNote inuringNote = new InuringNote(request.getInuringPackageId(),
                    request.getInuringObjectType()
                    ,request.getInuringObjectId(),
                    request.getNoteContent(),
                    request.getNoteColor(),
                    request.getNoteTitle());
            inuringNoteRepository.save(inuringNote);
        }else{
            InuringNote inuringNote = new InuringNote(request.getInuringPackageId(),
                    request.getNoteContent(),
                    request.getNoteColor(),
                    request.getNoteTitle());
            inuringNoteRepository.save(inuringNote);
        }
    }

    public List<InuringNote> readInuringNotes(int inuringPackageId){
        return inuringNoteRepository.findByInuringPackageId(inuringPackageId);
    }

    public void deleteInuringNote(int inuringNoteId){
        inuringNoteRepository.deleteById(inuringNoteId);
    }

    public void updateInuringNote(InuringNoteUpdateRequest request)throws RRException {
        InuringNote inuringNote = inuringNoteRepository.findByInuringNoteId(request.getInuringNoteId());
        if(inuringNote == null) throw new InuringNoteNotFoundException(request.getInuringNoteId());

        inuringNote.setNoteColor(request.getInuringNoteColor());
        inuringNote.setNoteContent(request.getInuringNoteDescription());
        inuringNote.setNoteTitle(request.getTitle());

        inuringNoteRepository.save(inuringNote);

    }




}
