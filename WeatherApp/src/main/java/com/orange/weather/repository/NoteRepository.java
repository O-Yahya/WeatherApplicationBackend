package com.orange.weather.repository;

import com.orange.weather.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    List<Note> findByDate(LocalDate date);
}
