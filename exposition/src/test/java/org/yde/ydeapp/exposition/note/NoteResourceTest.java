package org.yde.ydeapp.exposition.note;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.yde.ydeapp.application.in.GetNoteQuery;
import org.yde.ydeapp.application.in.ReferenceNoteUseCase;
import org.yde.ydeapp.domain.Note;
import org.yde.ydeapp.domain.out.EntityNotFound;


import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(NoteResource.class)
class NoteResourceTest {

    public static final String CODE_APPLICATION = "AP00001";
    public static final String NOTE_TITLE_FIRST = "Note 1";
    public static final String NOTE_CONTENT_FIRST = "Let's start";
    public static final String NOTE_CREATION_DATE_FIRST = "01/01/2020";
    public static final String NOTE_TITLE_SECOND = "Note 2";
    public static final String NOTE_CONTENT_SECOND = "Let's continue";
    public static final String NOTE_CREATION_DATE_SECOND = "01/01/2020";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReferenceNoteUseCase referenceNoteUseCase;

    @MockBean
    private GetNoteQuery getNoteQuery;

    private ReferenceNoteUseCase.ReferenceNoteCmd referenceNoteCmd;

    private List<Note> notes;
    Note noteInit = new Note(NOTE_TITLE_FIRST, NOTE_CONTENT_FIRST, NOTE_CREATION_DATE_FIRST);

    @BeforeEach
    void setup() {

        Note note2nd = new Note(NOTE_TITLE_SECOND, NOTE_CONTENT_SECOND, NOTE_CREATION_DATE_SECOND);
        notes = new ArrayList<>();
        notes.add(noteInit);
        notes.add(note2nd);

        referenceNoteCmd = new ReferenceNoteUseCase.ReferenceNoteCmd(NOTE_TITLE_FIRST, NOTE_CONTENT_FIRST, NOTE_CREATION_DATE_FIRST);
    }

    @Test
    @DisplayName("Retrieve a notes-list from an existing application")
    void testRetrieveAllNotesFromExistingApplication() throws Exception {
        // Given
        Mockito
            .when(getNoteQuery.getApplicationAllNotes(CODE_APPLICATION))
            .thenReturn(notes);

        mockMvc
            // When
            .perform(MockMvcRequestBuilders.get("/api/applications/" + CODE_APPLICATION + "/notes")
                .accept(MediaType.APPLICATION_JSON))
            // Then
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Retrieve a defined note from an existing application")
    void testRetrieveNoteByTitleFromExistingApplication() throws Exception {
        // Given
        Mockito
            .when(getNoteQuery.getApplicationNoteByTitle(CODE_APPLICATION, NOTE_TITLE_FIRST))
            .thenReturn(noteInit);

        mockMvc
            // When
            .perform(MockMvcRequestBuilders.get("/api/applications/" + CODE_APPLICATION + "/notes/" + NOTE_TITLE_FIRST)
                .accept(MediaType.APPLICATION_JSON))
            // Then
            .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Reference a new note to an existing application")
    void testReferenceNewNoteToExistingApplication() throws Exception {
        // Given
        Mockito
            .when(referenceNoteUseCase.referenceNote(any(String.class), any(ReferenceNoteUseCase.ReferenceNoteCmd.class)))
            .thenReturn(noteInit);
        String json = objectMapper.writeValueAsString(referenceNoteCmd);

        mockMvc
            // When
            .perform(MockMvcRequestBuilders
                .post("/api/applications/" + CODE_APPLICATION + "/notes")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            // Then
            .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("Delete a note from an existing application")
    void testDeleteNoteFromExistingApplication() throws Exception {
        // Given
        // rien a mocker la méthode est en void, il faut juste vérifier qu'elle est appellée
        mockMvc
            // When
            .perform(MockMvcRequestBuilders
                .delete("/api/applications/" + CODE_APPLICATION + "/notes/" + NOTE_TITLE_FIRST)
                .accept(MediaType.APPLICATION_JSON))
            // Then
            .andExpect(status().isOk());

        Mockito.verify(referenceNoteUseCase, Mockito.times(1)).deleteNoteByTitle(CODE_APPLICATION, NOTE_TITLE_FIRST);
    }

    @Test
    @DisplayName("Try to Delete a unknow note from an existing application and got a 404 error")
    void testDeleteUnknowNoteFromExistingApplication() throws Exception {
        // Given
        Mockito
            .doThrow(new EntityNotFound(String.format("Note %s does not exist", NOTE_TITLE_FIRST)))
            .when(referenceNoteUseCase).deleteNoteByTitle(CODE_APPLICATION, NOTE_TITLE_FIRST);

        mockMvc
            // When
            .perform(MockMvcRequestBuilders
                .delete("/api/applications/" + CODE_APPLICATION + "/notes/" + NOTE_TITLE_FIRST)
                .accept(MediaType.APPLICATION_JSON))
            // Then
            .andExpect(status().isNotFound());
    }
}
