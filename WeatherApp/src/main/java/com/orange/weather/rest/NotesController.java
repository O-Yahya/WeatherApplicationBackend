package com.orange.weather.rest;

import com.orange.weather.DTO.NoteDTO;
import com.orange.weather.DTO.ReturnedNoteDTO;
import com.orange.weather.services.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import java.security.Principal;

@RestController
@RequestMapping("api/notes")
public class NotesController {

    private NoteService noteService;

    public NotesController(NoteService noteService){
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<ReturnedNoteDTO> createNote(@RequestBody NoteDTO noteDTO, Principal principal){
        ReturnedNoteDTO returnedNoteDTO = noteService.createNote(noteDTO, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(returnedNoteDTO);
    }

    @GetMapping
    public ResponseEntity<List<ReturnedNoteDTO>>  getAllNotes(){
        List<ReturnedNoteDTO> notes = noteService.viewNotes();
        return ResponseEntity.status(HttpStatus.CREATED).body(notes);
    }

    @GetMapping("/mynotes")
    public ResponseEntity<List<ReturnedNoteDTO>> getAdminNotes(Principal principal){
        List<ReturnedNoteDTO> userNotes = noteService.findNotesByUser(principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(userNotes);
    }

    @GetMapping("/todaysnotes")
    public ResponseEntity<List<ReturnedNoteDTO>> getTodaysNotes(){
        List<ReturnedNoteDTO> notes = noteService.findNoteByDate();
        return ResponseEntity.status(HttpStatus.FOUND).body(notes);
    }

}

