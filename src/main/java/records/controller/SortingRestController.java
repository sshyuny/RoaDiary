package records.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import account.dto.LoginInfo;
import records.RecordsService;
import records.dto.EachResultsResDto;
import records.dto.JoinThingsTagResDto;
import records.dto.StoreTagTimeResDto;

@RestController
public class SortingRestController {
    private RecordsService recordsService;

    public void setSortingRestController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }
    
    @GetMapping(value="/getSortingTime/{dateStandardStr}/{weekNum}/{tag}") 
    public List<EachResultsResDto> sortingTimeAjax(HttpSession session, 
            @PathVariable(value = "dateStandardStr") String dateStandardStr, 
            @PathVariable(value = "weekNum") int weekNum, 
            @PathVariable(value = "tag") String tag) {

        List<EachResultsResDto> result = new ArrayList<>();

        // [이미 등록된 세션으로 LoginInfo 객체 생성] user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        // if (loginInfo == null) {
        //     result.add(new EachResultsResDto("noLoginInfo", 0));
        //     return result;
        // }
        Long loginId = loginInfo.getId();

        // [weekNum에 이상값 들어올 경우 12로 바꿔줌]
        if (weekNum <= 0 || weekNum > 16) weekNum = 12;

        // [weekNum주 동안 입력된 기록(객체)들 List로 생성]
        List<JoinThingsTagResDto> joinThingTagResDtos = recordsService.selectThingsPeriod(dateStandardStr, weekNum, loginId);

        // [날짜, 태그, 수행 시간 들어있는 객체들 List로 생성]
        List<StoreTagTimeResDto> listTimeRaw = recordsService.makeJoinTbsListByTime(joinThingTagResDtos);

        // [각 주마다, 파라미터로 주어진 태그의 수행 시간인 int[] 반환]
        result = recordsService.calculTime(listTimeRaw, dateStandardStr, tag, weekNum);

        return result;
    }
}
