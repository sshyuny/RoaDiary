package domain;

import java.time.LocalDateTime;

public class JoinWithThingsAndTagTb {
    
    private Long thingsId1;
    private Long userId;
    private LocalDateTime time;
    private String content;
    private Long categoryId;
    private Long thingsId2;
    private int tagId1;
    private int tagId2;
    private String name;

    public JoinWithThingsAndTagTb(Long thingsId, Long userId, LocalDateTime time, String content, Long categoryId, int tagId, String name) {
        this.thingsId1 = thingsId;
        this.userId = userId;
        this.time = time;
        this.content = content;
        this.categoryId = categoryId;
        this.thingsId2 = thingsId;
        this.tagId1 = tagId;
        this.tagId2 = tagId;
        this.name = name;
    }

    public Long getThingsId1() {
        return thingsId1;
    }
    public void setThings1(Long thingsId1) {
        this.thingsId1 = thingsId1;
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

    public Long getThingsId2() {
        return thingsId2;
    }
    public void setThingsId2(Long thingsId2) {
        this.thingsId2 = thingsId2;
    }

    public int getTagId1() {
        return tagId1;
    }
    public void setTagId1(int tagId1) {
        this.tagId1 = tagId1;
    }

    public int getTagId2() {
        return tagId2;
    }
    public void setTagId2(int tagId2) {
        this.tagId2 = tagId2;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
