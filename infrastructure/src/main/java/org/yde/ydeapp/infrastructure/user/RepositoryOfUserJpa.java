package org.yde.ydeapp.infrastructure.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.yde.ydeapp.domain.user.User;

import java.util.List;

public interface RepositoryOfUserJpa extends JpaRepository<UserEntity, Integer> {

    UserEntity findByUid(String uid);

    @Query(value = "SELECT new org.yde.ydeapp.domain.user.User(user.uid, user.password, user.roles) FROM UserEntity user")
    List<User> retrieveAllUsers();

}
