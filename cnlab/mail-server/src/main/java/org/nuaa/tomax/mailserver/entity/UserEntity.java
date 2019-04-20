package org.nuaa.tomax.mailserver.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @Name: UserEntity
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 16:05
 * @Version: 1.0
 */
@Entity
@Data
@Table(name = "user")
public class UserEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}
