package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoriesRepository;
import ru.practicum.enums.SortValue;
import ru.practicum.enums.State;
import ru.practicum.enums.StateActionForUser;
import ru.practicum.event.dto.EventFulDto;
import ru.practicum.event.dto.EventShortDto;
import ru.practicum.event.dto.NewEventDto;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.EventIsPublishedException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.WrongTimeException;
import ru.practicum.location.mapper.LocationMapper;
import ru.practicum.location.model.Location;
import ru.practicum.location.repository.LocationRepository;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
//@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EntityManager entityManager;
    private final UserRepository userRepository;
    private final CategoriesRepository categoriesRepository;
    private final LocationRepository locationRepository;
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
    public EventFulDto addEvent(Long userId, NewEventDto newEventDto) {
        Category category = categoriesRepository.findById(newEventDto.getCategory())
                .orElseThrow(() -> new NotFoundException(""));
        User user = userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));
        //Event event = EventMapper.toEvent(newEventDto);
        Location location = locationRepository.save(LocationMapper.toLocation(newEventDto.getLocation()));
        LocalDateTime eventDate = LocalDateTime.parse(newEventDto.getEventDate(), FORMATTER);

        if (eventDate.isBefore(LocalDateTime.now().plusHours(2))) {
            throw new WrongTimeException("Дата должна быть в будущем");
        }
        Event event = EventMapper.toEvent(newEventDto);
        event.setLocation(location);        //????
        event.setConfirmedRequests(0L);
        event.setInitiator(user);
        event.setCategory(category);
        EventFulDto eventFulDto = EventMapper.toEventFulDto(eventRepository.save(event));
        return eventFulDto;
        //return EventMapper.toEventFulDto(event);
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
        /*userRepository.findById(userId).orElseThrow(() ->
                new NotFoundException(String.format("Пользователь с таким Id не найден")));*/
        //log.info("Пользователь с таким Id не найден");
        Event event = eventRepository.findById(eventId).orElseThrow(() ->
                new NotFoundException(String.format("Событие с таким Id не найдено")));
        //добавить логику
        //
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
            LocalDateTime eventDateTime = updateEventUserRequest.getEventDate();
            if (eventDateTime.isBefore(LocalDateTime.now().plusHours(2))) {
                throw new WrongTimeException("Время начала данного мероприятия, составляет менее одного часа с даты публикации.");
            }
            event.setEventDate(updateEventUserRequest.getEventDate());
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

        if (updateEventUserRequest.getState() != null) {
            if (updateEventUserRequest.getState().equals(StateActionForUser.SEND_TO_REVIEW)) {
                event.setState(State.PENDING);
            } else {
                event.setState(State.CANCELED);
            }
        }

        return EventMapper.toEventFulDto(event);

        //return EventMapper.toEventFulDto(event);
    }

    @Transactional(readOnly = true)
    @Override
    public List<EventFulDto> getEventsByCondition(List<Long> users, List<String> states, List<Long> categories,
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
        //return EventMapper.toEventFulDto(eventList);
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
                                                         String rangeStart, String rangeEnd,
                                                         Boolean onlyAvailable, String sort, int from, int size) {
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

        /*if (onlyAvailable) {
            events = events.stream()
                    .filter((event -> event.getConfirmedRequests() < (long) event.getParticipantLimit()))
                    .collect(Collectors.toList());
        }*/

        if (sort != null) {
            if (sort.equals(SortValue.EVENT_DATE)) {
                events = events.stream().sorted(Comparator.comparing(Event::getEventDate)).collect(Collectors.toList());
            } else {
                events = events.stream().sorted(Comparator.comparing(Event::getViews)).collect(Collectors.toList());
            }
        }

        /*if (events.size() == 0) {
            return new ArrayList<>();
        }*/

        //Дописать увеличение просмотров

        //setView(events);
        //sendStat(events, request);
        return EventMapper.toEventFulDto(events);
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
