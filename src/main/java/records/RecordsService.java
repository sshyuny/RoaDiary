package records;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import domain.JoinWithThingsAndTagTb;
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
        // ThingsTb 객체 생성
        ThingsTb thingsTb = new ThingsTb(thingsReqDto.getTime(), thingsReqDto.getContent(), thingsReqDto.getCategory());
        thingsTb.setUserId(loginId);
        
        // [DB]
        // recordsDao를 통해 DB에 insert
        Long key = thingsDao.insert(thingsTb);
        return key;
    }

    public void updateThingsTime(ThingsReqDto thingsReqDto, Long thingsId) {
        // [DB]
        // recordsDao를 통해 DB update
        thingsDao.updateTime(thingsReqDto.getTime(), thingsId);
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
                    lastRowOfNewList.setName(newTagName);
                }
            }
        }
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

    public void updateThingsTag(ThingsReqDto thingsReqDto, Long thingsId) {
        thingsTagDao.delete(thingsId);
    }

}
