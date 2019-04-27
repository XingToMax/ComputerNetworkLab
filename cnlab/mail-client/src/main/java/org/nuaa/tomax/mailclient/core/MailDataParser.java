package org.nuaa.tomax.mailclient.core;

import org.nuaa.tomax.mailclient.constant.MimeType;
import org.nuaa.tomax.mailclient.entity.FileEntity;
import org.nuaa.tomax.mailclient.entity.MailDataEntity;
import org.nuaa.tomax.mailclient.entity.MailEntity;
import org.nuaa.tomax.mailclient.utils.Base64Wrapper;
import org.nuaa.tomax.mailclient.utils.FileUtil;
import org.nuaa.tomax.mailclient.utils.IContentTransferEncodingDecoder;
import org.nuaa.tomax.mailclient.utils.StringUtil;

import java.util.*;

/**
 * @Name: MailDataParser
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-24 17:08
 * @Version: 1.0
 */
public class MailDataParser {
    private final static Map<String, String> PARAM_KEY_LIST = new HashMap<String, String>(){{
        // key represents must have
        // default represents we can set it after parse
        put("subject", "key");
        put("from", "default");
        put("to", "key");
        put("date", "default");
        put("MIME-Version", "1.0");
        put("Content-Type", "default");
        put("X-priority", "default");
        put("Content-Transfer-Encoding", "default");
        put("Content-Disposition", "default");
    }};

    public static MailDataEntity lightParse(MailEntity src) {
        Map<String, String> paramMap = new HashMap<>();
        MailDataEntity mail = new MailDataEntity();
        mail.setId(src.getId());
        mail.setFrom(src.getFromMail());
        mail.setTo(src.getToMail());
        mail.setTime(src.getTime().toString());
        mail.setMailType(src.getMailType());
        List<String> head = getHead(src.getData());
        parseHead(head, paramMap);
        // set subject
        if (paramMap.containsKey("subject")) {
            mail.setSubject(paramMap.get("subject"));
        }

        // set cc
        if (paramMap.containsKey("cc")) {
            mail.setCc(paramMap.get("cc"));
        }
        return mail;
    }

    public static MailDataEntity parse(MailEntity src, String savePath) {
        Map<String, String> paramMap = new HashMap<>();
        MailDataEntity mail = new MailDataEntity();
        mail.setId(src.getId());
        mail.setFrom(src.getFromMail());
        mail.setTo(src.getToMail());
        mail.setMailType(src.getMailType());
        // TODO : time type wait to be modified
        mail.setTime(src.getTime().toString());

        // get head from data
        List<String> head = getHead(src.getData());
        // parse head
        parseHead(head, paramMap);

        // need to use content type to parse the body

        // if multipart-mixed, use boundary to split
        // else get data directly

        String body = getBody(src.getData());

        if (StringUtil.startsIgnoreCaseWith(
                paramMap.get("content-type"), "multipart/mixed"
        )) {
            // mixed branch
            List<String> parts = getBodyParts(body, getBoundary(paramMap));
            parseBodyParts(parts, mail, paramMap, savePath);

        } else {
            // only body part
            body = getBody(src.getData());
            // transfer decode
            if (paramMap.containsKey("content-transfer-encoding")) {
                body = IContentTransferEncodingDecoder.CONTENT_TRANSFER_ENCODER
                        .get(paramMap.get("content-transfer-encoding"))
                        .decode(body.replaceAll("\r", "").replaceAll("\n", ""));
            }
            mail.setContent(body);
        }

        // set subject
        if (paramMap.containsKey("subject")) {
            mail.setSubject(paramMap.get("subject"));
        }

        // set cc
        if (paramMap.containsKey("cc")) {
            mail.setCc(paramMap.get("cc"));
        }

        return mail;
    }

    private static void parseHead(List<String> head, Map<String, String> paramMap) {
        for (String line : head) {
            String[] kv = line.split(":");
            paramMap.put(kv[0].trim().toLowerCase(),
                    kv[1].trim());
        }
    }

