package org.yde.ydeapp.infrastructure.application;

import org.yde.ydeapp.domain.application.RoleTypeEnum;

import javax.persistence.*;
import java.util.Set;

@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "UID")
    private String uid;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ROLES")
    @ElementCollection
    private Set<RoleTypeEnum> roles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleTypeEnum> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleTypeEnum> roles) {
        this.roles = roles;
    }
}
