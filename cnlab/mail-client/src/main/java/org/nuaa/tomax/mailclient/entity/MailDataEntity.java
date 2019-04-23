package org.nuaa.tomax.mailclient.entity;

import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * @Name: MailDataEntity
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-23 21:41
 * @Version: 1.0
 */
@Data
public class MailDataEntity {
    private String from;
    private String to;
    private String subject;
    private String cc;
    private String content;
    private File[] attachments;
    private List<String> fileNameList;
    private List<String> fileTypeList;
}
