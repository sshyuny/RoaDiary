package records.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class ThingsReqDto {
    
    @DateTimeFormat(pattern = "yyyyMMddHHmm")
    private LocalDateTime time;
    private String content;
    private Long category;

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

    public Long getCategory() {
        return category;
    }
    public void setCategory(Long category) {
        this.category = category;
    }
}
