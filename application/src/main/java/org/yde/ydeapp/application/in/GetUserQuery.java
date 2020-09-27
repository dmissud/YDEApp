package org.yde.ydeapp.application.in;

import org.yde.ydeapp.domain.application.User;

import java.util.List;

public interface GetUserQuery {

    User getUserByUid (String uid);
    List<User> GetAllUsers();

}
