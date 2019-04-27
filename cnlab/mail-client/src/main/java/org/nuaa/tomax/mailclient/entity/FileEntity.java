package org.nuaa.tomax.mailclient.entity;

import lombok.Data;

/**
 * @Name: FileEntity
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-26 22:18
 * @Version: 1.0
 */
@Data
public class FileEntity {
    private String name;
    private String path;
    private String type;
    private int size;
    private String data;
}
