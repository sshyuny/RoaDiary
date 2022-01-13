package domain;

import java.time.LocalDateTime;

public class JoinWighThingsAndTag {
    
    private Long thingsId1;
    private Long userId;
    private LocalDateTime time;
    private String content;
    private Long categoryId;
    private Long thingsId2;
    private int tagId1;
    private int tagId2;
    private String name;

    public Long getThingsId1() {
        return thingsId1;
    }
    public void setThings1(Long thingsId1) {
        this.thingsId1 = thingsId1;
    }

}
