package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class JoinWithThingsAndTagTb {
    
    private Long thingsId;
    private Long userId;
    private LocalTime time;  // 원래는 LocalDateTime의 time이지만, 편의를 위해 time과 아래 date로 나눔
    private LocalDate date;  // (sql테이블에 없는 파라미터)
    private String content;
    private Long categoryId;
    private int tagId;
    private String name;
    private int hour; // 프론트에서 쉽게 사용하기 위한 변수. sql 테이블에 없는 파라미터임.

    public JoinWithThingsAndTagTb(Long thingsId, Long userId, LocalDateTime time, String content, Long categoryId, int tagId, String name) {
        this.thingsId = thingsId;
        this.userId = userId;
        this.time = time.toLocalTime();
        this.date = time.toLocalDate();  // (sql테이블에 없는 파라미터)
        this.content = content;
        this.categoryId = categoryId;
        this.tagId = tagId;
        this.name = name;
        this.hour = time.getHour(); // 프론트에서 쉽게 사용하기 위한 변수. sql 테이블에 없는 파라미터임.
    }

    // 프론트에서 쉽게 사용하기 위한 변수. sql 테이블에 없는 파라미터임.
    public int getHour() {
        return hour; 
    }
    public void setHour(int hour){
        this.hour = hour;
    }
    public LocalDate getDate() {
        return date; 
    }
    public void setDate(LocalDate date){
        this.date = date;
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

    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
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
