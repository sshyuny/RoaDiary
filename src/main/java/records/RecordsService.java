package records;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import domain.JoinWithThingsAndTagTb;
import domain.SortTagFrequency;
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
    public List<JoinWithThingsAndTagTb> selectThingsPeriod(String stringDate, Long loginId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTo;
        if (stringDate.isBlank()) {
            // date String에서 Localdate로 변환
            dateTo = LocalDate.now();
        } else {
            // date String에서 Localdate로 변환
            dateTo = LocalDate.parse(stringDate, formatter);
        }
        LocalDate dateFrom = LocalDate.of(dateTo.getYear(), dateTo.getMonthValue()-2, dateTo.getDayOfMonth());

        List<JoinWithThingsAndTagTb> joinTbList = joinDao.selectByDatePeriod(dateFrom, dateTo, loginId);
        return joinTbList;
    }
    public List<SortTagFrequency> calculJoinTbs(List<JoinWithThingsAndTagTb> joinTagTbs, int categoryId) {
        Map<String, Integer> map = new HashMap<>();

        for (JoinWithThingsAndTagTb one : joinTagTbs) {
            String tagName = one.getName();
            if ((one.getCategoryId() == categoryId) && (tagName != null)) {
                if (map.get(tagName) == null) {
                    map.put(tagName, 1);
                } else {
                    map.replace(tagName, map.get(tagName) + 1);
                }
                
            }
        }

        int mapSize = map.size();
        List<Integer> values = new ArrayList<>(map.values());
        Integer[] array = new Integer[mapSize];
        for (int i = 0; i < mapSize; i++) {
            array[i] = values.get(i);
        }
        Arrays.sort(array, Collections.reverseOrder());
        
        // 새로 객체 만들어서 태그이름, 사용 횟수, 퍼센트 들어가는 객체 만들기
        List<SortTagFrequency> list = new ArrayList<>();
        Set<String> set = map.keySet();
        for (int i = 0; i < 5; i++) {
            if (mapSize <= i) break;
            for (String one : set) {
                if (map.get(one) == array[i]) list.add(new SortTagFrequency(categoryId, one, array[i]));
            }
        }

        return list;
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

}
