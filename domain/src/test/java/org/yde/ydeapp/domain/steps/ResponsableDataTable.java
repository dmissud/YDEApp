package org.yde.ydeapp.domain.steps;

public class ResponsableDataTable {
    private final String uid;
    private final String firstName;
    private final String lastName;

    public ResponsableDataTable(String uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
