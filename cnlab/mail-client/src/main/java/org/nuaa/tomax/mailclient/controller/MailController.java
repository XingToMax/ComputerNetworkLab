package org.nuaa.tomax.mailclient.controller;

import lombok.extern.java.Log;
import org.nuaa.tomax.mailclient.core.AttachmentCache;
import org.nuaa.tomax.mailclient.core.MailBean;
import org.nuaa.tomax.mailclient.entity.Response;
import org.nuaa.tomax.mailclient.service.IMailService;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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

    @GetMapping("/download/file")
    public ResponseEntity<Resource> downloadFile(String filename, HttpServletRequest request) throws UnsupportedEncodingException {
        // TODO : same with the function in reource controller , optimize it by add file upload and download controller
        Resource resource = mailService.downloadFile(filename);
        if (resource == null) {
            return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
        }
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(resource.getFilename(), "UTF-8") + "\"")
                .body(resource);
    }

    @GetMapping("/count/data")
    public Response getCountData(HttpSession session) {
        String username = (String) session.getAttribute("username");
        return mailService.countData(username);
    }
}
