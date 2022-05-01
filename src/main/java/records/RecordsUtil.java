package records;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import domain.JoinThingsTagDto;
import records.dto.JoinThingsTagResDto;
import records.dto.StoreTagTimeResDto;

public class RecordsUtil {
    
    public static List<JoinThingsTagResDto> editJoinResDtos(List<JoinThingsTagResDto> rawResDtos) {

        List<JoinThingsTagResDto> newResDtos = new ArrayList<>();

        if (rawResDtos.size() == 0) return newResDtos;

        newResDtos.add(rawResDtos.get(0));           // 첫 행 넣어둠
        for(int i = 1; i < rawResDtos.size() ; i++) {   // 첫 행 넣어두었기 때문에, i는 1부터 시작함
            JoinThingsTagResDto firstRow = rawResDtos.get(i-1);
            JoinThingsTagResDto secondRow = rawResDtos.get(i);
            if(!firstRow.getThingsId().equals(secondRow.getThingsId())) {  // 수정
                newResDtos.add(secondRow);
            } else {
                int lastIdxOfNewResDtos = newResDtos.size() - 1;
                JoinThingsTagResDto lastRowOfNewList = newResDtos.get(lastIdxOfNewResDtos);
                String tagNameNewList = lastRowOfNewList.getTagName();
                String tagNameList = "#" + secondRow.getTagName();
                String newTagName =tagNameNewList +  tagNameList;
                // lastRowOfNewList.setName(newTagName); //[?] 왜 아래 코드 대신, 이거만 써도 잘 작동되는지 물어보기
                newResDtos.get(lastIdxOfNewResDtos).setTagName(newTagName);
            }
        }

        // 태그 있는 경우, 앞에 "#" 붙이기
        for(JoinThingsTagResDto rowOne : newResDtos) {
            String rowTagName = rowOne.getTagName();
            if(StringUtils.hasText(rowTagName)) {
                rowOne.setTagName("#" + rowTagName);
            }
        }

        return newResDtos;
    }
    /**
     * JoinWithThingsAndTagDto를 이용하여 JoinResDto를 만듭니다. 
     * JoinResDto에는 JoinWithThingsAndTagDto에서 몇 가지 매개변수가 추가됩니다.
     * @param list
     * @return
     */
    public static List<JoinThingsTagResDto> makeJoinResDtos(List<JoinThingsTagDto> list) {

        List<JoinThingsTagResDto> joinResDtos = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            JoinThingsTagDto dto = list.get(i);
            joinResDtos.add(new JoinThingsTagResDto(dto.getThingsId(), dto.getUserId(), dto.getDateTime(), dto.getContent(), dto.getCategoryId(), dto.getTagId(), dto.getTagName()));
            joinResDtos.get(i).setDate(dto.getDateTime().toLocalDate());
            joinResDtos.get(i).setTime(dto.getDateTime().toLocalTime());
            joinResDtos.get(i).setHour(dto.getDateTime().toLocalTime().getHour());
        }

