package com.scor.rr.configuration.utils;

import com.scor.rr.configuration.ConverterType;
import com.scor.rr.configuration.TypeToConvert;
import com.scor.rr.domain.importFile.*;
import com.scor.rr.domain.dto.ImportFilePLTData;
import com.scor.rr.repository.AdjustmentReturnPeriodBandingParameterRepository;
import com.scor.rr.repository.ImportedFileRepository;
import com.scor.rr.repository.PEQTFileSchemaRepository;
import com.scor.rr.repository.PEQTFileTypeRepository;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LossDataFileUtils {
}
