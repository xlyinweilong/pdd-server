package com.yin.pddserver.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadImageUtils {

    public static void download(String urlString, String filename) throws Exception {
        URL url = new URL(urlString);
        URLConnection con = url.openConnection();
        try (InputStream is = con.getInputStream()) {
            byte[] bs = new byte[1024];
            int len;
            File file = new File(filename);
            try (FileOutputStream os = new FileOutputStream(file, true)) {
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
            }
        }
    }
}
