package com.pipiolo.security.admin;

import com.pipiolo.security.note.Note;
import com.pipiolo.security.note.NoteService;
import com.pipiolo.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {

    private final NoteService noteService;

    @GetMapping
    public String getNoteForAmdin(Authentication authentication, Model model) {
        User user = (User) authentication.getPrincipal();
        List<Note> notes = noteService.findByUser(user);
        model.addAttribute("notes", notes);

        return "admin/index";
    }

}
