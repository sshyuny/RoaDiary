package records.controller;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import account.LoginInfo;
import domain.JoinWithThingsAndTagTb;
import domain.SortTagQuantity;
import domain.SortTagTime;
import records.RecordsService;
import records.validator.RecordsUtil;


@Controller
public class SortingController {
    
    private RecordsService recordsService;

    public void setSortingController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    @GetMapping("/sortingMain")
    public String sortingMain(HttpSession session, Model model) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsPeriod("", loginId);
        // model.addAttribute("joinTagTbs", joinTagTbs);

        List<SortTagQuantity> listCategory1 = recordsService.calculJoinTbsByFrequency(joinTagTbs, 1);
        List<SortTagQuantity> listCategory2 = recordsService.calculJoinTbsByFrequency(joinTagTbs, 2);
        List<SortTagQuantity> listCategory3 = recordsService.calculJoinTbsByFrequency(joinTagTbs, 3);
        model.addAttribute("listCategory1", listCategory1);
        model.addAttribute("listCategory2", listCategory2);
        model.addAttribute("listCategory3", listCategory3);

        List<SortTagTime> listTimeRaw = recordsService.makeJoinTbsListByTime(joinTagTbs);
        List<SortTagQuantity> listTime = recordsService.calculJoinTbsByTime(listTimeRaw);
        model.addAttribute("listTime", listTime);

        return "records/sortingMain";
    }

    @GetMapping("/sortingFrequency")
    public String sortingFrequency(HttpSession session, Model model, 
            @RequestParam(value = "category", required = true) String categoryId, 
            @RequestParam(value = "tag", required = true) String tag) {
        
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsPeriod("", loginId);
        int[] arrayFrequency = recordsService.calculFrequency(joinTagTbs, Integer.parseInt(categoryId), tag);
        model.addAttribute("arrayFrequency", arrayFrequency);
        
        return "records/sortingFrequency";
    }

    @GetMapping("/sortingTime")
    public String sortingTime(HttpSession session, Model model, 
            @RequestParam(value = "tag", required = true) String tag) {
        
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsPeriod("", loginId);
        List<SortTagTime> listTimeRaw = recordsService.makeJoinTbsListByTime(joinTagTbs);
        int[] arrayTime = recordsService.calculTime(listTimeRaw, tag);
        model.addAttribute("arrayTime", arrayTime);
        
        return "records/sortingTime";
    }
}
