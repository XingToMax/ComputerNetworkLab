package org.nuaa.tomax.mailclient.repository;

import org.nuaa.tomax.mailclient.entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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
    List<MailEntity> findMailEntitiesByToMail(String to);

    List<MailEntity> findMailEntitiesByFromMail(String to);

    @Query(value = "update mail set mail_type = ?1 where id = ?2", nativeQuery = true)
    void updateMailType(int mailType, long id);
}
