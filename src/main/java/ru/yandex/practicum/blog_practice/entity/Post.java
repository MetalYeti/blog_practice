package ru.yandex.practicum.blog_practice.entity;

import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Arrays;
import java.util.List;

@Data
@Table("posts")
public class Post {
    @Id
    private Long id;
    @NonNull
    private String caption;
    @NonNull
    private String content;
    @NonNull
    private String tags;
    private int likes;
    @MappedCollection(idColumn = "post_id", keyColumn = "comment_num")
    private List<Comment> comments;

    public Post(@NonNull String caption, @NonNull String content, @NonNull String tags) {
        this.caption = caption;
        this.content = content;
        this.tags = tags;
    }

    @PersistenceCreator
    public Post(long id, @NonNull String caption, @NonNull String content, @NonNull String tags, int likes, List<Comment> comments) {
        this.id = id;
        this.caption = caption;
        this.content = content;
        this.tags = tags;
        this.likes = likes;
        this.comments = comments;
    }

    public String getTextPreview() {
        return getTextParts().getFirst() + "...";
    }

    public List<String> getTextParts() {
        return Arrays.stream(content.split("\\n")).toList();
    }

    public String getTagsAsText() {
        return tags;
    }

    public List<String> getTags() {
        return Arrays.stream(tags.split(" ")).toList();
    }
}
