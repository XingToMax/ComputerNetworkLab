package org.nuaa.tomax.mailclient.controller;

import org.nuaa.tomax.mailclient.core.MailBean;
import org.nuaa.tomax.mailclient.entity.Response;
import org.springframework.web.bind.annotation.*;

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
public class MailController {
    @PostMapping("/send")
    public Response send(MailBean mail) {
        return null;
    }

    @GetMapping("/sendMailList")
    public Response getSendMailList() {
        return null;
    }

    @GetMapping("/receiveMailList")
    public Response getReceiveList() {
        return null;
    }
}
