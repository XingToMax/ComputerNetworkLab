package org.nuaa.tomax.mailserver.service;

import org.nuaa.tomax.mailserver.entity.MailEntity;
import org.nuaa.tomax.mailserver.repository.IMailRepository;
import org.nuaa.tomax.mailserver.repository.IUserRepository;
import org.springframework.stereotype.Service;

/**
 * @Name: DatabaseServiceImpl
 * @Description: TODO
 * @Author: tomax
 * @Date: 2019-04-20 16:11
 * @Version: 1.0
 */
@Service(value = "databaseService")
public class DatabaseServiceImpl implements DatabaseService{
    private final IUserRepository userRepository;
    private final IMailRepository mailRepository;

    public DatabaseServiceImpl(IUserRepository userRepository, IMailRepository mailRepository) {
        this.userRepository = userRepository;
        this.mailRepository = mailRepository;
    }

    @Override
    public boolean userExists(String username) {
        return false;
    }

    @Override
    public boolean checkAuthPassword(String username, String password) {
        return false;
    }

    @Override
    public void saveMail(MailEntity mail) {
        mailRepository.save(mail);
    }
}
