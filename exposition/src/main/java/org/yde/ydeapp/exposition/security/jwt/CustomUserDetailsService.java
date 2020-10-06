package org.yde.ydeapp.exposition.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    public static final String ADMIN = "admin";
    public static final String USER = "user";
    @Autowired
    public PasswordEncoder passwordEncoder;

    private static Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    final String[] userRoles = {"ROLE_USER"};
    final String[] adminRoles = {"ROLE_ADMIN"};

    // ici je transforme mes objets du domain en objets internes de Spring Security
    @Override
    public UserDetails loadUserByUsername(final String userName) {
        User user;
        switch (userName) {
            case ADMIN:
                user = new User(ADMIN,
                    passwordEncoder.encode("123"),
                    AuthorityUtils.createAuthorityList(adminRoles));
                break;
            case USER:
                user = new User(USER,
                    passwordEncoder.encode("123"),
                    AuthorityUtils.createAuthorityList(userRoles));
                break;
            default:
                throw new UsernameNotFoundException("Name " + userName + " not found");
        }
        log.debug("Identification of {}", userName);
        return user;
    }
}