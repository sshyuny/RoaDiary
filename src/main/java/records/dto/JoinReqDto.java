package records.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import domain.JoinWithThingsAndTagTb;

/**
 * DB에서 join의 결과로 얻어지는 JoinWithThingsAndTagTb 객체의 시간 내용 매개변수는 LocalDateTime dateTime 하나만 있습니다.
 * 그러나 프론트 등에서 사용하기 쉽기위해, 
 * 1. LocalDateTime을 LocalDate와 LocalTime 두 내용으로 구분하여 변수 추가
 * 2. hour 변수 추가
 * 위 두 가지 변화가 필요하여 만든 객체입니다.
 * 
 * 이 객체의 변수는 sql테이블에 없는 파라미터들로만 이루어져 있습니다.
 */
public class JoinReqDto extends JoinWithThingsAndTagTb {

    private LocalTime time;
    private LocalDate date;
    private int hour;


    public JoinReqDto(Long thingsId, Long userId, LocalDateTime dateTime, String content, Long categoryId, int tagId, String tagName) {
        super(thingsId, userId, dateTime, content, categoryId, tagId, tagName);
        this.time = dateTime.toLocalTime();
        this.date = dateTime.toLocalDate();
        this.hour = time.getHour();
    }

    public int getHour() {
        return hour; 
    }
    public void setHour(int hour){
        this.hour = hour;
    }

    public LocalDate getDate() {
        return date; 
    }
    public void setDate(LocalDate date){
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
}
