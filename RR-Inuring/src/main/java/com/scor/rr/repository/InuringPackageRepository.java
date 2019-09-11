package com.scor.rr.repository;

import com.scor.rr.entity.InuringPackage;

/**
 * Created by u004602 on 11/09/2019.
 */
public interface InuringPackageRepository {
    InuringPackage findById(int inuringPackageId);
}
