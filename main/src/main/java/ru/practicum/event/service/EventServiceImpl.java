package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoriesRepository;
import ru.practicum.client.EndpointHitClient;
import ru.practicum.client.ViewStatsClient;
import ru.practicum.endpointHit.EndpointHitDto;
import ru.practicum.enums.SortValue;
import ru.practicum.enums.State;
import ru.practicum.enums.StateActionForAdmin;
import ru.practicum.enums.StateActionForUser;
import ru.practicum.event.dto.*;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.EventIsPublishedException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.WrongTimeException;
import ru.practicum.location.model.Location;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final CategoriesRepository categoriesRepository;
    private final LocationRepository locationRepository;
    private final ViewStatsClient viewStatsClient;
    private final EndpointHitClient endpointHitClient;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional(readOnly = true)
    @Override
    public List<EventShortDto> getEventThisUser(Long userId, int from, int size) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        List<Event> eventList = new ArrayList<>();
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);
        eventList.addAll(eventRepository.findAllEventThisUserPage(userId, pageRequest));
        return EventMapper.toEventShortDto(eventList);
    }

    @Transactional//(readOnly = true)
    @Override
    public EventFullDto getEvent(Long eventId) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        event.setViews(event.getViews() + 1);
        saveEndpoint(event);
        //setView(event);
        //sendStat(event, request);
        return EventMapper.toEventFulDto(event);
    }

    @Transactional
    @Override
    public EventFullDto addEvent(Long userId, NewEventDto newEventDto, Location location) {
        Category category = categoriesRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException(""));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        LocalDateTime eventDate = LocalDateTime.parse(newEventDto.getEventDate(), FORMATTER);

        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new WrongTimeException("Дата должна быть в будущем");
        }
        Event event = EventMapper.toEvent(newEventDto);
        event.setConfirmedRequests(0L);
        event.setInitiator(user);
        event.setCategory(category);
        event.setViews(0L);
        event.setLocation(location);
        //event.setPublishedOn(LocalDateTime.now());
        EventFullDto eventFullDto = EventMapper.toEventFulDto(eventRepository.save(event));
        return eventFullDto;
    }

    @Transactional(readOnly = true)
    @Override
    public EventFullDto getEventFullThisUser(Long userId, Long eventId) {
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
    public EventFullDto updateEventThisUser(Long userId, Long eventId, UpdateEventUserRequest updateEventUserRequest) {
        userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Событие с таким Id не найдено")));

        if (event.getPublishedOn() != null) {
            throw new EventIsPublishedException("Событие уже опубликовано");
        }

        if (updateEventUserRequest == null) {
            return EventMapper.toEventFulDto(event);
        }

        if (updateEventUserRequest.getAnnotation() != null) {
            event.setAnnotation(updateEventUserRequest.getAnnotation());
        }
        if (updateEventUserRequest.getCategory() != null) {
            Category category = categoriesRepository.findById(updateEventUserRequest.getCategory()).orElseThrow(() ->
                    new NotFoundException("Категория не найдена"));
            event.setCategory(category);
        }
        if (updateEventUserRequest.getDescription() != null) {
            event.setDescription(updateEventUserRequest.getDescription());
        }
        if (updateEventUserRequest.getEventDate() != null) {
            LocalDateTime eventDateTime = LocalDateTime.parse(updateEventUserRequest.getEventDate(), FORMATTER);
            if (eventDateTime.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new WrongTimeException("Время начала данного мероприятия, составляет менее одного часа с даты публикации.");
            }
            event.setEventDate(eventDateTime);
        }
        if (updateEventUserRequest.getLocation() != null) {
            event.setLocation(updateEventUserRequest.getLocation());
        }
        if (updateEventUserRequest.getPaid() != null) {
            event.setPaid(updateEventUserRequest.getPaid());
        }
        if (updateEventUserRequest.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventUserRequest.getParticipantLimit().intValue());
        }
        if (updateEventUserRequest.getRequestModeration() != null) {
            event.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }
        if (updateEventUserRequest.getTitle() != null) {
            event.setTitle(updateEventUserRequest.getTitle());
        }

        if (updateEventUserRequest.getStateAction() != null) {
            if (updateEventUserRequest.getStateAction().equals(StateActionForUser.SEND_TO_REVIEW)) {
                event.setState(State.PENDING);
            } else {
                event.setState(State.CANCELED);
            }
        }
        return EventMapper.toEventFulDto(event);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventFullDto> getEventsByCondition(List<Long> users, State states, List<Long> categories,
                                                   String rangeStart, String rangeEnd, int from, int size) {
        LocalDateTime start = rangeStart != null ? LocalDateTime.parse(rangeStart, FORMATTER) : null;
        LocalDateTime end = rangeEnd != null ? LocalDateTime.parse(rangeEnd, FORMATTER) : null;

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
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
            Predicate greaterTime = builder.greaterThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), start);
            criteria = builder.and(criteria, greaterTime);
        }
        if (rangeEnd != null) {
            Predicate lessTime = builder.lessThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), end);
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

        //Увеличить колво просмотров

        //setView(events);

        //
        return EventMapper.toEventFulDto(events);
    }

    @Transactional
    @Override
    public EventFullDto updateEvent(Long eventId, UpdateEventAdminDto updateEventAdminDto) {
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Событие с таким Id отсутсвует")));
        if (updateEventAdminDto == null) {
            return EventMapper.toEventFulDto(event);
        }



        if (updateEventAdminDto.getAnnotation() != null) {
            event.setAnnotation(updateEventAdminDto.getAnnotation());
        }
        if (updateEventAdminDto.getCategory() != null) {
            Category category = categoriesRepository.findById(updateEventAdminDto.getCategory()).orElseThrow(() ->
                    new NotFoundException("Категория с таким Id отсутствует"));
            event.setCategory(category);
        }
        if (updateEventAdminDto.getDescription() != null) {
            event.setDescription(updateEventAdminDto.getDescription());
        }
        if (updateEventAdminDto.getLocation() != null) {
            Location location = updateEventAdminDto.getLocation();
            locationRepository.save(location);
            event.setLocation(location);
        }
        if (updateEventAdminDto.getPaid() != null) {
            event.setPaid(updateEventAdminDto.getPaid());
        }
        if (updateEventAdminDto.getParticipantLimit() != null) {
            event.setParticipantLimit(updateEventAdminDto.getParticipantLimit().intValue());
        }
        if (updateEventAdminDto.getRequestModeration() != null) {
            event.setRequestModeration(updateEventAdminDto.getRequestModeration());
        }
        if (updateEventAdminDto.getTitle() != null) {
            event.setTitle(updateEventAdminDto.getTitle());
        }
        if (updateEventAdminDto.getStateAction() != null) {
            if (updateEventAdminDto.getStateAction().equals(StateActionForAdmin.PUBLISH_EVENT)) {
                if (event.getPublishedOn() != null) {
                    throw new EventIsPublishedException("Событие уже опубликовано");
                }
                if (event.getState().equals(State.CANCELED)) {
                    throw new EventIsPublishedException("Событие уже отменено");
                }
                event.setState(State.PUBLISHED);
                event.setPublishedOn(LocalDateTime.now());
            } else if (updateEventAdminDto.getStateAction().equals(StateActionForAdmin.REJECT_EVENT)) {
                if (event.getPublishedOn() != null) {
                    throw new EventIsPublishedException("Событие уже опубликовано");
                }
                event.setState(State.CANCELED);
            }
        }
        if (updateEventAdminDto.getEventDate() != null) {
            LocalDateTime eventDateTime = LocalDateTime.parse(updateEventAdminDto.getEventDate(), FORMATTER);
            if (eventDateTime.isBefore(LocalDateTime.now())
                    || eventDateTime.isBefore(event.getPublishedOn().plusHours(1))) {
                throw new WrongTimeException("Дата начала мероприятия, которое должно быть изменено, составляет менее одного часа с даты публикации.");
            }

            event.setEventDate(eventDateTime);
        }

        return EventMapper.toEventFulDto(eventRepository.save(event));
    }

    @Transactional
    @Override
    public List<EventFullDto> searchForEventsByParameters(String text, List<Long> categories, Boolean paid,
                                                          String rangeStart, String rangeEnd,
                                                          Boolean onlyAvailable, String sort, int from, int size, HttpServletRequest request) {
        LocalDateTime start = rangeStart != null ? LocalDateTime.parse(rangeStart, FORMATTER) : null;
        LocalDateTime end = rangeEnd != null ? LocalDateTime.parse(rangeEnd, FORMATTER) : null;

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> query = builder.createQuery(Event.class);

        Root<Event> root = query.from(Event.class);
        Predicate criteria = builder.conjunction();

        if (text != null) {
            Predicate annotationContain = builder.like(builder.lower(root.get("annotation")),
                    "%" + text.toLowerCase() + "%");
            Predicate descriptionContain = builder.like(builder.lower(root.get("description")),
                    "%" + text.toLowerCase() + "%");
            Predicate containText = builder.or(annotationContain, descriptionContain);

            criteria = builder.and(criteria, containText);
        }

        if (categories != null && categories.size() > 0) {
            Predicate containStates = root.get("category").in(categories);
            criteria = builder.and(criteria, containStates);
        }

        if (paid != null) {
            Predicate isPaid;
            if (paid) {
                isPaid = builder.isTrue(root.get("paid"));
            } else {
                isPaid = builder.isFalse(root.get("paid"));
            }
            criteria = builder.and(criteria, isPaid);
        }

        if (rangeStart != null) {
            Predicate greaterTime = builder.greaterThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), start);
            criteria = builder.and(criteria, greaterTime);
        }
        if (rangeEnd != null) {
            Predicate lessTime = builder.lessThanOrEqualTo(root.get("eventDate").as(LocalDateTime.class), end);
            criteria = builder.and(criteria, lessTime);
        }

        query.select(root).where(criteria).orderBy(builder.asc(root.get("eventDate")));
        List<Event> events = entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();

        if (sort != null) {

            SortValue thisSortValue = SortValue.valueOf(sort);
            if (thisSortValue.equals(SortValue.EVENT_DATE)) {
                events = events.stream().sorted(Comparator.comparing(Event::getEventDate)).collect(Collectors.toList());
            } else {
                events = events.stream().sorted(Comparator.comparing(Event::getViews)).collect(Collectors.toList());
            }
        }

        //Дописать увеличение просмотров

        //setView(events);
        sendStat(events, request);
        return EventMapper.toEventFulDto(events);
    }

    public void sendStat(Event event, HttpServletRequest request) {
        LocalDateTime now = LocalDateTime.now();
        String remoteAddr = request.getRemoteAddr();
        String nameService = "main-service";

        EndpointHitDto requestDto = new EndpointHitDto();
        requestDto.setTimestamp(LocalDateTime.now());
        requestDto.setUri("/events");
        requestDto.setApp(nameService);
        requestDto.setIp(remoteAddr);
        endpointHitClient.addStats(requestDto);
        sendStatForTheEvent(event.getId(), remoteAddr, now, nameService);
    }

    public void sendStat(List<Event> events, HttpServletRequest request) {
        LocalDateTime now = LocalDateTime.now();
        String remoteAddr = request.getRemoteAddr();
        String nameService = "main-service";

        EndpointHitDto requestDto = new EndpointHitDto();
        requestDto.setTimestamp(LocalDateTime.now());
        requestDto.setUri("/events");
        requestDto.setApp(nameService);
        requestDto.setIp(request.getRemoteAddr());
        endpointHitClient.addStats(requestDto);
        sendStatForEveryEvent(events, remoteAddr, LocalDateTime.now(), nameService);
    }

    private void sendStatForTheEvent(Long eventId, String remoteAddr, LocalDateTime now,
                                     String nameService) {
        EndpointHitDto requestDto = new EndpointHitDto();
        requestDto.setTimestamp(LocalDateTime.now());
        requestDto.setUri("/events/" + eventId);
        requestDto.setApp(nameService);
        requestDto.setIp(remoteAddr);
        endpointHitClient.addStats(requestDto);
    }

    private void sendStatForEveryEvent(List<Event> events, String remoteAddr, LocalDateTime now,
                                       String nameService) {
        for (Event event : events) {
            EndpointHitDto requestDto = new EndpointHitDto();
            requestDto.setTimestamp(LocalDateTime.now());
            requestDto.setUri("/events/" + event.getId());
            requestDto.setApp(nameService);
            requestDto.setIp(remoteAddr);
            endpointHitClient.addStats(requestDto);
        }
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

        LocalDateTime startTime = start;
        LocalDateTime endTime = LocalDateTime.now();

        List<ViewStatsDto> stats = getViewStats(startTime.format(FORMATTER), endTime.format(FORMATTER), uris);
        stats.forEach((stat) ->
                eventsUri.get(stat.getUri()).setViews(stat.getHits()));
    }*/

    /*public void setView(Event event) {
        LocalDateTime startTime = event.getCreatedOn();
        LocalDateTime endTime = LocalDateTime.now();
        List<String> uris = List.of("/events/" + event.getId());

        List<ViewStatsDto> statsList = getViewStats(startTime.format(FORMATTER), endTime.format(FORMATTER), uris);
        if (statsList.size() == 1) {
            event.setViews(statsList.get(0).getHits());
        } else {
            event.setViews(0L);
        }
    }*/

/*    private List<ViewStatsDto> getViewStats(String startTime, String endTime, List<String> uris) {
        return viewStatsClient.getViewStats(startTime, endTime, uris, false);
    }*/

    public void saveEndpoint(Event event) {
        LocalDateTime creationTime = LocalDateTime.now();
        EndpointHitDto endpointHitDto = new EndpointHitDto();
        endpointHitDto.setApp("ewm-main-service");
        endpointHitDto.setUri("/events/" + event.getId());
        endpointHitDto.setIp("0:0:0:0:0:0:0:1");
        endpointHitDto.setTimestamp(creationTime);
        endpointHitClient.addStats(endpointHitDto);
    }
}
