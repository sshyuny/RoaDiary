package records;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import domain.JoinWithThingsAndTagTb;
import domain.SortTagQuantity;
import domain.SortTagTime;
import domain.TagTb;
import domain.ThingsTagTb;
import domain.ThingsTb;
import records.dto.ThingsReqDto;

public class RecordsService {
    
    private ThingsDao thingsDao;
    private TagDao tagDao;
    private ThingsTagDao thingsTagDao;
    private JoinDao joinDao;

    public RecordsService(ThingsDao thingsDao, TagDao tagDao, ThingsTagDao thingsTagDao, JoinDao joinDao) {
        this.thingsDao = thingsDao;
        this.tagDao = tagDao;
        this.thingsTagDao = thingsTagDao;
        this.joinDao = joinDao;
    }

    /**
     * Things 테이블에 내용 입력
     * @param thingsReqDto
     * @param loginId
     */
    public Long insertThings(ThingsReqDto thingsReqDto, Long loginId) {
        // 시간 부분 공백으로 두고 전송했을 때, 지금 시간으로 저장되게 함
        if (thingsReqDto.getTime() == null) {
            thingsReqDto.setTime(LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute()));
        }
        // 날짜 부분 공백으로 두고 전송했을 때, 오늘 날짜로 저장되게 함
        if (thingsReqDto.getDate() == null) {
            thingsReqDto.setDate(LocalDate.now());
        }
        // ThingsTb 객체 생성
        ThingsTb thingsTb = new ThingsTb(thingsReqDto.makeDateTime(thingsReqDto.getTime(), thingsReqDto.getDate()), thingsReqDto.getContent(), thingsReqDto.getCategory());
        thingsTb.setUserId(loginId);
        
