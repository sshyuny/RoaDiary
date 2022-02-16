package records.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class ThingsReqDto {
    
    private Long thingsId;
    @DateTimeFormat(pattern = "yyyyMMddHHmm")
    private LocalDateTime time;
    private String content;
    private Long category;
    private String tag1;
    private String tag2;
    private String tag3;
    private String tag4;

    public Long getThingsId(){
        return thingsId;
    }
    public void setThingsId(Long thingsId){
        this.thingsId = thingsId;
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

    public Long getCategory() {
        return category;
    }
    public void setCategory(Long category) {
        this.category = category;
    }

    public String getTag1() {
        return tag1;
    }
    public void setTag1(String tag) {
        this.tag1 = tag;
    }
    public String getTag2() {
        return tag2;
    }
    public void setTag2(String tag) {
        this.tag2 = tag;
    }
    public String getTag3() {
        return tag3;
    }
    public void setTag3(String tag) {
        this.tag3 = tag;
    }
    public String getTag4() {
        return tag4;
    }
    public void setTag4(String tag) {
        this.tag4 = tag;
    }
}
