package controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import account.LoginInfo;
import records.RecordsService;

@Controller
public class RecordsController {

    private RecordsService recordsService;

    public void setRecordsController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    //===== 홈페이지 =====//
    @GetMapping("/records")
    public String recordsMain(ThingsReqDto thingsReqDto) {
        return "records/recordsMain";
    }

    /**
     * 
     * @param thingsReqDto
     * @param session
     * @return
     */
    @PostMapping("/records")
    public String recordsRecording(ThingsReqDto thingsReqDto, HttpSession session) {
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();
        recordsService.recordThings(thingsReqDto, loginId);
        return "records/recordsMain";
    }
    
}
