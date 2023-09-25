package com.orange.weather.services;

import com.orange.weather.DTO.NoteDTO;
import com.orange.weather.DTO.ReturnedNoteDTO;
import com.orange.weather.entity.Note;
import com.orange.weather.entity.User;
import com.orange.weather.exception.InvalidDetailsException;
import com.orange.weather.repository.NoteRepository;
import com.orange.weather.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private UserRepository userRepo;
    private NoteRepository noteRepo;
    private static final Logger logger = LoggerFactory.getLogger(NoteService.class);

    public NoteService(UserRepository userRepo, NoteRepository noteRepo){
        this.userRepo = userRepo;
        this.noteRepo = noteRepo;
    }

    // method for creating a new note
    public ReturnedNoteDTO createNote(NoteDTO noteDTO, String email){
        logger.info("Method createNote in " + getClass().getSimpleName() + " called");
        logger.debug("Parameters: NoteDTO " + noteDTO.toString() + ", String " + email);
        if (noteDTO.getContent() == null || noteDTO.getContent().isEmpty()){
            throw new InvalidDetailsException("Note content cannot be empty!");
        }
        Note note = new Note();
        note.setContent(noteDTO.getContent());
        Optional<User> user = userRepo.findByEmail(email);
        if (user.isPresent()){
            note.setUser(user.get());
        }
        else {
            throw new RuntimeException("Not authenticated");
        }
        LocalDate date = LocalDate.now();
        note.setDate(date);

        Note savedNote = noteRepo.save(note);
        return new ReturnedNoteDTO(savedNote.getContent(), savedNote.getDate());
    }

    // method for getting all notes
    public List<ReturnedNoteDTO> viewNotes(){
        logger.info("Method viewNotes in " + getClass().getSimpleName() + " called");
        logger.debug("Parameters: None");
        List<Note> notes = noteRepo.findAll();
        List<ReturnedNoteDTO> notesInfo = new ArrayList<>();

        for (Note n : notes){
            notesInfo.add(new ReturnedNoteDTO(n.getContent(), n.getDate()));
        }

        return notesInfo;
    }

    // method for getting all notes created by a user based on their email
    public List<ReturnedNoteDTO> findNotesByUser(String email){
        logger.info("Method findNotesByUser in " + getClass().getSimpleName() + " called");
        logger.debug("Parameters: String " + email);
        Optional<User> search = userRepo.findByEmail(email);
        User user = new User();
        if (search.isPresent()){
            user = search.get();
        }
        else {
            throw new RuntimeException("Not authenticated");
        }

        List<Note> userNotes = user.getNotes();
        List<ReturnedNoteDTO> notesInfo = new ArrayList<>();
        for (Note n : userNotes){
            notesInfo.add(new ReturnedNoteDTO(n.getContent(), n.getDate()));
        }

        return notesInfo;
    }

    // method for getting all notes for the current day
    public List<ReturnedNoteDTO> findNoteByDate(){
        logger.info("Method findNoteByDate in " + getClass().getSimpleName() + " called");
        logger.debug("Parameters: None");
        List<Note> notes = noteRepo.findByDate(LocalDate.now());
        List<ReturnedNoteDTO> notesInfo = new ArrayList<>();
        for (Note n : notes){
            notesInfo.add(new ReturnedNoteDTO(n.getContent(), n.getDate()));
        }
        return notesInfo;
    }
}
