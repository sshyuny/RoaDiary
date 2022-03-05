package domain;

public class SortTagQuantity {

    private int categoryId;
    private String tagName;
    private int quantity;

    public SortTagQuantity(int categoryId, String tagName, int quantity) {
        this.categoryId = categoryId;
        this.tagName = tagName;
        this.quantity = quantity;
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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getQuantity() {
        return quantity;
    }

}
