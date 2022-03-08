package records;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import records.dto.AboutEachRecords;
import records.dto.StoreTagTime;

public class RecordsUtil {
    
    /**
     * 각 주마다, 해당 태그를 몇 번 했는지, 계산하여 반환합니다.
     * @param startDate
     * @param weekNum
     * @param dataList  해당 태그가 입력된 객체들만 들어간 List
     * @return
     */
    public static int[] countEachWeekFrequency(LocalDate startDate, int weekNum, List<? extends AboutEachRecords> dataList){

        // [배열 results 생성]
        // 각 주마다 주어진 파라미터 tag의 사용 횟수를 넣기 위한 배열
        int[] frequencyResults = new int[weekNum];  // 몇주 동안

        int restDay = startDate.getDayOfWeek().getValue();  // 요일을 int로 받기
        LocalDate fromDate = startDate.minusDays(7 * (weekNum - 1) - 1 + restDay);  // 시작일을 월요일로 맞추기 위해 restDay를 더해줍니다.
        int mok = 0;  // 몇 번째 주에 해당하는지 저장
        int frequency = 0;  // 해당 tag가 몇 번 사용되는지 저장

        // [각 주별로, 해당 tag가 몇 번 사용되는지 계산]
        int listSize = dataList.size();
        for (int i = 0; i < listSize; i++) {
            
            Long betweenDays = ChronoUnit.DAYS.between(fromDate, dataList.get(i).getDate());
            int tempMok = betweenDays.intValue() / 7;  // 현재 객체가, 몇 번째 주인지 저장

            // 맨 마지막 요소일 경우, 따로 frequencyResults에 frequency를 넣어줍니다(아래 if에서 마지막 요소는 list에 넣어지지 않기 때문).
            if (i == listSize - 1) {
                if (mok == tempMok) {
                    ++frequency;
                    frequencyResults[tempMok] = frequency;
                } else {
                    frequencyResults[tempMok] = 1;
                }
                return frequencyResults;
            }

            // mok과 tempMok이 일치하는지 여부에 따라(같은 주에 해당되는지 여부에 따라), 
            // frequency를 조정하고, frequency를 List frequencyResults에 넣어줍니다.
            if (mok == tempMok) {  // 같은 주에 해당되기 때문에, frequency값을 증가시켜줍니다.
                ++frequency;
            } else {
                if (i == 0) {  // 첫 번째 객체는 mok, tempMok 값과, frequency 값을 초기화하여, 이후 이를 기준으로 계산될 수 있게 해줍니다.
                    frequency = 1;
                    mok = tempMok;
                } else {
                    frequencyResults[mok] = frequency;
                    mok = tempMok;
                    frequency = 1;  // 객체가 존재하기 때문에(해당되는 객체 date에서만 돌리는 for문), frequency는 0이 아니라 1이 되어야 합니다.
                }
            }
        }

        return frequencyResults;
    }


    /**
     * 각 주마다, 해당 태그를 몇 분씩 했는지, 계산하여 반환합니다.
     * @param startDate
     * @param weekNum
     * @param dataList
     * @param tagId
     * @return
     */
    public static int[] countEachWeekTime(LocalDate startDate, int weekNum, List<StoreTagTime> dataList, int tagId){

        // [배열 results 생성]
        // 각 주마다 주어진 파라미터 tag의 사용 횟수를 넣기 위한 배열
        int[] frequencyResults = new int[weekNum];  // 몇주 동안

        int restDay = startDate.getDayOfWeek().getValue();  // 요일을 int로 받기
        LocalDate fromDate = startDate.minusDays(7 * (weekNum - 1) - 1 + restDay);  // 시작일을 월요일로 맞추기 위해 restDay를 더해줍니다.
        int mok = 0;  // 몇 번째 주에 해당하는지 저장
        int frequency = 0;  // 해당 tag가 몇 번 사용되는지 저장

        // [각 주별로, 해당 tag가 몇 번 사용되는지 계산]
        int listSize = dataList.size();
        for (int i = 0; i < listSize; i++) {

            StoreTagTime one = dataList.get(i);

            Long betweenDays = ChronoUnit.DAYS.between(fromDate, one.getDate());
            int tempMok = betweenDays.intValue() / 7;  // 현재 객체가, 몇 번째 주인지 저장
            

            // 맨 마지막 요소일 경우, 따로 frequencyResults에 frequency를 넣어줍니다(아래 if에서 마지막 요소는 list에 넣어지지 않기 때문).
            if (i == listSize - 1) {
                frequency += one.getMinutes().intValue();
                frequencyResults[tempMok] = frequency;
                return frequencyResults;
            }

            // mok과 tempMok이 일치하는지 여부에 따라(같은 주에 해당되는지 여부에 따라), 
            // frequency를 조정하고, frequency를 List frequencyResults에 넣어줍니다.
            if (mok == tempMok) {  // 같은 주에 해당되기 때문에, frequency값을 증가시켜줍니다.
                frequency += one.getMinutes().intValue();
            } else {
                if (i == 0) {  // 첫 번째 객체는 mok, tempMok 값을 초기화하여, 이후 이를 기준으로 계산될 수 있게 해줍니다.
                    mok = tempMok;
                    frequency += one.getMinutes().intValue();
                } else {
                    frequencyResults[mok] = frequency;
                    mok = tempMok;
                    // 객체가 존재하기 때문에(해당되는 객체 date에서만 돌리는 for문), frequency를 새 값으로 조정해줍니다.
                    frequency = one.getMinutes().intValue();
                }
            }
        }

        return frequencyResults;
    }

    /**
     * 파라미터 tagName에 해당하는 tagId를 찾아 반환합니다.
     * @param list  tagName과 tagId가 들어있는 객체 List
     * @param tagName  찾고자하는 태그의 이름
     * @return  tagId 반환
     */
    public static int findTagIdFromTagName(List<? extends AboutEachRecords> list, String tagName) {
        
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTagName() == null) continue;  // (NullPointerException 피하기 위해 넣어줍니다.)

            if (list.get(i).getTagName().equals(tagName)) return list.get(i).getTagId();
        }

        return 0;
    }

}
