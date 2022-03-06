package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import records.dto.AboutEachRecords;

public class JoinWithThingsAndTagTb extends AboutEachRecords{
    
    private Long thingsId;
    private Long userId;
    private LocalTime time;  // (sql테이블에 없는 파라미터) 
    private LocalDate date;  // (sql테이블에 없는 파라미터)
    private LocalDateTime dateTime;  // sql의 time과 동일(편의를 위해 time과 아래 date로 나눔)
    private String content;
    private Long categoryId;
    private int tagId;
    private String tagName;
    private int hour; // (sql테이블에 없는 파라미터) 프론트에서 쉽게 사용하기 위한 변수

    public JoinWithThingsAndTagTb(Long thingsId, Long userId, LocalDateTime dateTime, String content, Long categoryId, int tagId, String tagName) {
        this.thingsId = thingsId;
        this.userId = userId;
        this.time = dateTime.toLocalTime();  // (sql테이블에 없는 파라미터)
        this.date = dateTime.toLocalDate();  // (sql테이블에 없는 파라미터)
        this.dateTime = dateTime;
        this.content = content;
        this.categoryId = categoryId;
        this.tagId = tagId;
        this.tagName = tagName;
        this.hour = time.getHour(); // (sql테이블에 없는 파라미터) 프론트에서 쉽게 사용하기 위한 변수
    }

    // 프론트에서 쉽게 사용하기 위한 변수. sql 테이블에 없는 파라미터임.
    public int getHour() {
        return hour; 
    }
    public void setHour(int hour){
        this.hour = hour;
    }
    // 프론트에서 쉽게 사용하기 위한 변수. sql 테이블에 없는 파라미터임.
    @Override
    public LocalDate getDate() {
        return date; 
    }
    @Override
    public void setDate(LocalDate date){
        this.date = date;
    }
    // 프론트에서 쉽게 사용하기 위한 변수. sql 테이블에 없는 파라미터임.
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public String getTagName() {
        return tagName;
    }
    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

}
