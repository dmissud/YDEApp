package org.yde.ydeapp.domain.steps;

public class ApplicationDataTable {
    private final String codeApplication;
    private final String shortDescription;
    private final String longDescription;
    private final String uid;
    private final String firstName;
    private final String lastName;
    private final String noteContent;
    private final String noteVisibility;

    public ApplicationDataTable(String codeApplication, String shortDescription, String longDescription, String uid,
                                String firstName,String lastName, String noteContent, String noteVisibility) {
        this.codeApplication = codeApplication;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.noteContent =  noteContent;
        this.noteVisibility = noteVisibility;

    }


    public String getCodeApplication() { return codeApplication; }

    public String getShortDescription() { return shortDescription; }

    public String getLongDescription() { return longDescription; }

    public String getUid() {
        return uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public String getNoteVisibility() {
        return noteVisibility;
    }
}
