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
public class GalleryController {

    @RequestMapping("/gallery.html")
    public String galleryViewHandler(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        modelMap.addAttribute("pageName", PageViewDetail.GALLERY.pageName);
        return PageViewDetail.GALLERY.path;
    }
}
