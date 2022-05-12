package records.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import account.dto.LoginInfo;
import records.RecordsService;
import records.dto.EachResultsResDto;
import records.dto.JoinThingsTagResDto;
import records.dto.SortTagQuantityResDto;
import records.dto.StoreTagTimeResDto;


@Controller
public class SortingController {
    
    private RecordsService recordsService;

    public void setSortingController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    @GetMapping("/sortingMain")
    public String sortingMain(HttpSession session, Model model) {

        // [이미 등록된 세션으로 LoginInfo 객체 생성] user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        // [12주 동안 입력된 기록(객체)들 List로 생성]
        List<JoinThingsTagResDto> joinThingTagResDtos = recordsService.selectThingsPeriod("", 12, loginId);

        // [12주 동안, 태그와 사용 횟수 객체 List를, 횟수가 높은 순서대로 정렬하여 반환]
        List<SortTagQuantityResDto> sortResDtoCategory1 = recordsService.calculJoinTbsByFrequency(joinThingTagResDtos, 1);
        List<SortTagQuantityResDto> sortResDtoCategory2 = recordsService.calculJoinTbsByFrequency(joinThingTagResDtos, 2);
        List<SortTagQuantityResDto> sortResDtoCategory3 = recordsService.calculJoinTbsByFrequency(joinThingTagResDtos, 3);
        model.addAttribute("sortResDtoCategory1", sortResDtoCategory1);
        model.addAttribute("sortResDtoCategory2", sortResDtoCategory2);
        model.addAttribute("sortResDtoCategory3", sortResDtoCategory3);

        // [12주 동안, 태그와 사용 시간 객체 List를, 시간이 높은 순서대로 정렬하여 반환]
        List<StoreTagTimeResDto> listTimeRaw = recordsService.makeJoinTbsListByTime(joinThingTagResDtos);
        List<SortTagQuantityResDto> listTime = recordsService.calculJoinTbsByTime(listTimeRaw);
        model.addAttribute("listTime", listTime);

        // [12주의 시작일 계산] (중복 코드)
        LocalDate dateStandard = LocalDate.now();
        int restDay = LocalDate.now().getDayOfWeek().getValue();  // 요일을 int로 받기
        LocalDate dateFrom = dateStandard.minusDays(7 * (12 - 1) - 1 + restDay);  // 월요일부터 시작하도록 하기 위한 계산입니다(총 12주 이내).
        // String dateStandardStr = String.valueOf(dateStandard.getYear()) + "-" + String.valueOf(dateStandard.getMonthValue()) + "-" + String.valueOf(dateStandard.getDayOfMonth());
        String dateStandardStr, dateFromStr;
        if (dateStandard.getMonthValue() <= 9) dateStandardStr = String.valueOf(dateStandard.getYear()) + "-0" + String.valueOf(dateStandard.getMonthValue());
        else dateStandardStr = String.valueOf(dateStandard.getYear()) + "-" + String.valueOf(dateStandard.getMonthValue());
        if (dateStandard.getDayOfMonth() <= 9) dateStandardStr += "-0" + String.valueOf(dateStandard.getDayOfMonth());
        else dateStandardStr += "-" + String.valueOf(dateStandard.getDayOfMonth());
        
        if (dateFrom.getMonthValue() <= 9) dateFromStr = String.valueOf(dateFrom.getYear()) + "-0" + String.valueOf(dateFrom.getMonthValue());
        else dateFromStr = String.valueOf(dateFrom.getYear()) + "-" + String.valueOf(dateFrom.getMonthValue());
        if (dateFrom.getDayOfMonth() <= 9) dateFromStr += "-0" + String.valueOf(dateFrom.getDayOfMonth());
        else dateFromStr += "-" + String.valueOf(dateFrom.getDayOfMonth());
        model.addAttribute("dateStandardStr", dateStandardStr);
        model.addAttribute("dateFromStr", dateFromStr);

        return "records/sortingMain";
    }

    @GetMapping("/sortingFrequency")
    public String sortingFrequency(HttpSession session, Model model, 
            @RequestParam(value = "category", required = true) String categoryId, 
            @RequestParam(value = "tag", required = true) String tag) {
        
        // [이미 등록된 세션으로 LoginInfo 객체 생성] user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        if (loginInfo == null) return "account/requiredLogin"; // 로그아웃 상태일 경우 안내 페이지로 연결
        Long loginId = loginInfo.getId();

        // [12주 동안 입력된 기록(객체)들 List로 생성]
        List<JoinThingsTagResDto> joinThingTagResDtos = recordsService.selectThingsPeriod("", 12, loginId);

        // [각 주마다, 파라미터로 주어진 태그의 수행 횟수인 int[] 반환]
        int[] arrayFrequency = recordsService.calculFrequency(joinThingTagResDtos, Integer.parseInt(categoryId), tag);
        model.addAttribute("arrayFrequency", arrayFrequency);

        model.addAttribute("tag", tag);
        
        return "records/sortingFrequency";
    }

    /**
     * 수행 시간별 정리 그래프 페이지 반환합니다.
     * 데이터는 RestController의 sortingTimeAjax()에서 반환합니다. 
     * @param model
     * @param dateStandardStr
     * @param weekNum
     * @param tag
     * @return
     */
    @GetMapping("/sortingTime/{dateStandardStr}/{weekNum}/{tag}")
    public String sortingTime(Model model, HttpSession session, 
            @PathVariable(value = "dateStandardStr") String dateStandardStr, 
            @PathVariable(value = "weekNum") int weekNum, 
            @PathVariable(value = "tag") String tag) {

        // [이미 등록된 세션으로 LoginInfo 객체 생성] user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        if (loginInfo == null) {
            String url = "./sortingTime/" + dateStandardStr + "/12/" + tag;
            model.addAttribute("redirectURL", url);
            return "account/loginForm";
        }
        
        model.addAttribute("weekNum", weekNum);
        model.addAttribute("tag", tag);
        return "records/sortingTime";
    }

    @GetMapping("/account/requiredLogin")
    public String requiredLogin() {
        return "account/requiredLogin";
    }

    // Ajax 테스트
    @GetMapping("/sum") 
    public String sum(){
        return "records/sum";
    }
    @RequestMapping(value="get.do") 
    @ResponseBody
    public List<EachResultsResDto> ajax() {

        // Map<String, String> rmap = new HashMap<>();
		// rmap.put("msg", "메시지입니다");
		// rmap.put("name", "test");

        // Integer[] strArr = {1,2, 3};

        List<EachResultsResDto> list = new ArrayList<>();
        list.add(new EachResultsResDto("apple", 12));
        list.add(new EachResultsResDto("banana", 13));

        return list;
    }
}
