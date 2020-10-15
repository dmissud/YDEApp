package org.yde.ydeapp.application.in.user;

import org.yde.ydeapp.domain.user.User;
import org.yde.ydeapp.domain.user.UserDesc;

import java.util.List;

public interface GetUserQuery {

    User getUserByUid (String uid);
    List<UserDesc> getAllUsers();

}
