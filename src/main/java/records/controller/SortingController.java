package records.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import account.LoginInfo;
import domain.JoinWithThingsAndTagTb;
import domain.SortTagFrequency;
import records.RecordsService;


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

        List<SortTagFrequency> listCategory1 = recordsService.calculJoinTbs(joinTagTbs, 1);
        List<SortTagFrequency> listCategory2 = recordsService.calculJoinTbs(joinTagTbs, 2);
        List<SortTagFrequency> listCategory3 = recordsService.calculJoinTbs(joinTagTbs, 3);
        model.addAttribute("listCategory1", listCategory1);
        model.addAttribute("listCategory2", listCategory2);
        model.addAttribute("listCategory3", listCategory3);

        return "records/sortingMain";
    }

    @GetMapping("/sorting")
    public String sorting(HttpSession session, Model model, 
            @RequestParam(value = "category", required = true) String categoryId, 
            @RequestParam(value = "tag", required = true) String tag) {
        
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsPeriod("", loginId);
        int[] arrayFrequency = recordsService.calculFrequency(joinTagTbs, Integer.parseInt(categoryId), tag);
        model.addAttribute("arrayFrequency", arrayFrequency);
        
        return "records/sorting";
    }
}
