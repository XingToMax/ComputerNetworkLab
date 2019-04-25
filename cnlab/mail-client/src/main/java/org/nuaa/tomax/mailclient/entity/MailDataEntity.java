package org.nuaa.tomax.mailclient.entity;

import lombok.Data;

import java.io.File;
import java.util.ArrayList;
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
    private String time;
    private File[] attachments;
    private List<String> fileNameList;
    private List<String> fileTypeList;
    private List<String> filePathList;

    public MailDataEntity() {
        fileNameList = new ArrayList<>();
        fileTypeList = new ArrayList<>();
        filePathList = new ArrayList<>();
    }
}
