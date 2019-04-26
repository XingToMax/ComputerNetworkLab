package org.nuaa.tomax.mailclient.controller;

import lombok.extern.java.Log;
import org.nuaa.tomax.mailclient.core.AttachmentCache;
import org.nuaa.tomax.mailclient.core.MailBean;
import org.nuaa.tomax.mailclient.entity.Response;
import org.nuaa.tomax.mailclient.service.IMailService;
import org.springframework.core.env.Environment;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

/**
 * @Name: MailController
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 17:03
 * @Version: 1.0
 */
@RequestMapping("/mail")
@RestController
@CrossOrigin
@Log
public class MailController {

    private final IMailService mailService;
    private final String tmpPath;

    public MailController(IMailService mailService, Environment environment) {
        this.mailService = mailService;
        this.tmpPath = environment.getProperty("mail.attachments.cache.path");
    }

    @PostMapping("/send")
    public Response send(MailBean mail, HttpSession session) {
        String username = (String) session.getAttribute("username");
        mail.setUser(username);
        return mailService.sendMail(mail);
    }

    @GetMapping("/sendMailList")
    public Response getSendMailList(int page, int limit, HttpSession session) {
        String username = (String) session.getAttribute("username");
        return mailService.getSendMailList(username, page, limit);
    }

    @GetMapping("/receiveMailList")
    public Response getReceiveList(int page, int limit, HttpSession session) {
        String username = (String) session.getAttribute("username");
        return mailService.getRecieveMailList(username, page, limit);
    }

    @PostMapping("/upload/attachment")
    public Response uploadAttachmentFile(MultipartFile file, HttpSession session) {
        String username = (String) session.getAttribute("username");
        File tmp = new File(tmpPath + file.getOriginalFilename());
        try {
            FileCopyUtils.copy(file.getBytes(), tmp);
        } catch (IOException e) {
            log.info("upload file error : " + e.getMessage());
        }

        AttachmentCache.addAttachment(username, tmp);

        return new Response(Response.SUCCESS_CODE, "upload file success");
    }

    @DeleteMapping("/mail/{id}")
    public Response deleteMail(@PathVariable(name = "id")Long id) {
        return mailService.removeMail(id);
    }

    @PatchMapping("/mail/{id}")
    public Response updateMailState(@PathVariable(name = "id")Long id) {
        return mailService.updateMailRead(id);
    }

    @GetMapping("/mail/{id}")
    public Response getMailData(@PathVariable(name = "id") Long id) {
        return mailService.getMailById(id);
    }


}
