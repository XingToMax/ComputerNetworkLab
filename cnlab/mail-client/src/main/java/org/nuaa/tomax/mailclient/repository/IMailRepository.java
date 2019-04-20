package org.nuaa.tomax.mailclient.repository;

import org.nuaa.tomax.mailclient.entity.MailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Name: IMailRepository
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 16:08
 * @Version: 1.0
 */
public interface IMailRepository extends JpaRepository<MailEntity, Long> {
}
