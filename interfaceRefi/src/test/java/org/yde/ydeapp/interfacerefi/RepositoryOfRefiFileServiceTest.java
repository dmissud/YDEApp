package org.yde.ydeapp.interfacerefi;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.yde.ydeapp.application.service.RepositoryOfRefiFileService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@DisplayName("Validation du Repository du flux refi")
class RepositoryOfRefiFileServiceTest {


    @Autowired
    private RepositoryOfRefiFileService repositoryOfRefiFileService;


}