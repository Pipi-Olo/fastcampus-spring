package com.pipiolo.security.note;

import com.pipiolo.security.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Note findByIdAndUser(Long noteId, User user);

    List<Note> findByUserOrderByIdDesc(User user);
}
