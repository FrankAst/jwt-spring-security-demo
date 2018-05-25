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

@CrossOrigin
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
    public Note createNote(@Valid @RequestBody Note note, @RequestHeader(value = "Authorization") String tk) {
        String token = tk.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        user.addNote(note);
        userRepository.saveAndFlush(user);
        return noteService.add(note);
    }

    @PutMapping("/notes/{id}")
    public Note updateNote(@PathVariable(value = "id") Long noteId, @Valid @RequestBody Note noteDetails, @RequestHeader(value = "Authorization") String tk) {
        String token = tk.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        Note note = noteService.get(noteId);
        note.setTitle(noteDetails.getTitle());
        note.setText(noteDetails.getText());
        user.updateNote(note);
        return noteService.update(note);
    }


    @DeleteMapping("/notes/{id}")
    public Boolean getNoteById(@PathVariable(value = "id") Long noteId, @RequestHeader(value = "Authorization") String tk) {
        String token = tk.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username);
        user.deleteNote(noteId);
        noteService.delete(noteId);
        return true;
    }
}
