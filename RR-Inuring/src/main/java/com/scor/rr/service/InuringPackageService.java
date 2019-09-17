package com.scor.rr.service;

import com.scor.rr.entity.InuringPackage;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.InuringIllegalModificationException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.InuringFinalNodeRepository;
import com.scor.rr.repository.InuringPackageRepository;
import com.scor.rr.repository.InuringpackageRepository;
import com.scor.rr.request.InuringPackageCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by u004602 on 16/09/2019.
 */
@Service
public class InuringPackageService {
    @Autowired
    private InuringPackageRepository inuringPackageRepository;
    @Autowired
    private InuringFinalNodeService inuringFinalNodeService;

    public void createInuringPackage(InuringPackageCreationRequest request) throws RRException {
        InuringPackage inuringPackage = new InuringPackage(request.getPackageName(),
                request.getPackageDescription(),
                request.getWorkspaceId(),
                request.getCreatedBy());

        inuringPackage = inuringPackageRepository.save(inuringPackage);
        inuringFinalNodeService.createInuringFinalNodeForPackage(inuringPackage.getInuringPackageId());
    }

    public void deleteInuringPackage(int inuringPackageId) throws RRException {
        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(inuringPackageId);
        if (inuringPackage == null) throw new InuringPackageNotFoundException(inuringPackageId);
        if (inuringPackage.isLocked()) throw new InuringIllegalModificationException(inuringPackageId);
        inuringPackageRepository.delete(inuringPackage);
        inuringFinalNodeService.deleteByInuringPackageId(inuringPackageId);
    }
}
