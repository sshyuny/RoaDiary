package records.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import account.LoginInfo;
import domain.JoinWithThingsAndTagTb;
import records.RecordsService;


@Controller
public class SortingController {
    
    private RecordsService recordsService;

    public void setSortingController(RecordsService recordsService) {
        this.recordsService = recordsService;
    }

    @GetMapping("/sorting")
    public String sortingMain(HttpSession session, Model model) {
        // 이미 등록된 세션으로 LoginInfo 객체 생성 -  user key Id 가져옴
        LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");
        Long loginId = loginInfo.getId();

        List<JoinWithThingsAndTagTb> joinTagTbs = recordsService.selectThingsPeriod("", loginId);
        model.addAttribute("joinTagTbs", joinTagTbs);

        return "records/recordsSorting";
    }
}
