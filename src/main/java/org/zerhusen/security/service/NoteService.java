package org.zerhusen.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerhusen.model.security.Note;
import org.zerhusen.security.repository.NoteRepository;

import java.util.List;

@Service
public class NoteService implements NoteServiceInterface {
    @Autowired
    private NoteRepository noteRepository;

    @Override
    public Note add(Note note) {
        return noteRepository.saveAndFlush(note);
    }

    @Override
    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    @Override
    public Note update(Note note) {
        return noteRepository.saveAndFlush(note);
    }

    @Override
    public Note get(Long id) {
        return noteRepository.getOne(id);
    }

    @Override
    public List<Note> getAll() {
        return noteRepository.findAll();
    }
}
