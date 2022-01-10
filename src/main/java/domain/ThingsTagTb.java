package domain;

public class ThingsTagTb {
    private Long thingsId;
    private int tagId;

    public ThingsTagTb(Long thingsId, int tagId) {
        this.thingsId = thingsId;
        this.tagId = tagId;
    }

    public Long getThingsId() {
        return thingsId;
    }
    public void setThingsId(Long thingsId) {
        this.thingsId = thingsId;
    }

    public int getTagId() {
        return tagId;
    }
    public void setTagId(int tagId) {
        this.tagId = tagId;
    }
}
