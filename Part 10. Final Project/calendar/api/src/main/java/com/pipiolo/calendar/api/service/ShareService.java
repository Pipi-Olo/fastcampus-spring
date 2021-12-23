package com.pipiolo.calendar.api.service;

import com.pipiolo.calendar.api.dto.user.AuthUser;
import com.pipiolo.calendar.core.domain.RequestStatus;
import com.pipiolo.calendar.core.domain.entity.Share;
import com.pipiolo.calendar.core.domain.entity.User;
import com.pipiolo.calendar.core.domain.repository.ShareRepository;
import com.pipiolo.calendar.core.domain.type.RequestReplyType;
import com.pipiolo.calendar.core.excpetion.CalendarException;
import com.pipiolo.calendar.core.excpetion.ErrorCode;
import com.pipiolo.calendar.core.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class ShareService {
    private final UserService userService;
    private final ShareRepository shareRepository;
    private final EmailService emailService;

    @Transactional
    public Share createShare(Long fromUserId, Long toUserId, Share.Direction direction) {
        final User toUser = userService.findByUserId(toUserId);
        final User fromUser = userService.findByUserId(fromUserId);
        emailService.sendShareRequestMail(toUser.getEmail(), fromUser.getEmail(), direction);
        return shareRepository.save(Share.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .direction(direction)
                .requestStatus(RequestStatus.REQUESTED)
                .build());
    }

    @Transactional
    public void replyToShareRequest(Long shareId, Long toUserId, RequestReplyType type) {
        shareRepository.findById(shareId)
                .filter(s -> s.getToUserId()
                        .equals(toUserId))
                .filter(s -> s.getRequestStatus() == RequestStatus.REQUESTED)
                .map(share -> share.reply(type))
                .orElseThrow(() -> new CalendarException(ErrorCode.BAD_REQUEST));
    }

    @Transactional
    public List<Long> findSharedUserIdsByUser(AuthUser authUser) {
        return Stream.concat(
                        shareRepository.findAllByBiDirection(
                                        authUser.getId(),
                                        RequestStatus.ACCEPTED,
                                        Share.Direction.BI_DIRECTION
                                )
                                .stream()
                                .map(s -> s.getToUserId().equals(authUser.getId()) ? s.getFromUserId() : s.getToUserId()),
                        shareRepository.findAllByToUserIdAndRequestStatusAndDirection(authUser.getId(),
                                        RequestStatus.ACCEPTED,
                                        Share.Direction.UNI_DIRECTION
                                )
                                .stream()
                                .map(Share::getFromUserId)
                )
                .collect(Collectors.toList());
    }
}

