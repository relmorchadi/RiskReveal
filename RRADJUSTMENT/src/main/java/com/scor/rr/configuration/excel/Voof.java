package com.scor.rr.configuration.excel;



import com.scor.rr.configuration.excel.core.Callback;
import com.scor.rr.configuration.excel.core.Unmarshaller;
import com.scor.rr.configuration.excel.exception.VoofException;
import com.scor.rr.configuration.excel.option.Options;
import com.scor.rr.configuration.excel.util.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class Voof {

    public static <T> void fromFile(final String uri, Class<T> type, Callback<T> callback) {
        try {
            Unmarshaller unmarshaller = init(uri, new Options());
            unmarshaller.unmarshaller(type, callback);
        } catch (IOException e) {
            throw new VoofException(e);
        }
    }

    public static <T> Collection<T> fromFile(final String uri, Class<T> type) {
        try {
            Unmarshaller unmarshaller = init(uri, new Options());
            return unmarshaller.unmarshaller(type);
        } catch (IOException e) {
            throw new VoofException(e);
        }
    }

    private static Unmarshaller init(final String uri, final Options options) throws IOException {
        InputStream inputStream = Utils.uriToInputStream(uri);
        String extension = Utils.getExtention(uri);
        return new Unmarshaller(WorkbookFactory.constructWorkbook(extension, inputStream), options);
    }

}
