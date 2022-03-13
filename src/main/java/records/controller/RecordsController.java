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
    @RequestMapping("/records")
    public String recordsMain(ThingsReqDto thingsReqDto, HttpSession session, Model model) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();
        // 오늘 기록된 ThingsTb 행들, DB에서 가져옴
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsToday(loginId);
        model.addAttribute("joinTagTbs", joinTagTbs);
        // '오늘' 단어 전하기
        model.addAttribute("stringDate", "오늘");

        // return
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
    /**
     * recordsShowing와 거의 비슷합니다. <input type="date">로 들어온 value를 처리하기 위해 따로 작성한 메서드입니다.
     * @param thingsReqDto
     * @param request
     * @param session
     * @param model
     * @return
     */
    @PostMapping("/recordsShowingCalander")
    public String recordsShowingCalander(ThingsReqDto thingsReqDto, HttpServletRequest request, HttpSession session, Model model) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        // 요청된 날 가져오기
        String calanderDayStr = request.getParameter("calanderDay");
        model.addAttribute("calanderDay", calanderDayStr);
        model.addAttribute("stringDate", calanderDayStr);  // stringDate에도 넣어줍니다.
        // 요청된 날에 기록된 ThingsTb 행들, DB에서 가져옴
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsSomeday(calanderDayStr, loginId);
        model.addAttribute("joinTagTbs", joinTagTbs);

        // 오늘, 어제 등에 해당할 경우, 날짜를 숫자가 아닌 글로 보여주기 위함
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Period period = Period.between(LocalDate.now(), LocalDate.parse(calanderDayStr, formatter));
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

    // 기록 변경 또는 삭제할 때
    @PostMapping("/recordsChange")
    public String recordsChange(ThingsReqDto thingsReqDto, HttpServletRequest request, HttpSession session) {

        // thingsId 가져오기
        String thingsIdStr = request.getParameter("thingsId");
        Long thingsId = Long.valueOf(thingsIdStr);

        // 수정 버튼을 눌렀는지, 삭제 버튼을 눌렀는지 알아보려는 부분
        String save = request.getParameter("save");
        String delete = request.getParameter("delete");

        // [수정하려는 경우]
        if (save != null) {
            // time 변경: things 테이블 update
            if (thingsReqDto.getTime() != null) {
                recordsService.updateThingsTime(thingsReqDto, thingsId);
            }
            // 태그 변경: things 테이블 update
            // ((!String.valueOf(thingsReqDto.getTag1()).isBlank()) || (!String.valueOf(thingsReqDto.getTag2()).isBlank())
            // ||(!String.valueOf(thingsReqDto.getTag3()).isBlank()) ||(!String.valueOf(thingsReqDto.getTag4()).isBlank()))
            if ((String.valueOf(thingsReqDto.getTag1())!="") || (String.valueOf(thingsReqDto.getTag2())!="")
                ||(String.valueOf(thingsReqDto.getTag3())!="") ||(String.valueOf(thingsReqDto.getTag4())!="")) {
                recordsService.deleteThingsTag(thingsReqDto, thingsId);
                recordsService.insertTags(thingsReqDto, thingsId);
            }
            // content 변경: things 테이블 update
            if (thingsReqDto.getContent()!="") {
                recordsService.updateThingsContent(thingsReqDto, thingsId);
            }
        // [삭제하려는 경우]
        } else if (delete != null) {
            // thingsId에 해당하는 기록 지우기
            recordsService.deleteThingsOne(thingsId);
        }
        
        return "records/recordsMain";
    }

    // 빠른 내용 추가
    @PostMapping("/recordsQuickInsert")
    public String recordsQuickInsert(ThingsReqDto thingsReqDto, HttpServletRequest request, HttpSession session) {
        
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        // 빠른 추가를 하려는, 시간 가져오기
        int eachHour = Integer.valueOf(request.getParameter("eachHour"));

        // 객체(thingsReqDto)에 시간을 저장
        thingsReqDto.setTime(LocalTime.of(eachHour, 0));

        // things 테이블에 객체(thingsReqDto)를 기록
        Long key = recordsService.insertThings(thingsReqDto, loginId);

        // tag 테이블과 things_tag 테이블에 기록
        recordsService.insertTags(thingsReqDto, key);
        
        return "records/recordsMain";
    }
    
}
