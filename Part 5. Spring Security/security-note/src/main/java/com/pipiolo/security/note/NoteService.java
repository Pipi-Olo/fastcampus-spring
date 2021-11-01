package com.pipiolo.security.note;

import com.pipiolo.security.user.User;
import com.pipiolo.security.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteService {

    private final NoteRepository noteRepository;

    @Transactional(readOnly = true)
    public List<Note> findByUser(User user) {
        if (user == null)
            throw new UserNotFoundException();

        if (user.isAdmin())
            return noteRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));

        return noteRepository.findByUserOrderByIdDesc(user);
    }

    @Transactional
    public Note saveNote(User user, String title, String content) {
        if (user == null)
            throw new UserNotFoundException();

        return noteRepository.save(new Note(title, content, user));
    }

    @Transactional
    public void deleteNote(User user, Long noteId) {
        if (user == null)
            throw new UserNotFoundException();

        Note note = noteRepository.findByIdAndUser(noteId, user);
        if (note != null)
            noteRepository.delete(note);
    }
}
