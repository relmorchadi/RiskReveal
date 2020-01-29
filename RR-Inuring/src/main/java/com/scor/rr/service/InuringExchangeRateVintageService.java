package com.scor.rr.service;

import com.scor.rr.domain.WorkspaceEntity;
import com.scor.rr.entity.InuringExchangeRate;
import com.scor.rr.entity.InuringPackage;
import com.scor.rr.exceptions.RRException;
import com.scor.rr.exceptions.inuring.ExchangeRateTypeNotFoundException;
import com.scor.rr.exceptions.inuring.InuringPackageNotFoundException;
import com.scor.rr.repository.InuringExchangeRateVintageRepository;
import com.scor.rr.repository.InuringPackageRepository;
import com.scor.rr.repository.WorkspaceEntityRepository;
import com.scor.rr.request.InuringExchangeRateVintageRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InuringExchangeRateVintageService {

    @Autowired
    private InuringExchangeRateVintageRepository inuringExchangeRateVintageRepository;
    @Autowired
    private InuringPackageRepository inuringPackageRepository;

    @Value(value = "${application.inuring.path}")
    private String path;
    @Value(value = "${application.inuring.separator}")
    private String separator;
    private static final String DELIM = ";";
    private SimpleDateFormat sdf = new SimpleDateFormat("MMM d, yyyy hh:mm:ss aaa", Locale.ENGLISH);


    public void getCurrencyExchangeRate(InuringExchangeRateVintageRequest request) throws RRException, IOException {

        Map<String, Double> exchangeRates = new HashMap<>();
        String currencyFilePath = path + "CurrencyDefinitionFiles" + separator;

        InuringPackage inuringPackage = inuringPackageRepository.findByInuringPackageId(request.getInuringPackageId());
        if(inuringPackage == null) throw new InuringPackageNotFoundException(request.getInuringPackageId());



        boolean created = new File(currencyFilePath).mkdir();
        File file = new File(currencyFilePath + "CurrencyDefinition" + request.getInuringPackageId());

        //need to get the workspace and the section and do condition on them

        BufferedWriter bw = Files.newBufferedWriter(file.toPath(), Charset.defaultCharset());
        writeln(bw, "FormatId", 0);

        Date inforceDate = request.getInceptionDate();
        writeln(bw, "InforceDate", sdf.format(inforceDate));

//        writeln(bw, "BasicCurrency", );


    }

    private void writeln(BufferedWriter bw, Object... o) throws IOException {
        bw.write(StringUtils.join(o, DELIM));
        bw.newLine();
    }

}
