package records.controller;

import java.time.LocalDate;
import java.time.LocalTime;
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
import records.RecordsUtil;
import records.dto.ThingsReqDto;

@Controller
public class RecordsController {

    private RecordsService recordsService;

    public void setRecordsController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    /**
     * 메인 페이지입니다.
     * @param thingsReqDto
     * @param session
     * @param model
     * @return
     */
    @GetMapping("/records")
    public String recordsMain(ThingsReqDto thingsReqDto, HttpSession session, Model model) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();
        // 오늘 기록된 ThingsTb 행들, DB에서 가져옴
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsToday(loginId);
        model.addAttribute("joinTagTbs", joinTagTbs);
        // '오늘' 단어 전하기
        model.addAttribute("stringDate", "오늘");
        // '오늘' LocalDate 전하기
        model.addAttribute("onlyDate", LocalDate.now());

        return "records/recordsMain";
    }

    /**
     * things 테이블에 사용자가 보낸 데이터를 기록합니다.
     * @param thingsReqDto  사용자가 보낸 데이터
     * @param session
     * @return
     */
    @PostMapping("/records")
    public String recordsRecording(ThingsReqDto thingsReqDto, HttpSession session, Model model) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        // [전송된 데이터를 DB에 저장]
        // things 테이블에 기록
        Long key = recordsService.insertThings(thingsReqDto, loginId);
        // tag 테이블과 things_tag 테이블에 기록
        recordsService.insertTags(thingsReqDto, key);

        // [클라이언트에 전송할 데이터 처리] 
        // (클라이언트가 입력한 기록의 날짜로 페이지 화면을 바꿔주기 위한 부분입니다.)
        // 요청된 날에 기록된 ThingsTb 행들 가져오기
        LocalDate requestedDate = thingsReqDto.getDate();
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsSomeday(requestedDate, loginId);
        // 요청된 날(requestedDate)을 String으로 변환하기(어제, 오늘 등의 한글로 변환될 수도 있습니다.)
        String stringDate = RecordsUtil.fromLocalDatetoString(requestedDate);

        // [클라이언트에 변수들 보내기]
        model.addAttribute("joinTagTbs", joinTagTbs);
        model.addAttribute("onlyDate", requestedDate);
        model.addAttribute("stringDate", stringDate);
        
        return "records/recordsMain";
    }

    @RequestMapping("/recordsShow")
    public String recordsShowing(ThingsReqDto thingsReqDto, HttpServletRequest request, HttpSession session, Model model) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        // 어떤 버튼 눌렀는지 확인하기 위한 변수
        String minusDate = request.getParameter("minusDate");
        String plusDate = request.getParameter("plusDate");

        // 날짜 변수 가져오기
        String onlyDateStr = request.getParameter("onlyDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate onlyDate = LocalDate.now();  // 기본값으로 오늘을 넣어둡니다.

        // 클라이언트가 누른 버튼에 맞춰, 날짜 변경하기
        if (plusDate != null) {
            onlyDate = LocalDate.parse(onlyDateStr, formatter);
            onlyDate = onlyDate.plusDays(1);
        } else if (minusDate != null) {
            onlyDate = LocalDate.parse(onlyDateStr, formatter);
            onlyDate = onlyDate.plusDays(-1);
        }
        
        // [클라이언트에 전송할 데이터 처리] 
        // 요청된 날에 기록된 ThingsTb 행들 가져오기
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsSomeday(onlyDate, loginId);
        // 요청된 날(onlyDate)를 String으로 변환하기(어제, 오늘 등의 한글로 변환될 수도 있습니다.)
        String stringDate = RecordsUtil.fromLocalDatetoString(onlyDate);

        // [클라이언트에 변수들 보내기]
        model.addAttribute("joinTagTbs", joinTagTbs);
        model.addAttribute("onlyDate", onlyDate);
        model.addAttribute("stringDate", stringDate);

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

        // [클라이언트가 요청하는 날짜]
        // 요청된 날 가져오기
        String calanderDayStr = request.getParameter("calanderDay");
        LocalDate calanderDay;
        if (calanderDayStr.length() < 4) {
            // 사용자가, 달력에서 아무것도 선택하지 않고(value=null) submit했을때, 오늘을 넣어 예외(DateTimeParseException)를 피해줍니다.
            calanderDay = LocalDate.now();
        } else {
            // 요청된 날 LocalDate로 변환
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            calanderDay = LocalDate.parse(calanderDayStr, formatter);
        }

        // [클라이언트에 전송할 데이터 처리] 
        // 요청된 날에 기록된 ThingsTb 행들 가져오기
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsSomeday(calanderDay, loginId);
        // 요청된 날을 String으로 변환하기(어제, 오늘 등의 한글로 변환될 수도 있습니다.)
        String stringDate = RecordsUtil.fromLocalDatetoString(calanderDay);

        // [클라이언트에 변수들 보내기]
        model.addAttribute("calanderDay", calanderDayStr);
        model.addAttribute("onlyDate", calanderDay);
        model.addAttribute("joinTagTbs", joinTagTbs);
        model.addAttribute("stringDate", stringDate);

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
    public String recordsQuickInsert(ThingsReqDto thingsReqDto, HttpServletRequest request, HttpSession session, Model model) {
        // [클라이언트에서 데이터 가져오기]
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();
        // 빠른 추가를 하려는, 시간 가져오기
        int eachHour = Integer.valueOf(request.getParameter("eachHour"));
        // 빠른 추가를 하려는, 날짜 가져오기
        String onlyDateStr = request.getParameter("onlyDate");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate onlyDate = LocalDate.parse(onlyDateStr, formatter);

        // [DB에 저장할 객체 만들기]
        // 객체(thingsReqDto)에 시간, 날짜를 저장
        thingsReqDto.setTime(LocalTime.of(eachHour, 0));
        thingsReqDto.setDate(onlyDate);

        // [DB에 기록]
        // things 테이블에 객체(thingsReqDto)를 기록
        Long key = recordsService.insertThings(thingsReqDto, loginId);
        // tag 테이블과 things_tag 테이블에 기록
        recordsService.insertTags(thingsReqDto, key);

        // [클라이언트에 전송할 데이터 처리] 
        // (클라이언트가 입력한 기록의 날짜로 페이지 화면을 바꿔주기 위한 부분입니다.)
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsSomeday(onlyDate, loginId);
        // 요청된 날(requestedDate)을 String으로 변환하기(어제, 오늘 등의 한글로 변환될 수도 있습니다.)
        String stringDate = RecordsUtil.fromLocalDatetoString(onlyDate);

        // [클라이언트에 변수들 보내기]
        model.addAttribute("joinTagTbs", joinTagTbs);
        model.addAttribute("onlyDate", onlyDate);
        model.addAttribute("stringDate", stringDate);
        
        return "records/recordsMain";
    }
    
}
