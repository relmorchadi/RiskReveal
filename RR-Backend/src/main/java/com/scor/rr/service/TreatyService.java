package com.scor.rr.service;

import com.scor.rr.domain.Treaty;
import com.scor.rr.exceptions.ExceptionCodename;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.repository.TreatyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

import static com.scor.rr.exceptions.ExceptionCodename.UNKNOWN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TreatyService {
    
    @Autowired
    TreatyRepository treatyRepository;

    public Treaty findOne(String id){
        return treatyRepository.getOne(id);
    }

    public List<Treaty> findAll(){
        return treatyRepository.findAll();
    }

    public Treaty save(Treaty treatyModel){
        return treatyRepository.save(treatyModel);
    }

    public void delete(String id) {
        this.treatyRepository.delete(
                this.treatyRepository.
                        findById(id)
                        .orElseThrow(throwException(UNKNOWN, NOT_FOUND))
        );
    }
    private Supplier throwException(ExceptionCodename codeName, HttpStatus httpStatus) {
        return () -> new RRException(codeName, httpStatus.value());
    }
}
