package records;

import controller.ThingsCommand;

public class RecordsService {
    
    private RecordsDao recordsDao;

    public RecordsService(RecordsDao recordsDao) {
        this.recordsDao = recordsDao;
    }

    //===== Things 테이블에 내용 입력 =====//
    public void recordThings(ThingsCommand thingsCommand, Long loginId) {
        ThingsTb thingsTb = new ThingsTb(thingsCommand.getTime(), thingsCommand.getContent(), thingsCommand.getCategory());
        thingsTb.setUser_id(loginId);
        recordsDao.insert(thingsTb);
    }

}
