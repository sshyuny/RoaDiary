package domain;

import java.time.LocalDateTime;

public class JoinWithThingsAndTagTb {
    
    private Long thingsId;
    private Long userId;
    private LocalDateTime time;
    private String content;
    private Long categoryId;
    private int tagId;
    private String name;

    public JoinWithThingsAndTagTb(Long thingsId, Long userId, LocalDateTime time, String content, Long categoryId, int tagId, String name) {
        this.thingsId = thingsId;
        this.userId = userId;
        this.time = time;
        this.content = content;
        this.categoryId = categoryId;
        this.tagId = tagId;
        this.name = name;
    }

    public Long getThingsId() {
        return thingsId;
    }
    public void setThingsId(Long thingsId) {
        this.thingsId = thingsId;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public LocalDateTime getTime() {
        return time;
    }
    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public int getTagId() {
        return tagId;
    }
    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
