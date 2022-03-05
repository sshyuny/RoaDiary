package domain;

import java.time.LocalDate;

public class SortTagTime {
    
    private LocalDate date;
    private long minutes;
    private String tagName;

    public SortTagTime(LocalDate date, long minutes, String tagName) {
        this.date = date;
        this.minutes = minutes;
        this.tagName = tagName;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }
    public long getMinutes() {
        return minutes;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    public String getTagName() {
        return tagName;
    }
}
