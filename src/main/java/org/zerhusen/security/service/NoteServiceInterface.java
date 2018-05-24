package org.zerhusen.security.service;

import org.zerhusen.model.security.Note;

import java.util.List;

public interface NoteServiceInterface {
    Note add(Note note);

    void delete(Long id);

    Note update(Note note);

    Note get(Long id);

    List<Note> getAll();
}
