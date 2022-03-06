package domain;

import java.time.LocalDate;

import records.dto.AboutEachRecords;

public class SortTagTime extends AboutEachRecords{
    
    private LocalDate date;
    private Long minutes;
    private String tagName;
    private int tagId;
    private long categoryId;

    public SortTagTime(LocalDate date, Long minutes, String tagName, int tagId, long categoryId) {
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
