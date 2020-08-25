package org.yde.ydeapp.infrastructure.application;

import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Date;

@Entity
public class CycleLifeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    private String state;
    private Date dateOfCreation;
    private Date dateOfLastUpdate;
    private Date dateEndInReality;

    public long getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public Date getDateOfLastUpdate() {
        return dateOfLastUpdate;
    }

    public Date getDateEndInReality() {
        return dateEndInReality;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setDateOfLastUpdate(Date dateOfLastUpdate) {
        this.dateOfLastUpdate = dateOfLastUpdate;
    }

    public void setDateEndInReality(Date dateEndInReality) {
        this.dateEndInReality = dateEndInReality;
    }
}
