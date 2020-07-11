package org.yde.ydeapp.infrastructure.application;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.yde.ydeapp.domain.Application;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DisplayName("Validation du Repository des Applications")
public class RepositoryOfApplicationImplTest {


    private static final String CODE_APP = "AP00001";
    private static final String SHORT_DESCRIPTION = "A short description";
    private static final String LONG_DESCRIPTION = "A long long description";
    private static final String NAME_OF_RESPONSABLE = "Jhon Doe";
    private static final String CODE_APP_NOT_EXIST = "AP99999";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private RepositoryOfApplication repositoryOfApplication;

    @BeforeAll
    static void setup() {

    }

    @Test
    @DisplayName("Find a exiting application")
    public void When_Application_exist_i_should_retrieve_it() {
        // Given
        GivenAApplicationExistInBase();

        // When
        Application application = repositoryOfApplication.retrieveByAppCode(CODE_APP);

        // Then
        assertThat(application).isNotNull();
        assertThat(application.getNameOfResponsable()).isEqualTo(NAME_OF_RESPONSABLE);
    }

    @Test
    @DisplayName("Have a exception when try to find a non exist application")
    public void When_Application_not_exist_i_have_a_EntityNotExist_Exception() {
        // Given

        // When
        Throwable thrown = catchThrowable(() -> {
            Application application = repositoryOfApplication.retrieveByAppCode(CODE_APP_NOT_EXIST);
        });

        // Then
        Assertions.assertThat(thrown).as("Essai recherche application pas présente en base").hasMessage(String.format("Application with %s is not in repository", CODE_APP_NOT_EXIST));
    }

    private void GivenAApplicationExistInBase() {
        ApplicationEntity applicationEntity = new ApplicationEntity();
        applicationEntity.setCodeApp(CODE_APP);
        applicationEntity.setShortDescription(SHORT_DESCRIPTION);
        applicationEntity.setLongDescription(LONG_DESCRIPTION);
        applicationEntity.setNameOfResponsable(NAME_OF_RESPONSABLE);
        testEntityManager.persistAndFlush(applicationEntity);
    }

    @Test
    public void referenceApplication() {
        int i = 1;
        assertThat(i).isZero();
    }
}