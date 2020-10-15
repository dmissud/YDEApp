package org.yde.ydeapp.infrastructure.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.yde.ydeapp.domain.out.EntityAlreadyExist;
import org.yde.ydeapp.domain.out.EntityNotFound;
import org.yde.ydeapp.domain.out.RepositoryOfUser;
import org.yde.ydeapp.domain.user.User;
import org.yde.ydeapp.domain.user.UserDesc;

import java.util.List;


@Repository
public class RepositoryOfUserImpl implements RepositoryOfUser {

    private static final Logger log = LoggerFactory.getLogger(RepositoryOfUserImpl.class);

    @Autowired
    RepositoryOfUserJpa repositoryOfUserJpa;

    @Override
    public User retrieveUserByUid(final String uid) {
        UserEntity userEntity = repositoryOfUserJpa.findByUid(uid);
        User user = null;
        if (userEntity != null) {
            user = mapEntityToDomain(userEntity);
        }
        return user;
    }

    @Override
    public List<UserDesc> retrieveAllUsers() {
        return repositoryOfUserJpa.retrieveAllUsers();
    }

    @Override
    public void referenceUser(final User user) {
        UserEntity userEntity = repositoryOfUserJpa.findByUid(user.getUid());
        if (userEntity != null) {
            log.debug("User {} already exists", userEntity.getUid());
            throw new EntityAlreadyExist(String.format("User with %s is in repository", userEntity.getUid()));
        } else {
            userEntity = new UserEntity();
            mapDomainToEntity(user, userEntity);
            repositoryOfUserJpa.save(userEntity);
        }
    }

    @Override
    public void updateUser(User user) {
        UserEntity userEntity = repositoryOfUserJpa.findByUid(user.getUid());
        if (userEntity == null) {
            log.error("User {} does not exist", user.getUid());
            throw new EntityNotFound(String.format("User with %s is not in repository", user.getUid()));
        } else {
            mapDomainToEntity(user, userEntity);
            repositoryOfUserJpa.save(userEntity);
        }
    }

    @Override
    public void deleteUser(User user) {
        UserEntity userEntity = repositoryOfUserJpa.findByUid(user.getUid());
        if (userEntity == null) {
            log.error("User {} does not exist", user.getUid());
            throw new EntityNotFound(String.format("User with %s is not in repository", user.getUid()));
        } else {
            repositoryOfUserJpa.delete(userEntity);
        }
    }

    private void mapDomainToEntity(User user, UserEntity userEntity) {
        userEntity.setUid(user.getUid());
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());
        userEntity.setPassword(user.getPassword());
        userEntity.setRoles(user.getRoles());
    }

    private User mapEntityToDomain(UserEntity userEntity) {
        User user;
        user = new User(userEntity.getUid(),
            userEntity.getFirstName(),
            userEntity.getLastName(),
            userEntity.getPassword(),
            userEntity.getRoles());
        return user;
    }


}
