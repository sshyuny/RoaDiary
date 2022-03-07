package records.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import account.LoginInfo;
import domain.JoinWithThingsAndTagTb;
import records.RecordsService;
import records.dto.SortTagQuantity;
import records.dto.StoreTagTime;


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
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsPeriod("", 12, loginId);

        // [12주 동안, 태그와 사용 횟수 객체 List를, 횟수가 높은 순서대로 정렬하여 반환]
        List<SortTagQuantity> listCategory1 = recordsService.calculJoinTbsByFrequency(joinTagTbs, 1);
        List<SortTagQuantity> listCategory2 = recordsService.calculJoinTbsByFrequency(joinTagTbs, 2);
        List<SortTagQuantity> listCategory3 = recordsService.calculJoinTbsByFrequency(joinTagTbs, 3);
        model.addAttribute("listCategory1", listCategory1);
        model.addAttribute("listCategory2", listCategory2);
        model.addAttribute("listCategory3", listCategory3);

        // [12주 동안, 태그와 사용 시간 객체 List를, 시간이 높은 순서대로 정렬하여 반환]
        List<StoreTagTime> listTimeRaw = recordsService.makeJoinTbsListByTime(joinTagTbs);
        List<SortTagQuantity> listTime = recordsService.calculJoinTbsByTime(listTimeRaw);
        model.addAttribute("listTime", listTime);

        return "records/sortingMain";
    }

    @GetMapping("/sortingFrequency")
    public String sortingFrequency(HttpSession session, Model model, 
            @RequestParam(value = "category", required = true) String categoryId, 
            @RequestParam(value = "tag", required = true) String tag) {
        
        // [이미 등록된 세션으로 LoginInfo 객체 생성] user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        // [12주 동안 입력된 기록(객체)들 List로 생성]
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsPeriod("", 12, loginId);

        // [각 주마다, 파라미터로 주어진 태그의 수행 횟수인 int[] 반환]
        int[] arrayFrequency = recordsService.calculFrequency(joinTagTbs, Integer.parseInt(categoryId), tag);
        model.addAttribute("arrayFrequency", arrayFrequency);

        model.addAttribute("tag", tag);
        
        return "records/sortingFrequency";
    }

    @GetMapping("/sortingTime")
    public String sortingTime(HttpSession session, Model model, 
            @RequestParam(value = "tag", required = true) String tag) {
        
        // [이미 등록된 세션으로 LoginInfo 객체 생성] user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        // [12주 동안 입력된 기록(객체)들 List로 생성]
        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsPeriod("", 12, loginId);

        // [날짜, 태그, 수행 시간 들어있는 객체들 List로 생성]
        List<StoreTagTime> listTimeRaw = recordsService.makeJoinTbsListByTime(joinTagTbs);

        // [각 주마다, 파라미터로 주어진 태그의 수행 시간인 int[] 반환]
        int[] arrayTime = recordsService.calculTime(listTimeRaw, tag);
        model.addAttribute("arrayTime", arrayTime);

        model.addAttribute("tag", tag);
        
        return "records/sortingTime";
    }
}
