package records.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class JoinThingsTagResDto {

    // JoinWithThingsAndTagDto에도 있는 변수
    private Long thingsId;
    private Long userId;
    private LocalDateTime dateTime;
    private String content;
    private Long categoryId;
    private int tagId;
    private String tagName;
    // 이 객체에만 있는 변수
    private LocalTime time;
    private LocalDate date;
    private int hour;

    public JoinThingsTagResDto(Long thingsId, Long userId, LocalDateTime dateTime, String content, Long categoryId, int tagId, String tagName) {
        // super(thingsId, userId, dateTime, content, categoryId, tagId, tagName);
        // this.time = dateTime.toLocalTime();
        // this.date = dateTime.toLocalDate();
        // this.hour = time.getHour();
        this.thingsId = thingsId;
        this.userId = userId;
        this.dateTime = dateTime;
        this.content = content;
        this.categoryId = categoryId;
        this.tagId = tagId;
        this.tagName = tagName;
    }

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
