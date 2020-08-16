package org.worldbuild.aws.web;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.worldbuild.aws.constant.PageViewDetail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Data
@Log4j2
@Controller
public class RootController {


    @RequestMapping("/intex1.html")
    public String index1HandlerView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        modelMap.addAttribute("pageName",PageViewDetail.DASHBOARD_V1.pageName);
        return PageViewDetail.DASHBOARD_V1.path;
    }

    @RequestMapping("/intex2.html")
    public String index2HandlerView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        modelMap.addAttribute("pageName",PageViewDetail.DASHBOARD_V2.pageName);
        return PageViewDetail.DASHBOARD_V2.path;
    }

    @RequestMapping("/intex3.html")
    public String index3HandlerView(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        modelMap.addAttribute("pageName",PageViewDetail.DASHBOARD_V3.pageName);
        return PageViewDetail.DASHBOARD_V3.path;
    }
}
