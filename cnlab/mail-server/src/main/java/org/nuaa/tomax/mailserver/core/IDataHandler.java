package org.nuaa.tomax.mailserver.core;

/**
 * @Name: IDataHandler
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 10:49
 * @Version: 1.0
 */
public interface IDataHandler {
    /**
     * data handle
     * @param mail mail info
     * @return handle result
     */
    boolean handle(MailBean mail);
}
