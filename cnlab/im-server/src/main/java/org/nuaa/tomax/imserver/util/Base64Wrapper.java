package org.nuaa.tomax.imserver.util;

import java.util.Base64;

/**
 * @Author: ToMax
 * @Description:
 * @Date: Created in 2019/4/10 15:55
 */
public class Base64Wrapper {
    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();

    /**
     * just util class, not need to be construct
     */
    private Base64Wrapper() {}

    /**
     * encode string by base64
     * @param origin origin string
     * @return encode string
     */
    public static String encode(String origin) {
        return encoder.encodeToString(origin.getBytes());
    }

    /**
     * decode string by base64
     * @param origin encode string
     * @return decode string
     */
    public static String decode(String origin) {
        return new String(decoder.decode(origin));
    }
}
