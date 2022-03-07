package records.dto;

import java.time.LocalDate;

// 해당 태그의 수행 시간이 몇 시간인지 저장하는 객체
public class StoreTagTime extends AboutEachRecords{
    
    private LocalDate date;
    private Long minutes;
    private String tagName;
    private int tagId;
    private long categoryId;

    public StoreTagTime(LocalDate date, Long minutes, String tagName, int tagId, long categoryId) {
        this.date = date;
        this.minutes = minutes;
        this.tagName = tagName;
        this.tagId = tagId;
        this.categoryId = categoryId;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setMinutes(Long minutes) {
        this.minutes = minutes;
    }
    public Long getMinutes() {
        return minutes;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    public String getTagName() {
        return tagName;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
    public int getTagId() {
        return tagId;
    }

    public Long getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
