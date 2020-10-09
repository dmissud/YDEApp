package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.user.GetUserQuery;

import org.yde.ydeapp.application.in.user.ReferenceUserUseCase;
import org.yde.ydeapp.domain.user.User;
import org.yde.ydeapp.domain.out.EntityAlreadyExist;
import org.yde.ydeapp.domain.out.EntityNotFound;
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
        if (user == null) {
            log.error("User {} not exist", uid);
            throw new EntityNotFound(String.format("User %s does not exist", uid));
        }

        return user;
    }

    @Override
    public List<User> getAllUsers() {

        return repositoryOfUser.retrieveAllUsers();
    }

    @Override
    public User referenceNewUser(ReferenceUserCmd referenceUserCmd) {
        User user = repositoryOfUser.retrieveUserByUid(referenceUserCmd.getUid());
        if (user == null) {
            user = new User(referenceUserCmd.getUid(), referenceUserCmd.getPassword(), referenceUserCmd.getRoles());
            repositoryOfUser.referenceUser(user);
            return user;
        } else {
            log.error("User {} already exists", referenceUserCmd.getUid());
            throw new EntityAlreadyExist(String.format("User %s exists", referenceUserCmd.getUid()));
        }

    }

    @Override
    public User updateExistingUser(ReferenceUserCmd referenceUserCmd, String uid) {
        User user = repositoryOfUser.retrieveUserByUid(uid);
        user.setPassword(referenceUserCmd.getPassword());
        user.setRoles(referenceUserCmd.getRoles());
        repositoryOfUser.updateUser(user);
        return user;
    }

    @Override
    public User deleteUserByUid(String uid) {

        User user = repositoryOfUser.retrieveUserByUid(uid);
        if (user.getUid() != null) {
            repositoryOfUser.deleteUser(user);
        } else {
            log.error("User {} not exist", uid);
            throw new EntityNotFound(String.format("User %s does not exist", uid));
        }
        log.trace("User {} has been deleted.", uid);
        return user;
    }
}
