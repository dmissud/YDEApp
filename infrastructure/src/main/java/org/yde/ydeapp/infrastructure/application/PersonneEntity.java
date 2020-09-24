package org.yde.ydeapp.infrastructure.application;

import javax.persistence.*;

@Embeddable
public class PersonneEntity {
    private String uid;
    private String firstName;
    private String lastName;

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
