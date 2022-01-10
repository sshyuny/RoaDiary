package records;

import java.time.LocalDate;
import java.util.List;

import domain.TagTb;
import domain.ThingsTagTb;
import domain.ThingsTb;
import records.dto.ThingsReqDto;

public class RecordsService {
    
    private RecordsDao recordsDao;
    private TagDao tagDao;
    private ThingsTagDao thingsTagDao;

    public RecordsService(RecordsDao recordsDao, TagDao tagDao, ThingsTagDao thingsTagDao) {
        this.recordsDao = recordsDao;
        this.tagDao = tagDao;
        this.thingsTagDao = thingsTagDao;
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
        Long key = recordsDao.insert(thingsTb);
        return key;
    }

    public List<ThingsTb> selectThings(Long loginId) {
        // 현재 시간 가져오기
        LocalDate localDate = LocalDate.now();

        // [DB]
        // recordsDao를 통해 DB에서 select
        return recordsDao.selectByDate(localDate, loginId);
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
            if(! oneTagContent.isBlank() ) {

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

}
