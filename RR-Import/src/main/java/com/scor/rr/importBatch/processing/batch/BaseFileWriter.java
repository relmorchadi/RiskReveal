package com.scor.rr.importBatch.processing.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by U002629 on 17/03/2015.
 */
public abstract class BaseFileWriter extends BaseBatchBeanImpl {

    private static final Logger log= LoggerFactory.getLogger(BaseFileWriter.class);

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.US);
    private static final SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMdd", Locale.US);
    private static final NumberFormat format;

    static {
        format = NumberFormat.getInstance();
        format.setMinimumIntegerDigits(2);
        format.setGroupingUsed(false);
    }

	private String filePath;
    private String fileExtension;
    private Path ihubPath;

    
    // creer un fichier dans un repertoire 'proprement'
    // File tmp=new File(filePath,pltFileName);
    // filePath : chemin du repertoire
    // pltFileName : nom du fichier dans le repertoire.
    
    
    public BaseFileWriter() {
    }

    public void init(){
    }

    public BaseFileWriter(String filePath, String fileExtension) {
        this.filePath = filePath;
        this.fileExtension = fileExtension;
        this.ihubPath = Paths.get(filePath);
    }

    public void setIhubPath(Path ihubPath) {
        this.ihubPath = ihubPath;
    }

    public Path getIhubPath() {
        return ihubPath;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFileExtension() {
        return fileExtension;
    }
    
    protected boolean closeDirectBuffer(final ByteBuffer buffer){
        if(!buffer.isDirect())
            return false;
        final DirectBuffer dbb = (DirectBuffer)buffer;
        return AccessController.doPrivileged(
                new PrivilegedAction<Object>() {
                    public Object run() {
                        try {
                            Cleaner cleaner = dbb.cleaner();
                            if (cleaner != null) cleaner.clean();
                            return null;
                        } catch (Exception e) {
                            return dbb;
                        }
                    }
                }
        ) == null;
    }

    /**
     *
     * @param prefixDirectory
     *      \Treaty\Cedant\U002629-J629\01T000031.2011.1.0.1\2011-01\P_000000199
     * @param filename
 *          T_P_U002629_01T000031.2011.1.0.1_01_2011-01_ELT_20160805-032659_RMS-RiskLink_RL15_NAEQ_GR_EUR_P_000000199_FT_MODEL_DAT_O_ELTH_000001402_11.bin
     * @return
     */
    public File makeFullFile(String prefixDirectory, String filename) {
        final Path fullPath = ihubPath.resolve(prefixDirectory);
        try {
            Files.createDirectories(fullPath);
        } catch (IOException e) {
            log.error("Exception: ", e);
            throw new RuntimeException("error creating paths "+fullPath, e);
        }
        final File parent = fullPath.toFile();

        File file = new File(parent, filename);
        return file;
    }

}
