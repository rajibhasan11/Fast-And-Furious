package com.sportscar.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public final class IoUtils {

    public static void closeStream(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
            }
        }
    }

    public static void closeStream(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
            }
        }
    }

    public static long copy(File in, OutputStream out) throws IOException {
        return copy(new FileInputStream(in), out);
    }

    public static long copy(InputStream in, File out) throws IOException {
        return copy(in, new FileOutputStream(out));
    }

    /**
     * Pipe an InputStream to the given OutputStream <p /> Taken from Apache Commons IOUtils.
     */
    private static long copy(InputStream input, OutputStream output) throws IOException {
        try {
            byte[] buffer = new byte[1024 * 4];
            long count = 0;
            int n;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
                count += n;
            }
            output.flush();
            return count;
        } finally {
            IoUtils.closeStream(input);
            IoUtils.closeStream(output);
        }
    }

}
