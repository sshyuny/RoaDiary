package records.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import account.LoginInfo;
import records.RecordsService;
import records.dto.ThingsReqDto;

@Controller
public class RecordsController {

    private RecordsService recordsService;

    public void setRecordsController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    //===== ===== ===== =====//
    // 홈페이지
    //===== ===== ===== =====//
    @GetMapping("/records")
    public String recordsMain(ThingsReqDto thingsReqDto) {
        return "records/recordsMain";
    }

    /**
     * things 테이블에 기록
     * @param thingsReqDto  // dto
     * @param session       // 이미 등록된 세션 가져오기 위한
     * @return
     */
    @PostMapping("/records")
    public String recordsRecording(ThingsReqDto thingsReqDto, HttpSession session) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();
        // things 테이블에 기록
        recordsService.insertThings(thingsReqDto, loginId);
        return "records/recordsMain";
    }
    
}
