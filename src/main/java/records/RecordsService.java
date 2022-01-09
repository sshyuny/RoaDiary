package records;

import domain.ThingsTb;
import records.dto.ThingsReqDto;

public class RecordsService {
    
    private RecordsDao recordsDao;

    public RecordsService(RecordsDao recordsDao) {
        this.recordsDao = recordsDao;
    }

    /**
     * Things 테이블에 내용 입력
     * @param thingsReqDto
     * @param loginId
     */
    public void insertThings(ThingsReqDto thingsReqDto, Long loginId) {
        // Thingstb 객체 생성
        ThingsTb thingsTb = new ThingsTb(thingsReqDto.getTime(), thingsReqDto.getContent(), thingsReqDto.getCategory());
        thingsTb.setUserId(loginId);
        
        // [DB]
        // recordsDao를 통해 DB에 insert
        recordsDao.insert(thingsTb);
    }

}
