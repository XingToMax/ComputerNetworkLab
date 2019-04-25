package org.nuaa.tomax.mailclient.core;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Name: AttachmentCache
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-25 21:03
 * @Version: 1.0
 */
public class AttachmentCache {
    private static ConcurrentHashMap<String, List<File>> attachmentCache = new
            ConcurrentHashMap<>(100);

    public static void addAttachment(String username, File attachment) {
        if (attachmentCache.containsKey(username)) {
            attachmentCache.get(username).add(attachment);
        } else {
            List<File> attachments = new LinkedList<>();
            attachments.add(attachment);
            attachmentCache.put(username, attachments);
        }
    }

    public static List<File> getAttachmentList(String username) {
        if (!attachmentCache.containsKey(username)) {
            return null;
        }
        List<File> attachments = attachmentCache.get(username);
        attachmentCache.remove(username);
        return attachments;
    }

}
