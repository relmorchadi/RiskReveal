package com.scor.rr.configuration.file;

import sun.misc.Cleaner;
import sun.nio.ch.DirectBuffer;

import java.nio.ByteBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * Created by u004602 on 23/12/2019.
 */
public class FileUtils {
    public static boolean closeDirectBuffer(final ByteBuffer buffer){
        if(!buffer.isDirect())
            return false;
        final DirectBuffer dbb = (DirectBuffer)buffer;
        return AccessController.doPrivileged(
                (PrivilegedAction<Object>) () -> {
                    try {
                        Cleaner cleaner = dbb.cleaner();
                        if (cleaner != null) cleaner.clean();
                        return null;
                    } catch (Exception e) {
                        return dbb;
                    }
                }
        ) == null;
    }
}
