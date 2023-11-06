package com.yin.pddserver.common.utils;

import java.io.*;
import java.util.Base64;

/**
 * zip
 *
 * @author yin.weilong
 * @date 2019.09.29
 */
public class MyFileUtils {

    public static void base64ToFile(File file, String base64) {
        try (FileOutputStream fos = new FileOutputStream(file); BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            byte[] bytes = Base64.getDecoder().decode(base64);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean copyByte2File(byte [] bytes,File file){
        FileOutputStream  out = null;
        try {
            //转化为输入流
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);

            //写出文件
            byte[] buffer = new byte[1024];

            out = new FileOutputStream(file);

            //写文件
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len); // 文件写操作
            }
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                if(out != null){
                    out.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return false;
    }
}
