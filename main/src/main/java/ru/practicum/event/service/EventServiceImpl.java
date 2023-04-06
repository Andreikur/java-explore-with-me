package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.event.dto.EventFulDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional(readOnly = true)
    @Override
    public List<EventShortDto> getEventThisUser(Long userId, int from, int size) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        log.info("Пользователь с таким Id не найден");
        List<Event> eventList = new ArrayList<>();
        int page = from/size;
        PageRequest pageRequest = PageRequest.of(page, size);
        eventList.addAll(eventRepository.findAllEventThisUserPage(userId, pageRequest));
        return EventMapper.toEventShortDto(eventList);
    }

    @Transactional
    @Override
    public EventFulDto addEvent(Long userId, EventFulDto eventFulDto) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        log.info("Пользователь с таким Id не найден");
        Event event = eventRepository.save(EventMapper.toEvent(eventFulDto));
        event.setInitiator(user);
        return EventMapper.toEventFulDto(event);
    }

    @Transactional(readOnly = true)
    @Override
    public EventFulDto getEventFullThisUser(Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        log.info("Пользователь с таким Id не найден");
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Событие с таким Id не найдено")));
        log.info("Событие с таким Id не найдено");
        return EventMapper.toEventFulDto(event);
    }

    @Transactional
    @Override
    public EventFulDto updateEventThisUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        log.info("Пользователь с таким Id не найден");
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Событие с таким Id не найдено")));
        //добавить логику
        //
        //
        //
        //
        return EventMapper.toEventFulDto(event);
    }

    @Transactional
    @Override
    public List<EventFulDto> getEventsByCondition(List<Long> users, List<String> states, List<Long> categories,
                                                  String rangeStart, String rangeEnd, int from, int size) {
        //LocalDateTime rangeStartDate = LocalDateTime.parse(rangeStart, FORMATTER);
        //LocalDateTime rangeEndDate = LocalDateTime.parse(rangeEnd, FORMATTER);

        List<Event> eventList = new ArrayList<>();

/*        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);

        Root<Event> root = query.from(Event.class);
        Predicate criteria = builder.conjunction();

        if (categories != null && categories.size() > 0) {
            Predicate containCategories = root.get("category").in(categories);
            criteria = builder.and(criteria, containCategories);
        }

        if (users != null && users.size() > 0) {
            Predicate containUsers = root.get("initiator").in(users);
            criteria = builder.and(criteria, containUsers);
        }

        if (states != null) {
            Predicate containStates = root.get("state").in(states);
            criteria = builder.and(criteria, containStates);
        }

        if (rangeStart != null) {
            Predicate greaterTime = builder.greaterThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), rangeStart);
            criteria = builder.and(criteria, greaterTime);
        }
        if (rangeEnd != null) {
            Predicate lessTime = builder.lessThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), rangeEnd);
            criteria = builder.and(criteria, lessTime);
        }

        query.select(root).where(criteria);
        List<Event> events = entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

        if (events.size() == 0) {
            return new ArrayList<>();
        }

        setView(events);
        return EventMapper.toEventFulDto(events);*/
        return EventMapper.toEventFulDto(eventList);
    }

    @Transactional
    @Override
    public EventFulDto updateEvent(Long eventId, EventShortDto eventShortDto) {

        //дописать логику
        return null;
    }

    @Transactional
    @Override
    public List<EventFulDto> searchForEventsByParameters(String text, List<Long> categories, Boolean paid,
                                                         LocalDateTime rangeStart, LocalDateTime rangeEnd,
                                                         Boolean onlyAvailable, String sort, int from, int size) {


        //дописать логику
        return null;
    }

/*    public void setView(List<Event> events) {
        LocalDateTime start = events.get(0).getCreatedOn();
        List<String> uris = new ArrayList<>();
        Map<String, Event> eventsUri = new HashMap<>();
        String uri = "";
        for (Event event : events) {
            if (start.isBefore(event.getCreatedOn())) {
                start = event.getCreatedOn();
            }
            uri = "/events/" + event.getId();
            uris.add(uri);
            eventsUri.put(uri, event);
            event.setViews(0L);
        }

        String startTime = start.format(FORMATTER);
        String endTime = LocalDateTime.now().format(FORMATTER);

        List<ViewStatsDto> statsDtoList = getStats(startTime, endTime, uris);
        statsDtoList.forEach((stat) ->
                eventsUri.get(stat.getUri()).setViews(stat.getHits()));
    }*/

/*    public void setView(Event event) {
        String startTime = event.getCreatedOn().format(FORMATTER);
        String endTime = LocalDateTime.now().format(FORMATTER);
        List<String> uris = List.of("/events/" + event.getId());

        List<ViewStatsDto> stats = getStats(startTime, endTime, uris);
        if (stats.size() == 1) {
            event.setViews(stats.get(0).getHits());
        } else {
            event.setViews(0L);
        }
    }*/

/*   private List<ViewStatsDto> getStats(String startTime, String endTime, List<String> uris) {
        return statClient.getStats(startTime, endTime, uris, false);
    }*/
}
