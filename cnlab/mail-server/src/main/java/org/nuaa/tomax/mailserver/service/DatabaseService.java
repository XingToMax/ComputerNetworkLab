package org.nuaa.tomax.mailserver.service;

import org.nuaa.tomax.mailserver.entity.MailEntity;

/**
 * @Name: DatabaseService
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 16:09
 * @Version: 1.0
 */
public interface DatabaseService {
    boolean userExists(String username);
    boolean checkAuthPassword(String username, String password);
    void saveMail(MailEntity mail);
}
