package com.ulanzhasssanov.CrudAppWithJUnit.model;

import com.ulanzhasssanov.CrudAppWithJUnit.enums.PostStatus;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class Post {
    private int id;
    private String content;
    private String created;
    private String updated;
    private Integer labelId;
    private Integer writerId;
    private PostStatus status;

    public Post() {
    }

    public Post(String content, Integer labelId, Integer writerId) {
        this.content = content;
        this.labelId = labelId;
        this.writerId = writerId;
    }

    public Post(int id, String content, String created, String updated, Integer labelId, Integer writerId, PostStatus status) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labelId = labelId;
        this.writerId = writerId;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getWriterId() {
        return writerId;
    }

    public void setWriterId(Integer writerId) {
        this.writerId = writerId;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", created='" + created + '\'' +
                ", updated='" + updated + '\'' +
                ", labelId=" + labelId +
                ", status=" + status +
                '}';
    }
}
