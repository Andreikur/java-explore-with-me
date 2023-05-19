package ru.practicum.comments.service;

import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.dto.NewCommentDto;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentService {
    CommentDto createComment(NewCommentDto newCommentDto, Long userId, Long eventId);

    CommentDto updateCommentByUser(NewCommentDto newCommentDto, Long userId, Long commentId);

    CommentDto updateCommentByAdmin(NewCommentDto newCommentDto, Long commentId);

    CommentDto getCommentsByIdByUser(Long userId, Long commentId);

    List<CommentDto> getUserCommentsByCreateTime(Long userId, String createStart, String createEnd, Integer from, Integer size);

    void deleteCommentByUser(Long userId, Long commentId);

    List<CommentDto> getCommentsByEventIdByAdmin(Long eventId, Integer from, Integer size);

    CommentDto getCommentsByIdByAdmin(Long commentId);

    void deleteCommentByAdmin(Long commentId);
}
