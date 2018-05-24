package org.zerhusen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.zerhusen.model.security.Note;
import org.zerhusen.model.security.User;
import org.zerhusen.security.JwtTokenUtil;
import org.zerhusen.security.JwtUser;
import org.zerhusen.security.repository.UserRepository;
import org.zerhusen.security.service.NoteService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;


@RestController
public class NoteController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private NoteService noteService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/notes")
    public List<Note> getAllNotes(@RequestHeader(value = "Authorization") String tk) {
        String token = tk.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        return user.getNotes();
    }

    @PostMapping("/notes")
    public Note createNote(@Valid @RequestBody Note note, @RequestHeader(value = "Authorization") String tk) throws Exception {
        String token = tk.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        note.setUser(user);
        user.addNote(note);
        userRepository.saveAndFlush(user);
        return noteService.add(note);
    }

//    @PutMapping("/notes")
//    public Note updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetails) {
//
//        Note note = noteService.get(noteId);
//
//        note.setTitle(noteDetails.getTitle());
//        note.setText(noteDetails.getText());
//
//        Note updatedNote = noteService.update(note);
//        return updatedNote;
//
//    }
//
//
//    @DeleteMapping("/notes/{id}")
//    public Boolean getNoteById(@PathVariable(value = "id") Long noteId) {
//        noteService.delete(noteId);
//        return true;
//    }
}
