package com.pipiolo.security.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeService {

    private final NoticeRepository noticeRepository;

    @Transactional(readOnly = true)
    public List<Notice> findAll() {
        return noticeRepository.findAll(Sort.by(Direction.DESC, "id"));
    }

    @Transactional
    public Notice saveNotice(String title, String content) {
        return noticeRepository.save(new Notice(title, content));
    }

    @Transactional
    public void deleteNotice(Long id) {
        noticeRepository.findById(id).ifPresent(noticeRepository::delete);
    }
}
