package org.nuaa.tomax.mailclient.repository;

import org.nuaa.tomax.mailclient.entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Name: IMailRepository
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 16:08
 * @Version: 1.0
 */
public interface IMailRepository extends JpaRepository<MailEntity, Long> {
    @Query(value = "select * from mail where to_mail = ?1 order by time desc limit ?2 offset ?3", nativeQuery = true)
    List<MailEntity> findMailEntitiesByToMail(String to, int page, int offset);
    @Query(value = "select * from mail where from_mail = ?1 order by time desc limit ?2 offset ?3", nativeQuery = true)
    List<MailEntity> findMailEntitiesByFromMail(String from, int page, int offset);

    @Modifying
    @Query(value = "update mail set mail_type = ?1 where id = ?2", nativeQuery = true)
    void updateMailType(int mailType, long id);

}
