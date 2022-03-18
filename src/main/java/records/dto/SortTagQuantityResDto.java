package records.dto;

/**
 * 태그별로, 주어진 기간동안 몇 번 사용됐는지 저장하는 객체입니다.
 * comparable을 통해, 사용 횟수에 따라 내림차순으로 정렬됩니다.
 */
public class SortTagQuantityResDto implements Comparable<SortTagQuantityResDto> {

    private int categoryId;
    private String tagName;
    private int quantity;

    public SortTagQuantityResDto(int categoryId, String tagName, int quantity) {
        this.categoryId = categoryId;
        this.tagName = tagName;
        this.quantity = quantity;
    }

    @Override
    public int compareTo(SortTagQuantityResDto o) {
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
