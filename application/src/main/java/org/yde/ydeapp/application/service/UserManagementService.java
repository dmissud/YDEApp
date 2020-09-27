package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.GetUserQuery;
import org.yde.ydeapp.application.in.ReferenceUserUseCase;
import org.yde.ydeapp.domain.application.User;
import org.yde.ydeapp.domain.out.RepositoryOfUser;

import java.util.List;

@Service
@Transactional
public class UserManagementService implements GetUserQuery, ReferenceUserUseCase {

    private final Logger log = LoggerFactory.getLogger(UserManagementService.class);

    @Autowired
    RepositoryOfUser repositoryOfUser;

    @Override
    public User getUserByUid(String uid) {

        User user = repositoryOfUser.retrieveUserByUid(uid);
        return user;
    }

    @Override
    public List<User> GetAllUsers() {

        List<User> users = repositoryOfUser.retrieveAllUsers();
        return users;
    }

    @Override
    public User referenceNewUser(ReferenceUserCmd referenceUserCmd) {

        User user = new User(referenceUserCmd.getUid(), referenceUserCmd.getPassword(), referenceUserCmd.getRoles());
        return user;
    }
}