    private static void parseBodyParts(List<String> body, MailDataEntity mail, Map<String, String> paramMap, String savePath) {
        for (String part : body) {
            List<String> head = getHead(part);
            Map<String, String> params = new HashMap<>();
            parseHead(head, params);
            String contentType = "";
            // extract content-type
            if (params.containsKey("content-type")) {
                contentType = params.get("content-type");
                if (contentType.contains(";")) {
                    contentType = contentType.split(";")[0].trim();
                }

            } else {
                contentType = MimeType.getMimeType("*");
            }

            String data = getBody(part);
            // check transfer encoding
            if (params.containsKey("content-transfer-encoding")) {
//                String[] lines = data.split("\n");
//                StringBuilder dataBuilder = new StringBuilder();
//                for (String line : lines) {
//                    dataBuilder.append(IContentTransferEncodingDecoder.CONTENT_TRANSFER_ENCODER
//                            .get(params.get("content-transfer-encoding")).decode(data.replaceAll("\r", "").replaceAll("\n", "")));
//                }
//                data = dataBuilder.toString();
//                data = IContentTransferEncodingDecoder.CONTENT_TRANSFER_ENCODER
//                        .get(params.get("content-transfer-encoding")).decode(data.replaceAll("\r", "").replaceAll("\n", ""));
                // TODO : data decode
            }

            if (params.containsKey("content-disposition")) {
                // check attachment
                if (StringUtil.startsIgnoreCaseWith(params.get("content-disposition"), "attachment")) {
                    String deposition = params.get("content-disposition");
                    String[] cells = deposition.split(";");
                    String type = cells[0].trim();
                    String fileName = Base64Wrapper.decode(cells[1].split("\"")[1].trim());
                    FileEntity file = new FileEntity();
                    file.setName(fileName);
                    file.setSize((data.getBytes().length));
                    file.setType(fileName.split("\\.")[1]);
                    FileUtil.saveFile(data, savePath + fileName);
                    mail.getFileEntities().add(file);
                } else {
                    mail.setContent(data);
                }
            } else {
                data = IContentTransferEncodingDecoder.CONTENT_TRANSFER_ENCODER
                        .get(params.get("content-transfer-encoding")).decode(data.replaceAll("\r", "").replaceAll("\n", ""));
                mail.setContent(data);
            }
        }
    }

    private static List<String> getHead(String data) {
        List<String> lines = new ArrayList<>();

        String[] input = data.split("\r\n");
        int index = 0;
        while (index < input.length && input[index].isEmpty()) {
            index++;
        }
        while (index < input.length && input[index].contains(":")) {
            lines.add(input[index++]);
        }

        return lines;
    }

    private static String getBody(String data) {
        List<String> lines = new ArrayList<>();
        StringBuilder dataBuilder = new StringBuilder();

        String[] input = data.split("\r\n");
        int index = 0;
        while (index < input.length && input[index].isEmpty()) {
            index++;
        }
        while (index < input.length && input[index].contains(":")) {
            index++;
        }

        for (; index < input.length && !".".equals(input[index]); index++) {
            dataBuilder.append(input[index]);
            dataBuilder.append("\r\n");
        }

        return dataBuilder.toString();
    }

    private static List<String> getBodyParts(String data, String boundary) {
        List<String> parts = new ArrayList<>();
        String[] cells = data.split(boundary);
        for (int i = 1; i < cells.length - 1; i++) {
            parts.add(cells[i]);
        }
        return parts;
    }

    private static String getBoundary(Map<String, String> paramMap) {
        String key = paramMap.get("content-type");
        if (!key.contains("BOUNDARY=") && !key.contains("boundary=")
        ) {
            throw new IllegalArgumentException("multipart/mixed not contains boundary");
        } else {
            return "--" + (key.contains("BOUNDARY") ?
                    key.substring(
                    key.indexOf("BOUNDARY=") + 10, key.length() - 1).trim() :
                    key.substring(key.indexOf("boundary=") + 10, key.length() - 1).trim());
        }
    }
}
