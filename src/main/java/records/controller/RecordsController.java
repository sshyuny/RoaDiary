package records.controller;

import java.io.Console;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import account.LoginInfo;
import domain.JoinWithThingsAndTagTb;
import domain.ThingsTb;
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
    public String recordsMain(ThingsReqDto thingsReqDto, HttpSession session, Model model) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();
        // 오늘 기록된 ThingsTb 행들, DB에서 가져옴
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsToday(loginId);
        model.addAttribute("joinTagTbs", joinTagTbs);
        // 
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
        Long key = recordsService.insertThings(thingsReqDto, loginId);

        // tag 테이블과 things_tag 테이블에 기록
        recordsService.insertTags(thingsReqDto, key);
        
        //
        return "records/recordsMain";
    }
    @RequestMapping("/recordsShow")
    public String recordsShowing(ThingsReqDto thingsReqDto, HttpServletRequest request, HttpSession session, Model model) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        // 요청된 날 가져오기
        String stringDate = request.getParameter("someday");
        if (stringDate.isBlank()) {
            LocalDate localDate = LocalDate.now();
            stringDate = localDate.toString();
        }
        model.addAttribute("stringDate", stringDate);
        // 요청된 날에 기록된 ThingsTb 행들, DB에서 가져옴
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsSomeday(stringDate, loginId);
        model.addAttribute("joinTagTbs", joinTagTbs);
        // 
        return "records/recordsMain";
    }
    @PostMapping("/recordsChange")  // 수정중!!!
    public String recordsChange(ThingsReqDto thingsReqDto, HttpServletRequest request, HttpSession session) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        //LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        //Long loginId = loginInfo.getId();

        // thingsId 가져오기
        String thingsIdStr = request.getParameter("thingsId");
        Long thingsId = Long.valueOf(thingsIdStr);

        // things 테이블 update
        recordsService.updateThingsTime(thingsReqDto, thingsId);

        // tag 테이블과 things_tag 테이블 update
        //recordsService.updateTags(thingsReqDto, thingsId);
        
        //
        return "records/recordsMain";
    }
    
}
