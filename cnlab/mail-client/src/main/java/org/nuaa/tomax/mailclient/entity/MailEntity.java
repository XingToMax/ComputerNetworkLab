package org.nuaa.tomax.mailclient.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @Name: MailEntity
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 16:06
 * @Version: 1.0
 */
@Entity
@Data
@Table(name = "mail")
public class MailEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String from;
    private String to;
    private String data;
    private int mailType;
    private Timestamp time;
    private String prop;
}