        // [DB]
        // recordsDao를 통해 DB에 insert
        Long key = thingsDao.insert(thingsTb);
        return key;
    }

    public void updateThingsTime(ThingsReqDto thingsReqDto, Long thingsId) {
        // [DB]
        // recordsDao를 통해 DB update
        thingsDao.updateTime(thingsReqDto.getTime(), thingsId); // 날짜 변경은 불가능하게 만듦(thingsReqDto.makeDateTime() 삭제)
    }
    public void updateThingsContent(ThingsReqDto thingsReqDto, Long thingsId) {
        // [DB]
        // recordsDao를 통해 DB update
        thingsDao.updateContent(thingsReqDto.getContent(), thingsId);
    }

    public List<JoinWithThingsAndTagTb> selectThingsToday(Long loginId) {
        // 현재 시간 가져오기
        LocalDate date = LocalDate.now();

        // [DB]
        // recordsDao를 통해 DB에서 select
        return joinDao.selectByDate(date, loginId);
    }
    public List<JoinWithThingsAndTagTb> selectThingsSomeday(String stringDate, Long loginId) {
        // date String에서 Localdate로 변환
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(stringDate, formatter);

        // [DB]
        // recordsDao를 통해 DB에서 select
        List<JoinWithThingsAndTagTb> joinTbList =  joinDao.selectByDate(date, loginId);
        List<JoinWithThingsAndTagTb> newJoinTbList = new ArrayList<>();
        if(joinTbList.size() > 0) {
            newJoinTbList.add(joinTbList.get(0));           // 첫 행 넣어둠
            for(int i = 1; i < joinTbList.size() ; i++) {   // 첫 행 넣어두었기 때문에, i는 1부터 시작함
                JoinWithThingsAndTagTb firstRow = joinTbList.get(i-1);
                JoinWithThingsAndTagTb secondRow = joinTbList.get(i);
                if(!firstRow.getThingsId().equals(secondRow.getThingsId())) {  // 수정
                    newJoinTbList.add(secondRow);
                } else {
                    int lastIdxOfnewJoinList = newJoinTbList.size() - 1;
                    JoinWithThingsAndTagTb lastRowOfNewList = newJoinTbList.get(lastIdxOfnewJoinList);
                    String tagNameNewList = lastRowOfNewList.getName();
                    String tagNameList = "#" + secondRow.getName();
                    String newTagName =tagNameNewList +  tagNameList;
                    // lastRowOfNewList.setName(newTagName); //[?] 왜 아래 코드 대신, 이거만 써도 잘 작동되는지 물어보기
                    newJoinTbList.get(lastIdxOfnewJoinList).setName(newTagName);
                }
            }
        }
        // 태그 있는 경우, 앞에 "#" 붙이기
        for(JoinWithThingsAndTagTb rowTb : newJoinTbList) {
            String rowTbTagName = rowTb.getName();
            if(StringUtils.hasText(rowTbTagName)) {
                rowTb.setName("#" + rowTbTagName);
            }
        }
        return newJoinTbList;
    }
    

    public void insertTags(ThingsReqDto thingsReqDto, Long thingsId) {
        // 여러 tag들 담는 String 배열 tagContent 생성
        String[] tagContent = {
            thingsReqDto.getTag1(),
            thingsReqDto.getTag2(),
            thingsReqDto.getTag3(),
            thingsReqDto.getTag4()
        };

        // 여러 tag들 각각 수행
        for(String oneTagContent : tagContent) {
            // tag에 무언가 적혀있다면,
            if(StringUtils.hasText(oneTagContent) ) {

                // 이미 저장된 tag인지 확인
                TagTb beTagTb = tagDao.selectByName(oneTagContent);

                // 이미 저장된 tag일 경우
                if(beTagTb != null) {
                    // (tag 내용 저장하는 과정 생략)

                    // ThingsTagTb 객체 생성(이미 저장된 tag의 Id값 사용)
                    ThingsTagTb thingsTagTb = new ThingsTagTb(thingsId, beTagTb.getTagId());

                    // [DB]
                    // Dao를 통해 DB에 insert
                    thingsTagDao.insert(thingsTagTb);

                } else { // 새로운 tag일 경우
                    // TagTb 객체 생성
                    TagTb tagTb = new TagTb(oneTagContent);

                    // [DB]
                    // Dao를 통해 DB에 insert
                    tagDao.insert(tagTb);
                    // tagTb.getTagId()를 하기 위해선 이 과정 먼저 거쳐야 됨(dao에서 setTagId함)

                    // ThingsTagTb 객체 생성
                    ThingsTagTb thingsTagTb = new ThingsTagTb(thingsId, tagTb.getTagId());

                    // [DB]
                    // Dao를 통해 DB에 insert
                    thingsTagDao.insert(thingsTagTb);
                }
            }
        }
        
    }

    public void deleteThingsTag(ThingsReqDto thingsReqDto, Long thingsId) {
        thingsTagDao.delete(thingsId);
    }

    // ========== sorting ==========

    public List<JoinWithThingsAndTagTb> selectThingsPeriod(String stringDate, Long loginId) {

        // [가쥰 시점 날짜 받기]
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // dateTo 생성 과정 - 1
        LocalDate dateTo;
        if (stringDate.isBlank()) {  // 따로 기입받지 않은 경우, 오늘이 기준 시점이 됩니다.
            dateTo = LocalDate.now();
        } else {
            // date String에서 Localdate로 변환
            dateTo = LocalDate.parse(stringDate, formatter);
        }
        // dateFrom 생성 과정
        LocalDate dateFrom = LocalDate.of(dateTo.getYear(), dateTo.getMonthValue(), dateTo.getDayOfMonth());
        int restDay = LocalDate.now().getDayOfWeek().getValue();  // 요일을 int로 받기
        dateFrom = dateFrom.minusDays(7 * 11 - 1 + restDay);  // 월요일부터 시작하도록 하기 위한 계산입니다(총 12주 이내).
        // dateTo 생성 과정 - 2
        dateTo = dateTo.plusDays(1); // sql에서 Between을 썼을 때, dateTo 당일이 포함되지 않기 때문에 하루를 더해줍니다.

        // [DB]
        // dateoFrom부터 dateTo까지 사이의 기록들 List로 가져오기
        List<JoinWithThingsAndTagTb> joinTbList = joinDao.selectByDatePeriod(dateFrom, dateTo, loginId);

        return joinTbList;
    }

    public List<SortTagQuantity> calculJoinTbsByFrequency(List<JoinWithThingsAndTagTb> joinTagTbs, int categoryId) {

        // [Map map 생성]
        // Key: 각 태그 ; value: 해당 태그의 사용 횟수
        Map<String, Integer> map = new HashMap<>();

        // [map에 각 값들 넣어주기]
        for (JoinWithThingsAndTagTb one : joinTagTbs) {
            String tagName = one.getName();
            if ((one.getCategoryId() == categoryId) && (tagName != null)) {  // 태그가 null인 경우는 제외시킵니다(안 할 경우 NullPointerException 발생).
                if (map.get(tagName) == null) {
                    map.put(tagName, 1);
                } else {
                    map.replace(tagName, map.get(tagName) + 1);
                }
                
            }
        }

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
                if (map.get(one) == array[i]) list.add(new SortTagQuantity(categoryId, one, array[i]));
            }
        }

        return list;
    }

    public int[] calculFrequency(List<JoinWithThingsAndTagTb> joinTagTbs, int categoryId, String tag) {

        // [List newJoinTagTbs 생성]
        // 주어진 파라미터 categoryId와 tag와 일치하는 객체(JoinWithThingsAndTagTb)만 선택하여 새 list에 넣기
        List<JoinWithThingsAndTagTb> newJoinTagTbs = new ArrayList<>();
        for (JoinWithThingsAndTagTb one : joinTagTbs) {
            if (one.getName() != null) {  // tag가 없을 경우 제외시킵니다(안 할 경우 NullPointerException 발생).
                if ((one.getCategoryId() == categoryId) && (one.getName().equals(tag))) newJoinTagTbs.add(one);
            }
        }

        // [배열 results 생성]
        // 각 주마다 주어진 파라미터 tag의 사용 횟수를 넣기 위한 배열
        int[] frequencyResults = new int[12];  // 12주 동안

        int restDay = LocalDate.now().getDayOfWeek().getValue();  // 요일을 int로 받기
        LocalDate fromDate = LocalDate.now().minusDays(7 * 11 - 1 + restDay);  // 시작일을 월요일로 맞추기 위해 restDay를 더해줍니다.
        int mok = 0;  // 몇 번째 주에 해당하는지 저장
        int frequency = 0;  // 해당 tag가 몇 번 사용되는지 저장

        // [각 주별로, 해당 tag가 몇 번 사용되는지 계산]
        int listSize = newJoinTagTbs.size();
        for (int i = 0; i < listSize; i++) {
            
            Long betweenDays = ChronoUnit.DAYS.between(fromDate, newJoinTagTbs.get(i).getDate());
            int tempMok = betweenDays.intValue() / 7;  // 현재 객체가, 몇 번째 주인지 저장

            // 맨 마지막 요소일 경우, 따로 frequencyResults에 frequency를 넣어줍니다.
            if (i == listSize - 1) {
                if (mok == tempMok) {
                    ++frequency;
                    frequencyResults[tempMok] = frequency;
                } else {
                    frequencyResults[tempMok] = 1;
                }
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

    public List<SortTagTime> makeJoinTbsListByTime(List<JoinWithThingsAndTagTb> joinTagTbs) {

        List<SortTagTime> list = new ArrayList<>();

        if (joinTagTbs.size() > 1) {  // joinTagTbs 크기가 2보다 클 때만 계산해줍니다.
            for (int i = 0; i < joinTagTbs.size() - 1; i++) {

                if (joinTagTbs.get(i).getCategoryId() != 1) continue;  // 카테고리(CategoryId)가 1이 아닐 경우, 제외시킵니다.

                // @@수정: 카테고리(CategoryId)가 1이 아닐 경우, 제외시키는 부분 수정하기

                JoinWithThingsAndTagTb join1 = joinTagTbs.get(i);
                JoinWithThingsAndTagTb join2 = joinTagTbs.get(i+1);

                LocalDateTime dateTime1 = join1.getDateTime();
                LocalDateTime dateTime2 = join2.getDateTime();

                long betweenMinutes = ChronoUnit.MINUTES.between(dateTime1, dateTime2);

                if (betweenMinutes < 12 * 60) {  // 두 기록(객체) 사이의 시간이 12시간 이내일 때에만, list에 기입해줍니다.
                    SortTagTime sortOne = new SortTagTime(join1.getDate(), betweenMinutes, join1.getName());
                    list.add(sortOne);
                }
            }
        }

        return list;
    }

    public List<SortTagQuantity> calculJoinTbsByTime(List<SortTagTime> sortTagTime) {

        // [List SortTagQuantity 생성]
        // 각 태그 별 몇 분의 시간을 들였는지 계산
        Map<String, Long> map = new HashMap<>();
        for (SortTagTime one : sortTagTime) {
            String tagName = one.getTagName();

            if (tagName == null) continue;  // 태그가 null인 경우는 제외시킵니다.

            if (map.containsKey(tagName)) {
                map.replace(tagName, map.get(tagName) + one.getMinutes());
            } else {
                map.put(tagName, one.getMinutes());
            }
        }

        // [배열 array 생성] (중복 사용)
        // map의 value(태그 반복 횟수)를 넣어 두기 위해
        int mapSize = map.size();
        List<Long> values = new ArrayList<>(map.values());
        Long[] array = new Long[mapSize];
        for (int i = 0; i < mapSize; i++) {
            array[i] = values.get(i);
        }
        Arrays.sort(array, Collections.reverseOrder());  // 자주 반복되는 태그를 앞에 두기 위해 reverseOrder()을 사용해줍니다.
        
        // [List list 생성] (중복 사용)
        // 카테고리(categoryId)와 태그(tagName)와 그 사용 횟수(frequency)를 SortTagQuantity에 넣어 객체 생성
        // (수정)SortTagQuantity에서 categoryId 필요 없는거 확인하고 이후 지우기
        List<SortTagQuantity> list = new ArrayList<>();
        Set<String> set = map.keySet();
        for (int i = 0; i < 5; i++) {  // 가장 자주 순으로 위에서 5개만 선택합니다.
            if (mapSize <= i) break;  // 만일 사용된 태그가 5개 이하일 경우, for문을 중간에 종료시킵니다(안 할 경우, IndexOutOfBounceException 발생).
            for (String one : set) {
                if (map.get(one) == array[i]) list.add(new SortTagQuantity(1, one, array[i].intValue() / 60));
            }
        }
        

        return list;
    }

}
