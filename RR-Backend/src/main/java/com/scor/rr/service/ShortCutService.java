package com.scor.rr.service;

import com.scor.rr.domain.entities.Search.ShortCut;
import com.scor.rr.repository.ShortCutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShortCutService {

    @Autowired
    ShortCutRepository shortCutRepository;


    public List<ShortCut> getShortCuts() {
        return this.shortCutRepository.findAll();
    }
}
