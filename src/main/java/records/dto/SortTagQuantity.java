package records.dto;

public class SortTagQuantity implements Comparable<SortTagQuantity> {

    private int categoryId;
    private String tagName;
    private int quantity;

    public SortTagQuantity(int categoryId, String tagName, int quantity) {
        this.categoryId = categoryId;
        this.tagName = tagName;
        this.quantity = quantity;
    }

    @Override
    public int compareTo(SortTagQuantity o) {
        return o.quantity - this.quantity;
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
