package org.zerhusen.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerhusen.model.security.Note;

public interface NoteRepository extends JpaRepository<Note, Long> {

}
