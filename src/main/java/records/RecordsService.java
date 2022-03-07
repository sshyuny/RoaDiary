package records;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import domain.JoinWithThingsAndTagTb;
import domain.TagTb;
import domain.ThingsTagTb;
import domain.ThingsTb;
import records.dto.SortTagQuantity;
import records.dto.StoreTagTime;
import records.dto.ThingsReqDto;
import records.validator.RecordsUtil;

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
                    String tagNameNewList = lastRowOfNewList.getTagName();
                    String tagNameList = "#" + secondRow.getTagName();
                    String newTagName =tagNameNewList +  tagNameList;
                    // lastRowOfNewList.setName(newTagName); //[?] 왜 아래 코드 대신, 이거만 써도 잘 작동되는지 물어보기
                    newJoinTbList.get(lastIdxOfnewJoinList).setTagName(newTagName);
                }
            }
        }
        // 태그 있는 경우, 앞에 "#" 붙이기
        for(JoinWithThingsAndTagTb rowTb : newJoinTbList) {
            String rowTbTagName = rowTb.getTagName();
            if(StringUtils.hasText(rowTbTagName)) {
                rowTb.setTagName("#" + rowTbTagName);
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

    // ====================
    //  sorting
    // ====================

    /**
     * 특정 기간에 저장된 기록들 모두 선택해서 반환
     * @param stringDate  특정 기간의 끝 날짜
     * @param loginId  계정 아이디
     * @return  Join객체들 List로 반환
     */
    public List<JoinWithThingsAndTagTb> selectThingsPeriod(String stringDate, Long loginId) {

        // [기준 시점 날짜 받기]
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

    /**
     * 각 태그 별로, 총 수행 횟수를 저장하여 반환합니다(태그의 날짜 정보는 들어있지 않습니다).
     * @param joinTagTbs  기록 객체들 저장돼있는 List
     * @param categoryId  카테고리(같은 태그 이름이더라도 다른 카테고리면 제외하고 계산하기 위함)
     * @return  SortTagQuantity 객체(태그, 수행 횟수 또는 시간이 매개 변수) List 반환
     */
    public List<SortTagQuantity> calculJoinTbsByFrequency(List<JoinWithThingsAndTagTb> joinTagTbs, int categoryId) {

        // [Map map 생성]
        // Key: 각 태그 ; value: 해당 태그의 총 사용 횟수
        Map<String, Integer> map = new HashMap<>();

        // [map에 각 값들 넣어주기]
        for (int i = 0; i < joinTagTbs.size(); i++) {
            String tagName = joinTagTbs.get(i).getTagName();

            if (tagName == null) continue;  // 태그가 null인 경우는 제외시킵니다(안 할 경우 NullPointerException 발생).

            if (joinTagTbs.get(i).getCategoryId() == categoryId) {
                if (map.get(tagName) == null) {
                    map.put(tagName, 1);
                } else {
                    map.replace(tagName, map.get(tagName) + 1);
                }
                
            }
        }

        // map 내용을 SortTagQualtity 객체로 저장해줍니다.
        Set<String> set = map.keySet();
        List<SortTagQuantity> list = new ArrayList<>();
        for (String one : set) {
            list.add(new SortTagQuantity(categoryId, one, map.get(one)));
        }

        // 객체를 정렬해줍니다(quantity가 많은 순으로).
        Collections.sort(list);
        return list;
    }

    /**
     * 각 태그 별로, 총 수행 시간을 저장하여 반환합니다(태그의 날짜 정보는 들어있지 않습니다).
     * @param sortTagTime  태그 기록 객체(태그, 입력된 날짜, 수행 시간이 매개변수)들이 저장된 List
     * @return  SortTagQuantity 객체(태그, 수행 횟수 또는 시간이 매개 변수) List 반환
     */
    public List<SortTagQuantity> calculJoinTbsByTime(List<StoreTagTime> sortTagTime) {
        // 메서드명과 다르게 JoinTbs를 계산하진 않고, SortTagTime을 계산합니다(calculJoinTbsByFrequency메서드와 메서드명 맞추기 위해).

        // [Map map 생성]
        // Key: 각 태그 ; value: 해당 태그의 총 사용 시간
        Map<String, Integer> map = new HashMap<>();
        for (StoreTagTime one : sortTagTime) {
            String tagName = one.getTagName();

            if (tagName == null) continue;  // 태그가 null인 경우는 제외시킵니다(안 할 경우 NullPointerException 발생).

            if (map.containsKey(tagName)) {
                map.replace(tagName, map.get(tagName) + one.getMinutes().intValue());
            } else {
                map.put(tagName, one.getMinutes().intValue());
            }
        }

        // map 내용을 SortTagQualtity 객체로 저장해줍니다.
        Set<String> set = map.keySet();
        List<SortTagQuantity> list = new ArrayList<>();
        for (String one : set) {
            list.add(new SortTagQuantity(1, one, map.get(one)));
        }

        // 객체를 정렬해줍니다(quantity가 많은 순으로).
        Collections.sort(list);

        return list;
    }

    /**
     * 각 주마다, 선택된 태그의 총 수행 횟수를 계산합니다.
     * @param joinTagTbs
     * @param categoryId
     * @param tag
     * @return  매 주의 정보가 들어있는 int[]
     */
    public int[] calculFrequency(List<JoinWithThingsAndTagTb> joinTagTbs, int categoryId, String tag) {

        // tagId를 찾습니다.
        int tagId = RecordsUtil.findTagIdFromTagName(joinTagTbs, tag);

        // [List newJoinTagTbs 생성]
        // 주어진 파라미터 categoryId와 tagId와 일치하는 객체(JoinWithThingsAndTagTb)만 선택하여 새 list에 넣기
        List<JoinWithThingsAndTagTb> newJoinTagTbs = new ArrayList<>();
        for (JoinWithThingsAndTagTb one : joinTagTbs) {
            if (one.getTagName() != null) {  // tag가 없을 경우 제외시킵니다(안 할 경우 NullPointerException 발생).
                if ((one.getCategoryId() == categoryId) && (one.getTagId() == tagId)) newJoinTagTbs.add(one);
            }
        }

        // 각 주마다, 해당 태그를 몇 번씩 했는지 int[]로 반환
        // 조건에 부합하는 newJoinTagTbs 객체들을 파라미터로 전달
        int[] array = RecordsUtil.countEachWeekFrequency(LocalDate.now(), 12, newJoinTagTbs);

        return array;
    }

    /**
     * 각 주마다, 선택된 태그의 총 수행 시간을 계산합니다.
     * @param sortTagTimes
     * @param tag
     * @return  매 주의 정보가 들어있는 int[]
     */
    public int[] calculTime(List<StoreTagTime> sortTagTimes, String tag) {

        // tagId를 찾습니다.
        int tagId = RecordsUtil.findTagIdFromTagName(sortTagTimes, tag);

        // @@ 수정: 카테고리와 태그 일치하는 것 골라서 전달 가능하면 수정하기

        // 각 주마다, 해당 태그를 몇 분씩 했는지 int[]로 반환
        // 각 태그별 수행 시간이 들어있는 객체 전달
        int[] array = RecordsUtil.countEachWeekTime(LocalDate.now(), 12, sortTagTimes, tagId);

        return array;
    }

    /**
     * 각 태그의 날짜와 날짜별 수행 시간을 객체로 저장하여 반환합니다.
     * @param joinTagTbs  모든 기록 객체(정해진 기간 동안의)
     * @return  객체 StoreTagTime들의 List
     */
    public List<StoreTagTime> makeJoinTbsListByTime(List<JoinWithThingsAndTagTb> joinTagTbs) {

        // categoryId가 1일 경우에만 시간을 계산하기 위해, 새 List를 만들어줍니다.
        List<JoinWithThingsAndTagTb> newJoinTagTbs = new ArrayList<>();
        for (JoinWithThingsAndTagTb one : joinTagTbs) {
            if (one.getCategoryId() == 1) newJoinTagTbs.add(one);
        }

        List<StoreTagTime> StoreTagTimeList = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();

        if (newJoinTagTbs.size() > 1) {  // newJoinTagTbs 크기가 2보다 클 때만 계산해줍니다.
            for (int i = 0; i < newJoinTagTbs.size() - 1; i++) {

                JoinWithThingsAndTagTb join1 = newJoinTagTbs.get(i);
                JoinWithThingsAndTagTb join2 = newJoinTagTbs.get(i+1);

                LocalDateTime dateTime1 = join1.getDateTime();
                LocalDateTime dateTime2 = join2.getDateTime();
                long betweenMinutes = ChronoUnit.MINUTES.between(dateTime1, dateTime2);

                long tempThingsId1 = join1.getThingsId();
                long tempThingsId2 = join2.getThingsId();

                // 하나의 기록에 여러 태그가 기입된 경우, 해당 태그들에 같은 시간을 기입하기 위한 부분입니다.
                if (tempThingsId1 == tempThingsId2) {
                    tempList.add(i);
                } else {
                    if (betweenMinutes < 12 * 60) {
                        for (Integer one : tempList) {
                            StoreTagTime sortOne = new StoreTagTime(newJoinTagTbs.get(one).getDate(), betweenMinutes, newJoinTagTbs.get(one).getTagName(), newJoinTagTbs.get(one).getTagId(), newJoinTagTbs.get(one).getCategoryId());
                            StoreTagTimeList.add(sortOne);
                        }
                        StoreTagTime sortOne = new StoreTagTime(join1.getDate(), betweenMinutes, join1.getTagName(), join1.getTagId(), join1.getCategoryId());
                        StoreTagTimeList.add(sortOne);
                    }
                    tempList.clear();
                }
            }
        }

        return StoreTagTimeList;
    }

}
