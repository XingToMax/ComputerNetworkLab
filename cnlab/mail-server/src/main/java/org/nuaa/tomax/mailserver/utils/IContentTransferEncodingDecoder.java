package org.nuaa.tomax.mailserver.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Name: IContentTransferEncodingDecoder
 * @Description: use to decode data that encoded by some special
 *           encoding type when transferred
 * @Author: tomax
 * @Date: 2019-04-18 23:22
 * @Version: 1.0
 */
@FunctionalInterface
public interface IContentTransferEncodingDecoder {
    /**
     * decode source
     * @param source encode source
     * @return decode string
     */
    String decode(String source);

    /**
     * some decoding util to special encoding type
     */
    Map<String, IContentTransferEncodingDecoder> CONTENT_TRANSFER_ENCODER =
            new HashMap<String, IContentTransferEncodingDecoder>() {{
                // not special encoding on data
                put("empty", (src) -> src);

                // base64 encoding type
                put("base64", Base64Wrapper::decode);

                // base64 encoding type
                put("BASE64", Base64Wrapper::decode);

                // TODO : add other decoder
            }};
}
