package ru.practicum.comments.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.NewCommentDto;
import ru.practicum.comments.mapper.CommentMapper;
import ru.practicum.comments.model.Comment;
import ru.practicum.comments.repository.CommentRepository;
import ru.practicum.event.model.Event;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exception.CommentConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.exception.WrongTimeException;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final EntityManager entityManager;

    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    @Transactional
    public CommentDto createComment(NewCommentDto newCommentDto, Long userId, Long eventId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(String.format("Пользователь с таким Id не найден")));
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format("Событие с таким Id не найдено")));

        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setCreated(LocalDateTime.now());
        comment.setText(newCommentDto.getText());
        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    @Transactional
    public CommentDto updateCommentByUser(NewCommentDto newCommentDto, Long userId, Long commentId) {
        Comment oldComment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Коментарий с таким Id не найден"));
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Неозможно изменить коментарий, такой пользователь не существует");
        }
        if (!oldComment.getAuthor().getId().equals(userId)) {
            throw new CommentConflictException("Коментарий не удален, его создал другой пользователь");
        }
        oldComment.setText(newCommentDto.getText());
        Comment comment = commentRepository.save(oldComment);
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    @Transactional
    public CommentDto updateCommentByAdmin(NewCommentDto newCommentDto, Long commentId) {
        Comment oldComment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Коментарий не найден"));
        oldComment.setText(newCommentDto.getText());
        Comment savedComment = commentRepository.save(oldComment);
        return CommentMapper.toCommentDto(savedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentsByIdByUser(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Коментарий не найден"));
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь не найден");
        }

        if (!userId.equals(comment.getAuthor().getId())) {
            throw new CommentConflictException("Коментарий не получен, его создал другой пользователь");
        }
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getUserCommentsByCreateTime(Long userId, String createStart, String createEnd, Integer from, Integer size) {
        userRepository.findById(userId);
        LocalDateTime start = createStart != null ? LocalDateTime.parse(createStart, FORMATTER) : null;
        LocalDateTime end = createEnd != null ? LocalDateTime.parse(createEnd, FORMATTER) : null;

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Comment> query = builder.createQuery(Comment.class);

        Root<Comment> root = query.from(Comment.class);

        if (createStart != null && createEnd != null) {
            if (end.isBefore(start)) {
                throw new WrongTimeException("createStart дожно должно бы раньше createEnd");
            }
        }

        Predicate criteria = root.get("author").in(userId);
        if (start != null) {
            Predicate greaterTime = builder.greaterThanOrEqualTo(
                    root.get("created").as(LocalDateTime.class), start);
            criteria = builder.and(criteria, greaterTime);
        }
        if (end != null) {
            Predicate lessTime = builder.lessThanOrEqualTo(
                    root.get("created").as(LocalDateTime.class), end);
            criteria = builder.and(criteria, lessTime);
        }
        query.select(root).where(criteria).orderBy(builder.asc(root.get("created")));
        List<Comment> comments = entityManager.createQuery(query)
                .setFirstResult(from)
                .setMaxResults(size)
                .getResultList();
        return CommentMapper.toCommentDto(comments);
    }

    @Override
    @Transactional
    public void deleteCommentByUser(Long userId, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException("Коментарий не найден"));
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Пользователь не найден, коментарий не удален");
        }

        if (!comment.getAuthor().getId().equals(userId)) {
            throw new CommentConflictException("Коментарий не удален, его владелец другой пользователь");
        }
        commentRepository.delete(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentDto> getCommentsByEventIdByAdmin(Long eventId, Integer from, Integer size) {
        eventRepository.findById(eventId).orElseThrow(() -> new NotFoundException(String.format("Событие не найдено")));
        Pageable page = PageRequest.of(from / size, size);
        List<Comment> eventComments = commentRepository.findAllByEvent_Id(eventId, page);
        return CommentMapper.toCommentDto(eventComments);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentDto getCommentsByIdByAdmin(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NotFoundException(String.format("Коментарий не найден")));
        return CommentMapper.toCommentDto(comment);
    }

    @Override
    @Transactional
    public void deleteCommentByAdmin(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}

