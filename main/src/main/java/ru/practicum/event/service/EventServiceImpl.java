package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventShortDto;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventServiceImpl implements EventService {
    @Override
    public EventShortDto getEventThisUser() {
        return null;
    }

    @Override
    public EventShortDto addEvent() {
        return null;
    }

    @Override
    public EventShortDto getEventThisUserFull() {
        return null;
    }

    @Override
    public EventShortDto updateEventThisUser() {
        return null;
    }

    @Override
    public EventShortDto getEventThisUserRequest() {
        return null;
    }

    @Override
    public EventShortDto updateEventThisUserRequest() {
        return null;
    }
}
