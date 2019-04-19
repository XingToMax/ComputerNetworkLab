package org.nuaa.tomax.mailserver.constant;

/**
 * @Name: EncodingType
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-18 23:35
 * @Version: 1.0
 */
public class EncodingType {
    // content transfer encoding

    public final static String CONTENT_TRANSFER_ENCODING_BASE64 = "base64";
    public final static String CONTENT_TRANSFER_ENCODING_8BIT = "8bit";
    public final static String CONTENT_TRANSFER_ENCODING_7BIT = "7bit";
    public final static String CONTENT_TRANSFER_ENCODING_QUOTED_PRINTABLE = "quoted-printable";

    public final static String CONTENT_TYPE_MULTIPART_MIXED = "multipart/mixed";
    public final static String CONTENT_TYPE_MULTIPART_ALTERNATIVE = "multipart/alternative";
    public final static String CONTENT_TYPE_MULTIPART_PARALLEL = "multipart/parallel";
    public final static String CONTENT_TYPE_MULTIPART_DIGEST = "multipart/digest";

}
