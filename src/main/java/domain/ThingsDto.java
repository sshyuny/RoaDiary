package domain;

import java.time.LocalDateTime;

public class ThingsDto {

    private Long thingsId;
    private Long userId;
    private LocalDateTime time;
    private String content;
    private Long categoryId;

    public ThingsDto(LocalDateTime time, String content, Long categoryId) {
        this.time = time;
        this.content = content;
        this.categoryId = categoryId;
    }

    //===== 게터와 세터 설정 =====//
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
    
}
