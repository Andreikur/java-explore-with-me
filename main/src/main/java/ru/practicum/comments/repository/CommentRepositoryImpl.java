package ru.practicum.comments.repository;

import org.springframework.context.annotation.Lazy;

public class CommentRepositoryImpl {
    private final CommentRepository commentRepository;

    public CommentRepositoryImpl(@Lazy CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }
}
