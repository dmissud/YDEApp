package org.yde.ydeapp.exposition.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.yde.ydeapp.application.in.GetNoteQuery;
import org.yde.ydeapp.application.in.ReferenceNoteUseCase;
import org.yde.ydeapp.domain.Note;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NoteResource {

    @Autowired
    ReferenceNoteUseCase referenceNoteUseCase;

    @Autowired
    GetNoteQuery getNoteQuery;

    @GetMapping(value = "applications/{codeApplication}/notes", produces = {"application/json"})
    public ResponseEntity<List<Note>> getApplicationAllNotes(
            @NotNull
            @PathVariable("codeApplication") final String codeApplication) {

        List<Note> notes = getNoteQuery.getApplicationAllNotes(codeApplication);

        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping(value = "applications/{codeApplication}/notes/{noteTitle}", produces = {"application/json"})
    public ResponseEntity<Note> getNoteByTitle(
            @NotNull
            @PathVariable("codeApplication") final String codeApplication,
            @PathVariable("noteTitle") final String noteTitle) {

        Note note = getNoteQuery.getApplicationNoteByTitle(codeApplication, noteTitle);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PostMapping("applications/{codeApplication}/notes")
    public ResponseEntity<Void> referenceNote(
            @Valid @RequestBody ReferenceNoteUseCase.ReferenceNoteCmd referenceNoteCmd,
            @PathVariable("codeApplication") final String codeApplication) {

        Note note = referenceNoteUseCase.referenceNote(codeApplication, referenceNoteCmd);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{noteTitle}")
                .buildAndExpand(note.getNoteTitle())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("applications/{codeApplication}/notes/{noteTitle}")
    public ResponseEntity<Void> updateNote(
            @Valid @RequestBody String noteContent,
            @PathVariable("codeApplication") final String codeApplication,
            @PathVariable("noteTitle") final String noteTitle) {
        ReferenceNoteUseCase.ReferenceNoteCmd referenceNoteCmd = new ReferenceNoteUseCase.ReferenceNoteCmd(noteTitle, noteContent, LocalDate.now());
        Note note = referenceNoteUseCase.updateNote(codeApplication, noteTitle, referenceNoteCmd);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{noteTitle}")
                .buildAndExpand(note.getNoteTitle())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("applications/{codeApplication}/notes/{noteTitle}")
    public ResponseEntity<String> deleteNote(
            @PathVariable("codeApplication") final String codeApplication,
            @PathVariable("noteTitle") final String noteTitle) {

        referenceNoteUseCase.deleteNoteByTitle(codeApplication, noteTitle);

        return new ResponseEntity<>(noteTitle, HttpStatus.OK);
    }
}
