package org.yde.ydeapp.application.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yde.ydeapp.application.in.GetApplicationQuery;
import org.yde.ydeapp.application.in.ReferenceNoteUseCase;
import org.yde.ydeapp.domain.out.RepositoryOfApplication;

@Service
@Transactional
public class NoteManagementService implements ReferenceNoteUseCase, GetApplicationQuery {

    private final Logger log = LoggerFactory.getLogger(NoteManagementService.class);

    @Autowired
    RepositoryOfApplication repositoryOfApplication;


}
