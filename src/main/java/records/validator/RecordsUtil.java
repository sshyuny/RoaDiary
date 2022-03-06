package records.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import domain.JoinWithThingsAndTagTb;
import domain.SortTagQuantity;
import domain.SortTagTime;
import records.dto.AboutEachRecords;

public class RecordsUtil {
    
    public static int[] countEachWeek(LocalDate startDate, int weekNum, List<? extends AboutEachRecords> dataList){

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

    public static int[] countEachWeekTime(LocalDate startDate, int weekNum, List<SortTagTime> dataList, int tagId){

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
                // if (mok == tempMok) {
                    
                    if ((dataList.get(i).getCategoryId() == 1) && (dataList.get(i).getTagId() == tagId)) {  // CategoryId 1일때와 태그 일치할 때만 저장
                        frequency += dataList.get(i).getMinutes().intValue();
                        frequencyResults[tempMok] = frequency;
                        // return frequencyResults;
                    }
                    return frequencyResults;
                // } else {
                //     if ((dataList.get(i).getCategoryId() == 1) && (dataList.get(i).getTagId() == tagId)) {  // CategoryId 1일때와 태그 일치할 때만 저장
                //         frequencyResults[tempMok] = dataList.get(i).getMinutes().intValue();
                //     }
                // }
            }

            // mok과 tempMok이 일치하는지 여부에 따라(같은 주에 해당되는지 여부에 따라), 
            // frequency를 조정하고, frequency를 List frequencyResults에 넣어줍니다.
            if (mok == tempMok) {  // 같은 주에 해당되기 때문에, frequency값을 증가시켜줍니다.
                if ((dataList.get(i).getCategoryId() == 1) && (dataList.get(i).getTagId() == tagId)) {  // CategoryId 1일때와 태그 일치할 때만 저장
                    frequency += dataList.get(i).getMinutes().intValue();
                    frequencyResults[tempMok] = frequency;
                }
            } else {
                if (i == 0) {  // 첫 번째 객체는 mok, tempMok 값을 초기화하여, 이후 이를 기준으로 계산될 수 있게 해줍니다.
                    mok = tempMok;
                    if ((dataList.get(i).getCategoryId() == 1) && (dataList.get(i).getTagId() == tagId)) {  // CategoryId 1일때와 태그 일치할 때만 저장
                        frequencyResults[tempMok] = dataList.get(i).getMinutes().intValue();
                    }
                } else {
                    frequencyResults[mok] = frequency;
                    mok = tempMok;
                    if ((dataList.get(i).getCategoryId() == 1) && (dataList.get(i).getTagId() == tagId)) {  // CategoryId 1일때와 태그 일치할 때만 저장
                        frequency = dataList.get(i).getMinutes().intValue();  // 객체가 존재하기 때문에(해당되는 객체 date에서만 돌리는 for문), frequency를 조정해줍니다.
                        frequencyResults[tempMok] = frequency;
                    } else {
                        frequency = 0;
                    }
                }
            }
        }

        return frequencyResults;
    }

    public static int findTagIdFromTagName(List<? extends AboutEachRecords> list, String tagName) {
        
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTagName() == null) continue;  // (NullPointerException 피하기 위해 넣어줍니다.)

            if (list.get(i).getTagName().equals(tagName)) return list.get(i).getTagId();
        }

        return 0;
    }

    /**
     * 
     * @param map
     * @param categoryId
     * @param toDevide  // 각 태그별로 수행한 시간이 파라미터로 들어올 경우, 60으로 나눠서 보여주어야 시간 단위로 보여줄 수 있습니다.
     *                  // 단순 수행 횟수를 알아보려는 경우에는, 1을 넣어줍니다.
     * @return
     */
    public static List<SortTagQuantity> makeSortTagQuantities(Map<String, Integer> map, int categoryId, int toDevide) {

        // [배열 array 생성]
        // map의 value(태그 반복 횟수)를 넣어 두기 위해
        int mapSize = map.size();
        List<Integer> values = new ArrayList<>(map.values());
        Integer[] array = new Integer[mapSize];
        for (int i = 0; i < mapSize; i++) {
            array[i] = values.get(i);
        }
        Arrays.sort(array, Collections.reverseOrder());  // 자주 반복되는 태그를 앞에 두기 위해 reverseOrder()을 사용해줍니다.
        
        // [List list 생성]
        // 카테고리(categoryId)와 태그(tagName)와 그 사용 횟수(frequency)를 SortTagQuantity에 넣어 객체 생성
        // (수정)SortTagQuantity에서 categoryId 필요 없는거 확인하고 이후 지우기
        List<SortTagQuantity> list = new ArrayList<>();
        Set<String> set = map.keySet();
        for (int i = 0; i < 5; i++) {  // 가장 자주 순으로 위에서 5개만 선택합니다.
            if (mapSize <= i) break;  // 만일 사용된 태그가 5개 이하일 경우, for문을 중간에 종료시킵니다(안 할 경우, IndexOutOfBounceException 발생).
            for (String one : set) {
                if (map.get(one) == array[i]) list.add(new SortTagQuantity(categoryId, one, array[i] / toDevide));
            }
        }

        return list;
    }
}
