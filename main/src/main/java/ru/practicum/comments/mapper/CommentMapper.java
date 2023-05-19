package ru.practicum.comments.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.comments.dto.CommentDto;
import ru.practicum.comments.model.Comment;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommentMapper {

    public static CommentDto toCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .created(comment.getCreated())
                .authorName(comment.getAuthor().getName())
                .eventId(comment.getEvent().getId())
                .build();
    }

    public static List<CommentDto> toCommentDto(Iterable<Comment> comments) {
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment : comments) {
            commentDtoList.add(toCommentDto(comment));
        }
        return commentDtoList;
    }
}
