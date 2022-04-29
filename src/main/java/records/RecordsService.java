package records;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.StringUtils;

import domain.JoinThingsTagDto;
import domain.TagDto;
import domain.ThingsTagDto;
import domain.ThingsDto;
import records.dao.JoinDao;
import records.dao.TagDao;
import records.dao.ThingsDao;
import records.dao.ThingsTagDao;
import records.dto.EachResultsResDto;
import records.dto.JoinThingsTagResDto;
import records.dto.SortTagQuantityResDto;
import records.dto.StoreTagTimeResDto;
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
        // ThingsDto 객체 생성
        ThingsDto thingsDto = new ThingsDto(thingsReqDto.makeDateTime(thingsReqDto.getTime(), thingsReqDto.getDate()), thingsReqDto.getContent(), thingsReqDto.getCategory());
        thingsDto.setUserId(loginId);
        
        // [DB]
        // recordsDao를 통해 DB에 insert
        Long key = thingsDao.insert(thingsDto);
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

    public List<JoinThingsTagResDto> selectThingsToday(Long loginId) {
        // [현재 시간 가져오기]
        LocalDate date = LocalDate.now();

        // [DB에서 select]
        List<JoinThingsTagDto> joinTbList =  joinDao.selectByDate(date, loginId);

        // [DB에서 가져온 List, ResDto로 변경]
        List<JoinThingsTagResDto> rawJoinResDtos = RecordsUtil.makeJoinResDtos(joinTbList);
        List<JoinThingsTagResDto> newJoinResDtos = RecordsUtil.editJoinResDtos(rawJoinResDtos);
        
        return newJoinResDtos;
    }
    public List<JoinThingsTagResDto> selectThingsSomeday(LocalDate date, Long loginId) {
        // [DB에서 select]
        List<JoinThingsTagDto> joinTbList =  joinDao.selectByDate(date, loginId);

        // [DB에서 가져온 List, ResDto로 변경]
        List<JoinThingsTagResDto> rawJoinResDtos = RecordsUtil.makeJoinResDtos(joinTbList);
        List<JoinThingsTagResDto> newJoinResDtos = RecordsUtil.editJoinResDtos(rawJoinResDtos);
        
        return newJoinResDtos;
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
                TagDto beforeTagDto = tagDao.selectByName(oneTagContent);

                // 이미 저장된 tag일 경우
                if(beforeTagDto != null) {
                    // (tag 내용 저장하는 과정 생략)

                    // ThingsTagDto 객체 생성(이미 저장된 tag의 Id값 사용)
                    ThingsTagDto thingsTagDto = new ThingsTagDto(thingsId, beforeTagDto.getTagId());

                    // [DB]
                    // Dao를 통해 DB에 insert
                    thingsTagDao.insert(thingsTagDto);

                } else { // 새로운 tag일 경우
                    // TagTb 객체 생성
                    TagDto tagDto = new TagDto(oneTagContent);

                    // [DB]
                    // Dao를 통해 DB에 insert
                    tagDao.insert(tagDto);
                    // tagTb.getTagId()를 하기 위해선 이 과정 먼저 거쳐야 됨(dao에서 setTagId함)

                    // ThingsTagDto 객체 생성
                    ThingsTagDto thingsTagDto = new ThingsTagDto(thingsId, tagDto.getTagId());

                    // [DB]
                    // Dao를 통해 DB에 insert
                    thingsTagDao.insert(thingsTagDto);
                }
            }
        }
        
    }

    public void deleteThingsTag(ThingsReqDto thingsReqDto, Long thingsId) {
        thingsTagDao.delete(thingsId);
    }

    // things테이블에 기록된 한 행 삭제
    public void deleteThingsOne(Long thingsId) {
        
        //[DB]
        // 외래키 설정이 되어있는 things_tag 테이블 기록 먼저 삭제
        //(안할시, 오류 a foreign key constraint fails 발생됨)
        thingsTagDao.delete(thingsId);

        //[DB]
        // things 테이블 기록 삭제
        thingsDao.deleteOne(thingsId);
    }

    // ====================
    //  sorting
    // ====================

    /**
     * 특정 기간에 저장된 기록들 모두 선택해서 반환
     * @param stringDate  특정 기간의 끝 날짜
     * @param weekNum  특정 기간 주(몇 주동안 저장된 기록들 가져올 지에 대한)
     * @param loginId  계정 아이디
     * @return  Join객체들 List로 반환
     */
    public List<JoinThingsTagResDto> selectThingsPeriod(String stringDate, int weekNum, Long loginId) {

        // [기준 시점 날짜 받기]
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // // dateTo 생성 과정 - 1
        // LocalDate dateTo;
        // if (stringDate.equals("")) {  // 따로 기입받지 않은 경우, 오늘이 기준 시점이 됩니다.
        //                               //(java.lang.NoSuchMethodError: java.lang.String.isBlank()Z 때문에 isBlank쓰지 않음)
        //     dateTo = LocalDate.now();
        // } else {
        //     // date String에서 Localdate로 변환
        //     try {
        //         dateTo = LocalDate.parse(stringDate, formatter);
        //     } catch(DateTimeParseException e) {
        //         dateTo = LocalDate.now();
        //          // @@ 이 경우 어떻게 url 수정할지
        //     }
        // }

        // 받은 date String을 LocalDate로 변환
        // (변환 불가할 경우 오늘 날짜를 반환)
        LocalDate dateTo = RecordsUtil.makeLocalDateFromStr(stringDate);
        // dateFrom 생성 과정
        LocalDate dateFrom = LocalDate.of(dateTo.getYear(), dateTo.getMonthValue(), dateTo.getDayOfMonth());
        int restDay = LocalDate.now().getDayOfWeek().getValue();  // 요일을 int로 받기
        dateFrom = dateFrom.minusDays(7 * (weekNum - 1) - 1 + restDay);  // 월요일부터 시작하도록 하기 위한 계산입니다(총 12주 이내).
        // dateTo 생성 과정 - 2
        dateTo = dateTo.plusDays(1); // sql에서 Between을 썼을 때, dateTo 당일이 포함되지 않기 때문에 하루를 더해줍니다.

        // [DB]
        // dateoFrom부터 dateTo까지 사이의 기록들 List로 가져오기
        List<JoinThingsTagDto> joinTbList = joinDao.selectByDatePeriod(dateFrom, dateTo, loginId);
        // [DB에서 가져온 List, ResDto로 변경]
        List<JoinThingsTagResDto> rawJoinResDtos = RecordsUtil.makeJoinResDtos(joinTbList);

        return rawJoinResDtos;
    }

    /**
     * 각 태그 별로, 총 수행 횟수를 저장하여 반환합니다(태그의 날짜 정보는 들어있지 않습니다).
     * @param joinThingTagResDtos  기록 객체들 저장돼있는 List
     * @param categoryId  카테고리(같은 태그 이름이더라도 다른 카테고리면 제외하고 계산하기 위함)
     * @return  SortTagQuantity 객체(태그, 수행 횟수 또는 시간이 매개 변수) List 반환
     */
    public List<SortTagQuantityResDto> calculJoinTbsByFrequency(List<JoinThingsTagResDto> joinThingTagResDtos, int categoryId) {

        // [Map map 생성]
        // Key: 각 태그 ; value: 해당 태그의 총 사용 횟수
        Map<String, Integer> map = new HashMap<>();

        // [map에 각 값들 넣어주기]
        for (int i = 0; i < joinThingTagResDtos.size(); i++) {
            String tagName = joinThingTagResDtos.get(i).getTagName();

            if (tagName == null) continue;  // 태그가 null인 경우는 제외시킵니다(안 할 경우 NullPointerException 발생).

            if (joinThingTagResDtos.get(i).getCategoryId() == categoryId) {
                if (map.get(tagName) == null) {
                    map.put(tagName, 1);
                } else {
                    map.replace(tagName, map.get(tagName) + 1);
                }
                
            }
        }

        // map 내용을 SortTagQualtity 객체로 저장해줍니다.
        Set<String> set = map.keySet();
        List<SortTagQuantityResDto> list = new ArrayList<>();
        for (String one : set) {
            list.add(new SortTagQuantityResDto(categoryId, one, map.get(one)));
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
    public List<SortTagQuantityResDto> calculJoinTbsByTime(List<StoreTagTimeResDto> sortTagTime) {
        // 메서드명과 다르게 JoinTbs를 계산하진 않고, SortTagTime을 계산합니다(calculJoinTbsByFrequency메서드와 메서드명 맞추기 위해).

        // [Map map 생성]
        // Key: 각 태그 ; value: 해당 태그의 총 사용 시간
        Map<String, Integer> map = new HashMap<>();
        for (StoreTagTimeResDto one : sortTagTime) {
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
        List<SortTagQuantityResDto> list = new ArrayList<>();
        for (String one : set) {
            list.add(new SortTagQuantityResDto(1, one, map.get(one)));
        }

        // 객체를 정렬해줍니다(quantity가 많은 순으로).
        Collections.sort(list);

        return list;
    }

    /**
     * 각 주마다, 선택된 태그의 총 수행 횟수를 계산합니다.
     * @param joinThingTagResDtos
     * @param categoryId
     * @param tag
     * @return  매 주의 정보가 들어있는 int[]
     */
    public int[] calculFrequency(List<JoinThingsTagResDto> joinThingTagResDtos, int categoryId, String tag) {

        // tagId를 찾습니다.
        int tagId = RecordsUtil.findTagIdFromTagNameForJoin(joinThingTagResDtos, tag);

        // [List newjoinThingTagResDtos 생성]
        // 주어진 파라미터 categoryId와 tagId와 일치하는 객체(JoinWithThingsAndTagTb)만 선택하여 새 list에 넣기
        List<JoinThingsTagResDto> newjoinThingTagResDtos = new ArrayList<>();
        for (JoinThingsTagResDto one : joinThingTagResDtos) {
            if (one.getTagName() != null) {  // tag가 없을 경우 제외시킵니다(안 할 경우 NullPointerException 발생).
                if ((one.getCategoryId() == categoryId) && (one.getTagId() == tagId)) newjoinThingTagResDtos.add(one);
            }
        }

        // 각 주마다, 해당 태그를 몇 번씩 했는지 int[]로 반환
        // 조건에 부합하는 newjoinThingTagResDtos 객체들을 파라미터로 전달
        int[] array = RecordsUtil.countEachWeekFrequency(LocalDate.now(), 12, newjoinThingTagResDtos);

        return array;
    }

    /**
     * 각 주마다, 선택된 태그의 총 수행 시간을 계산합니다.
     * @param sortTagTimes
     * @param tag
     * @return  매 주의 정보가 들어있는 int[]
     */
    public List<EachResultsResDto> calculTime(List<StoreTagTimeResDto> sortTagTimes, String dateStandardStr, String tag, int weekNum) {

        // tagId를 찾습니다.
        int tagId = RecordsUtil.findTagIdFromTagNameForStore(sortTagTimes, tag);

        // [List newSortTagTimes 생성]
        // tagId 일치하는 객체(SortTagTime)만 선택하여 새 list에 넣기
        // (categoryId는 이미 sortTagTimes를 만들 때(recordsService.makeJoinTbsListByTime) 적용했기 때문에, 여기서는 안해도 됩니다.)
        List<StoreTagTimeResDto> newSortTagTimes = new ArrayList<>();
        for (StoreTagTimeResDto one : sortTagTimes) {
            if (one.getTagName() == null) continue;  // tag가 없을 경우 제외시킵니다.
            
            if (one.getTagId() == tagId) newSortTagTimes.add(one);
        }

        // [날짜 계산]
        // LocalDate로 변환
        // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // LocalDate dateTo = LocalDate.parse(dateStandardStr, formatter);

        // 받은 date String을 LocalDate로 변환
        // (변환 불가할 경우 오늘 날짜를 반환)
        LocalDate dateTo = RecordsUtil.makeLocalDateFromStr(dateStandardStr);
        // 시작일을 월요일로 맞추기 위한 계산
        int restDay = dateTo.getDayOfWeek().getValue();  // 요일을 int로 받기
        LocalDate fromDate = dateTo.minusDays(7 * (weekNum - 1) - 1 + restDay);  // 시작일을 월요일로 맞추기 위해 restDay를 더해줍니다.

        // 각 주마다, 해당 태그를 몇 분씩 했는지 int[]로 반환
        // 각 태그별 수행 시간이 들어있는 객체 전달
        int[] arrayTime = RecordsUtil.countEachWeekTime(fromDate, weekNum, newSortTagTimes);
        String[] arrayStr = RecordsUtil.storeEachWeekDayName(fromDate, weekNum);

        // ResDto 생성
        List<EachResultsResDto> result = new ArrayList<>();
        for (int i = 0; i < weekNum; i++) {
            result.add(new EachResultsResDto(arrayStr[i], arrayTime[i]));
        }

        return result;
    }

    /**
     * 각 태그의 날짜와 날짜별 수행 시간을 객체로 저장하여 반환합니다.
     * @param joinThingTagResDtos  모든 기록 객체(정해진 기간 동안의)
     * @return  객체 StoreTagTime들의 List
     */
    public List<StoreTagTimeResDto> makeJoinTbsListByTime(List<JoinThingsTagResDto> joinThingTagResDtos) {

        // categoryId가 1일 경우에만 시간을 계산하기 위해, 새 List를 만들어줍니다.
        List<JoinThingsTagResDto> newjoinThingTagResDtos = new ArrayList<>();
        for (JoinThingsTagResDto one : joinThingTagResDtos) {
            if (one.getCategoryId() == 1) newjoinThingTagResDtos.add(one);
        }

        List<StoreTagTimeResDto> StoreTagTimeList = new ArrayList<>();
        List<Integer> tempList = new ArrayList<>();

        if (newjoinThingTagResDtos.size() > 1) {  // newjoinThingTagResDtos 크기가 2보다 클 때만 계산해줍니다.
            for (int i = 0; i < newjoinThingTagResDtos.size() - 1; i++) {

                JoinThingsTagResDto join1 = newjoinThingTagResDtos.get(i);
                JoinThingsTagResDto join2 = newjoinThingTagResDtos.get(i+1);

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
                            StoreTagTimeResDto sortOne = new StoreTagTimeResDto(newjoinThingTagResDtos.get(one).getDate(), betweenMinutes, newjoinThingTagResDtos.get(one).getTagName(), newjoinThingTagResDtos.get(one).getTagId(), newjoinThingTagResDtos.get(one).getCategoryId());
                            StoreTagTimeList.add(sortOne);
                        }
                        StoreTagTimeResDto sortOne = new StoreTagTimeResDto(join1.getDate(), betweenMinutes, join1.getTagName(), join1.getTagId(), join1.getCategoryId());
                        StoreTagTimeList.add(sortOne);
                    }
                    tempList.clear();
                }
            }
        }

        return StoreTagTimeList;
    }

}
