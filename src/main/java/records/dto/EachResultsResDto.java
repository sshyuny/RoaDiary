package records.dto;

/**
 * 각 날 또는 기간(eachTime)마다, 수행 정도(extent) 저장
 */
public class EachResultsResDto {
    private String eachTime;
    private int extent;

    public EachResultsResDto(String eachTime, int extent) {
        this.eachTime = eachTime;
        this.extent = extent;
    }

    public void setEachTime(String eachTime) {
        this.eachTime = eachTime;
    }
    public String getEachTime() {
        return eachTime;
    }

    public void setExtent(int extent) {
        this.extent = extent;
    }
    public int getExtent() {
        return extent;
    }
}
