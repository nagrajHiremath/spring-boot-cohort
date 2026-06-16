package com.spring.cohort.assignment.service;

import com.spring.cohort.assignment.entity.Session;
import com.spring.cohort.assignment.entity.User;
import com.spring.cohort.assignment.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    public void createSession(String refreshToken, User user) {

        List<Session> sessionList = sessionRepository.findByUser(user);

        if(sessionList.size() == user.getSessionLimit()){
            sessionList.sort(Comparator.comparing(Session::getLastUsedAt));
            Session leastRecentlyUsedSession = sessionList.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        Session newSession = Session.builder()
                .refreshToken(refreshToken)
                .user(user)
                .username(user.getUsername())
                .build();

        sessionRepository.save(newSession);

    }

    public void validateSession(String refreshToken) {
        Session validSession = sessionRepository.findByRefreshToken(refreshToken).orElseThrow();
        validSession.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(validSession);
    }

    public void deleteSession(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken).orElseThrow();
        sessionRepository.delete(session);
    }
    public void rotateRefreshToken(String oldRefreshToken, String newRefreshToken){
        Session session = sessionRepository.findByRefreshToken(oldRefreshToken).orElseThrow();
        session.setRefreshToken(newRefreshToken);
        sessionRepository.save(session);
    }

}
