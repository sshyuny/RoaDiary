package records;

import java.time.LocalDateTime;

public class ThingsTb {

    private Long things_id;
    private Long user_id;
    private LocalDateTime time;
    private String content;
    private Long category_id;

    public ThingsTb(LocalDateTime time, String content, Long category_id) {
        //this.things_id = things_id;
        //this.user_id = user_id;
        this.time = time;
        this.content = content;
        this.category_id = category_id;
    }

    //===== 게터와 세터 설정 =====//
    public Long getThngs_id() {
        return things_id;
    }
    public void setThings_id(Long things_id) {
        this.things_id = things_id;
    }

    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public Long getCategory_id() {
        return category_id;
    }
    public void setCategory_id(Long category_id) {
        this.category_id = category_id;
    }
    
}
