package org.nuaa.tomax.mailserver.repository;

import org.nuaa.tomax.mailserver.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Name: IUserRepository
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 16:04
 * @Version: 1.0
 */
public interface IUserRepository extends JpaRepository<UserEntity, Long> {

}
