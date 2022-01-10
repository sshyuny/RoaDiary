package domain;

public class TagTb {

    private int tagId;
    private String tagContent;

    public TagTb(String tagContent) {
        this.tagContent = tagContent;
    }

    public int getTagId() {
        return tagId;
    }
    public void setTagID(int tagId) {
        this.tagId = tagId;
    }

    public String getTagContent() {
        return tagContent;
    }
    public void setTagContent(String tagContent) {
        this.tagContent = tagContent;
    }
}
