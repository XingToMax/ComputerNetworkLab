package org.nuaa.tomax.mailclient.service.impl;

import org.nuaa.tomax.mailclient.core.AttachmentCache;
import org.nuaa.tomax.mailclient.core.MailBean;
import org.nuaa.tomax.mailclient.core.MailDataParser;
import org.nuaa.tomax.mailclient.core.Sender;
import org.nuaa.tomax.mailclient.entity.*;
import org.nuaa.tomax.mailclient.repository.IMailRepository;
import org.nuaa.tomax.mailclient.repository.IUserRepository;
import org.nuaa.tomax.mailclient.service.IMailService;
import org.nuaa.tomax.mailclient.utils.Base64Wrapper;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Name: MailServiceImpl
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-23 21:44
 * @Version: 1.0
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class MailServiceImpl implements IMailService {

    private final IMailRepository mailRepository;
    private final IUserRepository userRepository;
    private final String host;
    private final String savePath;
    private final String tmpPath;
    public MailServiceImpl(IMailRepository mailRepository, IUserRepository userRepository, Environment environment) {
        this.mailRepository = mailRepository;
        this.userRepository = userRepository;
        this.host = environment.getProperty("mail.host");
        this.savePath = environment.getProperty("mail.attachments.cache.save.path");
        this.tmpPath = environment.getProperty("mail.attachments.cache.path");
    }

    @Override
    public Response sendMail(MailBean mail) {
        UserEntity user = userRepository.findUserEntityByUsername(mail.getUser());
        mail.setAttachments(AttachmentCache.getAttachmentList(mail.getUser()));
        mail.setUser(Base64Wrapper.encode(user.getUsername()));
        mail.setPassword(user.getPassword());
        mail.setFrom(user.getUsername() + "@" + host);
        Sender sender = new Sender(host);
        if (sender.send(mail).size() != 0) {
            return new Response(Response.SERVER_ERROR_CODE, "mail send error");
        }
        return new Response(Response.SUCCESS_CODE, "mail send success");
    }

    @Override
    public Response getRecieveMailList(String username, int page, int limit) {
        return new Response<MailDataEntity>(
                Response.SUCCESS_CODE,
                "get data list success",
                parse(mailRepository.findMailEntitiesByToMail(username + "@" + host, limit, (page - 1) * limit))
        );
    }

    @Override
    public Response getSendMailList(String username, int page, int limit) {
        return new Response<MailDataEntity>(
                Response.SUCCESS_CODE,
                "get data list success",
                parse(mailRepository.findMailEntitiesByFromMail(username + "@" + host, limit, (page - 1) * limit))
        );
    }

    @Override
    public Response getMailById(long id) {
        updateMailRead(id);
        return new Response<MailDataEntity>(
                Response.SUCCESS_CODE,
                "get data success",
                MailDataParser.parse(mailRepository.findById(id).orElseGet(MailEntity::new), savePath)
        );
    }

    @Override
    public Response removeMail(long id) {
        mailRepository.deleteById(id);
        return new Response(Response.SUCCESS_CODE, "delete mail success");
    }



    @Override
    public Response updateMailRead(long id) {
        mailRepository.updateMailType(4, id);
        return null;
    }

    @Override
    public Resource downloadFile(String filename) {
        // TODO : use savePath instead
        try {
            return new FileUrlResource(tmpPath + filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Response countData(String username) {
        String mail = username + "@" + host;
        CountDataEntity dataEntity = new CountDataEntity(
                mailRepository.countByToMail(mail),
                mailRepository.countByFromMail(mail),
                mailRepository.countByToMailAndMailType(mail, 4)
        );
        return new Response<CountDataEntity>(
                Response.SUCCESS_CODE,
                "get data success",
                dataEntity
        );
    }

    private List<MailDataEntity> parse(List<MailEntity> srcList) {
        return srcList.stream()
                .map(MailDataParser::lightParse)
                .collect(Collectors.toList());
    }
}
