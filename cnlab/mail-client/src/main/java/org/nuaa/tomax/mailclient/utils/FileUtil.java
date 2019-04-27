package org.nuaa.tomax.mailclient.utils;

import java.io.*;

/**
 * @Name: FileUtil
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-26 22:25
 * @Version: 1.0
 */
public class FileUtil {

    public static void saveFile(String data, String path) {
        File file = new File(path);

        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bos.write(data.getBytes());
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
