package ru.yandex.practicum.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("comments")
public class Comment {
    @Id
    private long id;
    private long postId;
    private String comment;
    private int commentNum;

    @PersistenceCreator
    public Comment(long id, long postId, String comment, int commentNum) {
        this.id = id;
        this.postId = postId;
        this.comment = comment;
    }

    public Comment(String text, Long postId) {
        this.comment = text;
        this.postId = postId;
    }
}
