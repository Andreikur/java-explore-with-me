package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.modelMapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @Override
    public List<EventShortDto> getEventThisUser(Long userId, int from, int size) {
        List<Event> eventList = new ArrayList<>();
        int page = from/size;
        PageRequest pageRequest = PageRequest.of(page, size);
        eventList.addAll(eventRepository.findAllEventThisUserPage(userId, pageRequest));
        return modelMapper.map(eventList, EventShortDto.class);
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
