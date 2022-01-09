package records;

import controller.ThingsReqDto;

public class RecordsService {
    
    private RecordsDao recordsDao;

    public RecordsService(RecordsDao recordsDao) {
        this.recordsDao = recordsDao;
    }

    //===== Things 테이블에 내용 입력 =====//
    public void recordThings(ThingsReqDto thingsReqDto, Long loginId) {
        ThingsTb thingsTb = new ThingsTb(thingsReqDto.getTime(), thingsReqDto.getContent(), thingsReqDto.getCategory());
        thingsTb.setUser_id(loginId);
        recordsDao.insert(thingsTb);
    }

}
