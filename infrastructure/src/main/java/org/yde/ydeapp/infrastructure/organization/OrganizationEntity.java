package org.yde.ydeapp.infrastructure.organization;

import org.yde.ydeapp.infrastructure.application.ApplicationEntity;

import javax.persistence.*;
import java.util.List;

@Entity
public class OrganizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String idRefog;

    private String name;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrganizationEntity> children;

    @OneToMany(mappedBy = "organisation", cascade = CascadeType.ALL)
    private List<ApplicationEntity> applications;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ApplicationEntity> getApplications() {
        return applications;
    }

    public void setApplications(List<ApplicationEntity> applications) {
        this.applications = applications;
    }

    public String getIdRefog() {
        return idRefog;
    }

    public void setIdRefog(String idRefog) {
        this.idRefog = idRefog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<OrganizationEntity> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationEntity> children) {
        this.children = children;
    }

}
