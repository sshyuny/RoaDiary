package records.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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
        // '오늘' 단어 전하기
        // model.addAttribute("stringDate", LocalDate.now().getYear() + "-" + LocalDate.now().getMonth() + "-" + LocalDate.now().getDayOfMonth());
        model.addAttribute("stringDate", "오늘");
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
        model.addAttribute("stringDate", stringDate);
        // 요청된 날에 기록된 ThingsTb 행들, DB에서 가져옴
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsSomeday(stringDate, loginId);
        model.addAttribute("joinTagTbs", joinTagTbs);

        // 오늘, 어제 등에 해당할 경우, 날짜를 숫자가 아닌 글로 보여주기 위함
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Period period = Period.between(LocalDate.now(), LocalDate.parse(stringDate, formatter));
        if (period.getDays() == 0) {
            model.addAttribute("stringDate", "오늘");
        } else if (period.getDays() == -1) {
            model.addAttribute("stringDate", "어제");
        } else if (period.getDays() == -2) {
            model.addAttribute("stringDate", "그제");
        } else if (period.getDays() == 1) {
            model.addAttribute("stringDate", "내일");
        } else if (period.getDays() == 2) {
            model.addAttribute("stringDate", "모레");
        }

        // return
        return "records/recordsMain";
    }
    // 내용 변경할 때
    @PostMapping("/recordsChange")
    public String recordsChange(ThingsReqDto thingsReqDto, HttpServletRequest request, HttpSession session) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        //LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        //Long loginId = loginInfo.getId();

        // thingsId 가져오기
        String thingsIdStr = request.getParameter("thingsId");
        Long thingsId = Long.valueOf(thingsIdStr);

        // time 변경: things 테이블 update
        if (thingsReqDto.getTime() != null) {
            recordsService.updateThingsTime(thingsReqDto, thingsId);
        }
        // 태그 변경: things 테이블 update
        if ((!String.valueOf(thingsReqDto.getTag1()).isBlank()) || (!String.valueOf(thingsReqDto.getTag2()).isBlank())
            ||(!String.valueOf(thingsReqDto.getTag3()).isBlank()) ||(!String.valueOf(thingsReqDto.getTag4()).isBlank())) {
            recordsService.deleteThingsTag(thingsReqDto, thingsId);
            recordsService.insertTags(thingsReqDto, thingsId);
        }
        // content 변경: things 테이블 update
        if (!thingsReqDto.getContent().isBlank()) {
            recordsService.updateThingsContent(thingsReqDto, thingsId);
        }
        
        //
        return "records/recordsMain";
    }

    // 빠른 내용 변경
    @PostMapping("/recordsQuickInsert")
    public String recordsQuickInsert(ThingsReqDto thingsReqDto, HttpServletRequest request, HttpSession session) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        int eachHour = Integer.valueOf(request.getParameter("eachHour"));

        thingsReqDto.setTime(LocalTime.of(eachHour, 0));

        // things 테이블에 기록
        Long key = recordsService.insertThings(thingsReqDto, loginId);

        // tag 테이블과 things_tag 테이블에 기록
        recordsService.insertTags(thingsReqDto, key);
        
        //
        return "records/recordsMain";
    }
    
}
