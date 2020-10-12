package org.yde.ydeapp.domain.out;

import org.yde.ydeapp.domain.user.User;
import org.yde.ydeapp.domain.user.UserDesc;

import java.util.List;

public interface RepositoryOfUser {

    User retrieveUserByUid(String uid);
    List<UserDesc> retrieveAllUsers();

    void referenceUser(User user);
    void updateUser(User user);
    void deleteUser(User user);
}
