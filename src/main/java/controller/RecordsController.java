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
    public String recordsMain(ThingsCommand thingsCommand) {
        return "records/recordsMain";
    }

    @PostMapping("/records")
    public String recordsRecording(ThingsCommand thingsCommand, HttpSession session) {
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();
        recordsService.recordThings(thingsCommand, loginId);
        return "records/recordsMain";
    }
    
}
