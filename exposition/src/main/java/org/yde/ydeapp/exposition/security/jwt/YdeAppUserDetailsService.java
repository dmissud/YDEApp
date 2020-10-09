package org.yde.ydeapp.exposition.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.user.GetUserQuery;
import org.yde.ydeapp.domain.user.User;
import org.yde.ydeapp.domain.out.EntityNotFound;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Transactional
public class YdeAppUserDetailsService implements UserDetailsService {

    public static final String ADMIN = "admin";
    public static final String USER = "user";

    @Autowired
    GetUserQuery getUserQuery;

    @Autowired
    public PasswordEncoder passwordEncoder;

    private static Logger log = LoggerFactory.getLogger(YdeAppUserDetailsService.class);
    final String[] userRoles = {"ROLE_USER"};
    final String[] adminRoles = {"ROLE_ADMIN"};

    // ici je transforme mes objets du domain en objets internes de Spring Security
    @Override
    public UserDetails loadUserByUsername(final String uid) {
        User user;
        UserDetails userDetails;
        try {
            user = getUserQuery.getUserByUid(uid);
            log.debug("Identification of {}", uid);
            userDetails = new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {

                    return user.getRoles()
                        .stream()
                        .map(role -> (GrantedAuthority) role::toString)
                        .collect(Collectors.toList());
                }

                @Override
                public String getPassword() {
                    return user.getPassword();
                }

                @Override
                public String getUsername() {
                    return user.getUid();
                }

                @Override
                public boolean isAccountNonExpired() {
                    return true;
                }

                @Override
                public boolean isAccountNonLocked() {
                    return true;
                }

                @Override
                public boolean isCredentialsNonExpired() {
                    return true;
                }

                @Override
                public boolean isEnabled() {
                    return true;
                }
            };
            return userDetails;

        } catch (EntityNotFound entityNotFound) {
            log.debug("User {} not exitent", uid);
            throw new UsernameNotFoundException(entityNotFound.getMessage());
        }
    }
}