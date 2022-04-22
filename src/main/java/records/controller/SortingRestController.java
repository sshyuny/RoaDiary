package records.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import account.dto.LoginInfo;
import records.RecordsService;
import records.dto.JoinThingsTagResDto;
import records.dto.StoreTagTimeResDto;

@RestController
public class SortingRestController {
    private RecordsService recordsService;

    public void setSortingRestController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }
    
    @RequestMapping(value="getSortingTime") 
    public Map<String, String> sortingTimeAjax(HttpSession session, 
            @RequestParam(value = "tag", required = false) String tag) {


        // [이미 등록된 세션으로 LoginInfo 객체 생성] user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        // if (loginInfo == null) return "account/requiredLogin"; // 로그아웃 상태일 경우 안내 페이지로 연결
        Long loginId = loginInfo.getId();

        // [12주 동안 입력된 기록(객체)들 List로 생성]
        List<JoinThingsTagResDto> joinThingTagResDtos = recordsService.selectThingsPeriod("", 12, loginId);

        // [날짜, 태그, 수행 시간 들어있는 객체들 List로 생성]
        List<StoreTagTimeResDto> listTimeRaw = recordsService.makeJoinTbsListByTime(joinThingTagResDtos);

        // [각 주마다, 파라미터로 주어진 태그의 수행 시간인 int[] 반환]
        int[] arrayTime = recordsService.calculTime(listTimeRaw, tag);

        Map<String, String> map = new HashMap<>();
        // model.addAttribute("tag", tag);
        // model.addAttribute("arrayTime", arrayTime);
        for (int i = 0; i < arrayTime.length; i++) {
            map.put("week" + i, arrayTime[i] + "");
        }
        // map.put("a", value)
        return map;
    }
}