        return joinResDtos;
    }

    public static String fromLocalDatetoString(LocalDate date) {

        // 오늘, 어제 등에 해당할 경우, 날짜를 숫자가 아닌 단어로 반환합니다.
        long betweenDays = ChronoUnit.DAYS.between(LocalDate.now(), date);
        if (betweenDays == 0) {
            return "오늘";
        } else if (betweenDays == -1) {
            return "어제";
        } else if (betweenDays == -2) {
            return "그제";
        } else if (betweenDays == 1) {
            return "내일";
        } else if (betweenDays == 2) {
            return "모레";
        }

        // 위 기준에 해당되지 않을 경우, yyyy-MM-dd 형식 String으로 반환합니다.
        return date.getYear() + "-" + date.getMonthValue() + "-" + date.getDayOfMonth();
    }


    /**
     * 각 주마다, 해당 태그를 몇 번 했는지, 계산하여 반환합니다.
     * @param startDate
     * @param weekNum
     * @param dataList  해당 태그가 입력된 객체들만 들어간 List
     * @return
     */
    public static int[] countEachWeekFrequency(LocalDate startDate, int weekNum, List<JoinThingsTagResDto> dataList){

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
    public static int[] countEachWeekTime(LocalDate fromDate, int weekNum, List<StoreTagTimeResDto> dataList){

        // [배열 results 생성]
        // 각 주마다 주어진 파라미터 tag의 사용 횟수를 넣기 위한 배열
        int[] frequencyResults = new int[weekNum];  // 몇주 동안        
        // int restDay = startDate.getDayOfWeek().getValue();  // 요일을 int로 받기
        // LocalDate fromDate = startDate.minusDays(7 * (weekNum - 1) - 1 + restDay);  // 시작일을 월요일로 맞추기 위해 restDay를 더해줍니다.
        int mok = 0;  // 몇 번째 주에 해당하는지 저장
        int frequency = 0;  // 해당 tag가 몇 번 사용되는지 저장

        // [각 주별로, 해당 tag가 몇 번 사용되는지 계산]
        int listSize = dataList.size();
        for (int i = 0; i < listSize; i++) {

            StoreTagTimeResDto one = dataList.get(i);
            
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
     * 기록 분석 결과에서, 각 결과에 해당되는 날짜를 양식에 맞춰 String[]로 반환합니다.
     * @param fromDate 기록 분석 시작 날짜
     * @param weekNum
     * @return
     */
    public static String[] storeEachWeekDayName(LocalDate fromDate, int weekNum) {
        String[] result = new String[weekNum];
        String dateStr;

        for (int i = 0; i < weekNum ; i++) {
            // 월(month) 양식 맞추기
            if (fromDate.getMonthValue() <= 9) dateStr = String.valueOf(fromDate.getYear()) + "-0" + String.valueOf(fromDate.getMonthValue());
            else dateStr = String.valueOf(fromDate.getYear()) + "-" + String.valueOf(fromDate.getMonthValue());
            // 일(day) 양식 맞추기
            if (fromDate.getDayOfMonth() <= 9) dateStr += "-0" + String.valueOf(fromDate.getDayOfMonth());
            else dateStr += "-" + String.valueOf(fromDate.getDayOfMonth());

            // 다음 결과 시작 날짜로 설정
            fromDate = fromDate.plusDays(7);

            result[i] = dateStr;
        }
        
        return result;
    }
    
    /**
     * 요청받은 날을 String에서 LocalDate로 변환합니다.
     * 요청값이 없을 경우와 이상한 날짜값 들어올 경우, 오늘 날짜를 사용합니다.
     * @param dayStr
     * @return
     */
    public static LocalDate makeLocalDateFromStr(String dayStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTo;

        if (dayStr.equals("")) {  // 따로 기입받지 않은 경우, 오늘이 기준 시점이 됩니다.
                                      //(java.lang.NoSuchMethodError: java.lang.String.isBlank()Z 때문에 isBlank쓰지 않음)
            dateTo = LocalDate.now();
        } else {
            // date String에서 Localdate로 변환
            try {
                dateTo = LocalDate.parse(dayStr, formatter);
            } catch(DateTimeParseException e) {
                // 이상한 날짜값 들어올 경우, 오늘 날짜를 사용
                dateTo = LocalDate.now();
            }
        }
        
        return dateTo;
    }

    /**
     * 파라미터 tagName에 해당하는 tagId를 찾아 반환합니다.
     * @param list  tagName과 tagId가 들어있는 객체 List
     * @param tagName  찾고자하는 태그의 이름
     * @return  tagId 반환
     */
    public static int findTagIdFromTagNameForStore(List<StoreTagTimeResDto> list, String tagName) {
        
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTagName() == null) continue;

            if (list.get(i).getTagName().equals(tagName)) return list.get(i).getTagId();
        }

        return 0;
    }
    public static int findTagIdFromTagNameForJoin(List<JoinThingsTagResDto> list, String tagName) {
        
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTagName() == null) continue;

            if (list.get(i).getTagName().equals(tagName)) return list.get(i).getTagId();
        }

        return 0;
    }

}
