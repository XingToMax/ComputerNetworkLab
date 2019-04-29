package org.nuaa.tomax.mailclient.entity;

import lombok.Data;

/**
 * @Name: CountDataEntity
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-27 10:59
 * @Version: 1.0
 */
@Data
public class CountDataEntity {
    private int receiveNum;
    private int sendNum;
    private int unreadNum;

    public CountDataEntity(){}

    public CountDataEntity(int receiveNum, int sendNum, int unreadNum) {
        this.receiveNum = receiveNum;
        this.sendNum = sendNum;
        this.unreadNum = unreadNum;
    }
}
