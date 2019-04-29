package org.nuaa.tomax.mailclient.service;

import org.nuaa.tomax.mailclient.core.MailBean;
import org.nuaa.tomax.mailclient.entity.MailDataEntity;
import org.nuaa.tomax.mailclient.entity.Response;
import org.springframework.core.io.Resource;

/**
 * @Name: IMailService
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-23 21:40
 * @Version: 1.0
 */
public interface IMailService {
    Response sendMail(MailBean mail);

    Response getRecieveMailList(String username, int page, int limit);

    Response getSendMailList(String username, int page, int limit);

    Response removeMail(long id);

    Response updateMailRead(long id);

    Response getMailById(long id);

    Resource downloadFile(String filename);

    Response countData(String username);
}
