package org.nuaa.tomax.imclient.util;

/**
 * @Name: StringUtil
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-19 09:22
 * @Version: 1.0
 */
public class StringUtil {
    /**
     * check if a string has the prefix(not consider case) or not
     * @param src source string
     * @param prefix check prefix
     * @return check result
     */
    public static boolean startsIgnoreCaseWith(String src, String prefix) {
        if (src == null || prefix == null || src.length() < prefix.length()) {
            return false;
        }

        String srcPrefix = src.substring(0, prefix.length());
        return srcPrefix.equalsIgnoreCase(prefix);
    }

    /**
     * check if a string has the suffix(not consider case) or not
     * @param src source string
     * @param suffix check suffix
     * @return check result
     */
    public static boolean endsIgnoreCaseWith(String src, String suffix) {
        if (src == null || suffix == null || src.length() < suffix.length()) {
            return false;
        }

        String srcSuffix = src.substring(src.length() - suffix.length());
        return srcSuffix.equalsIgnoreCase(suffix);
    }

    /**
     * get file suffix
     * @param fileName file name
     * @return suffix
     */
    public static String getFileSuffix(String fileName) {
        return fileName.contains(".") ?
                fileName.substring(fileName.lastIndexOf(".") + 1) : "*";
    }
}
