package com.scor.rr.importBatch.processing.ylt;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.scor.rr.domain.entities.plt.ModelRAP;
import com.scor.rr.domain.entities.references.cat.mapping.ModelRAPSourceMapping;
import com.scor.rr.importBatch.processing.mapping.MappingHandler;
import com.scor.rr.repository.cat.mapping.ModelRAPSourceMappingRepository;
import com.scor.rr.repository.plt.ModelRAPRepository;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class ModelRapPEQTSelector implements PEQTSelector {

    private ModelRAPSourceMappingRepository modelRAPSourceMappingRepository;
    private ModelRAPRepository modelRAPRepository;
    private MappingHandler mappingHandler;

    private final String peqtPath;

    private final LoadingCache<String, PET> cache;

    public ModelRapPEQTSelector(final String peqtPath) {
        this.peqtPath = peqtPath;
        cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(120, TimeUnit.MINUTES)
                .build(new CacheLoader<String, PET>() {
                    @Override
                    public PET load(String s){
                        // Warning : must never return null  !
                        final ModelRAPSourceMapping mrs = modelRAPSourceMappingRepository.findByDlmProfileNameIgnoreCaseAndModellingSystemId(s, mappingHandler.getSystem());
                        final String mrsId = mrs.getTarget().getId();
                        final ModelRAP modelRAP = modelRAPRepository.findByModelRAPSourceIdAndRegionPerilDefaultTrue(mrsId);
                        final com.scor.rr.domain.entities.plt.PET pet = modelRAP.getPet();
                        return new PET(new File(peqtPath,pet.getPeqtFile().getFileName()).getAbsolutePath(), pet.getPetTransformFunction(), pet.getPetTypeCode());
                    }
                });
    }

    public String getPeqtPath() {
        return peqtPath;
    }

    public void setModelRAPSourceMappingRepository(ModelRAPSourceMappingRepository modelRAPSourceMappingRepository) {
        this.modelRAPSourceMappingRepository = modelRAPSourceMappingRepository;
    }

    public void setModelRAPRepository(ModelRAPRepository modelRAPRepository) {
        this.modelRAPRepository = modelRAPRepository;
    }

    public void setMappingHandler(MappingHandler mappingHandler) {
        this.mappingHandler = mappingHandler;
    }

    @Override
    public String getPEQTFile(String region, String peril) {
        return null;
    }

    @Override
    public String getPEQTFile(String dlmProfile) {
        return cache.getUnchecked(dlmProfile).getFileName();
    }

    @Override
    public String getPEQTFunction(String dlmProfile) {
        return cache.getUnchecked(dlmProfile).getFunctionType();
    }

    @Override
    public String getPETType(String dlmProfile) {
        return cache.getUnchecked(dlmProfile).getPetType();
    }

    @Override
    public PET getPETInfo(String dlmProfile) {
        return cache.getUnchecked(dlmProfile);
    }

}
