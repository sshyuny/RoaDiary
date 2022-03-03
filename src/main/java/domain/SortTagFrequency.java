package domain;

public class SortTagFrequency {

    private int categoryId;
    private String tagName;
    private int frequency;

    public SortTagFrequency(int categoryId, String tagName, int frequency) {
        this.categoryId = categoryId;
        this.tagName = tagName;
        this.frequency = frequency;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }
    public int getCategoryId() {
        return categoryId;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }
    public String getTagName() {
        return tagName;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
    public int getFrequency() {
        return frequency;
    }

}
