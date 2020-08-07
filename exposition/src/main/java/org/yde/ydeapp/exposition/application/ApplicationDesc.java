package org.yde.ydeapp.exposition.application;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ApplicationDesc {

    @ApiModelProperty(example = "Code Application", required = true, value = "Code of application reference")
    @NotNull
    @Pattern(regexp = "^(AP[0-9]{5})$")
    @Size(max = 30)
    private String codeApplication;

    @ApiModelProperty(example = "Application presentation", required = true, value = "a short description")
    @NotNull
    @Pattern(regexp = "^([ a-zA-Z0-9]{1,30})$")
    @Size(max = 30)
    private String shortDescription;

    @ApiModelProperty(example = "Application description", value = "a long description")
    @NotNull
    @Size(max = 300)
    private String longDescription;

    @ApiModelProperty(example = "123456", required = true, value = "Uid of the responsible")
    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9]{6})$")
    @Size(max = 10)
    private String uid;

    @ApiModelProperty(example = "John", required = true, value = "Firstname of the responsible")
    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9]{1,30})$")
    @Size(max = 30)
    private String firstName;

    @ApiModelProperty(example = "Doe", required = true, value = "Lastname of the responsible")
    @NotNull
    @Pattern(regexp = "^([a-zA-Z0-9]{1,30})$")
    @Size(max = 100)
    private String lastName;

    public String getCodeApplication() { return codeApplication; }

    public void setCodeApplication(String codeApplication) {
        this.codeApplication = codeApplication;
    }

    public String getShortDescription() { return shortDescription; }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLongDescription() { return longDescription; }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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
