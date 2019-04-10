package tvz.labos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/home")
    public String home(){
        return "homePage";
    }
    /*
    @GetMapping("/admin")
    public String adminPage()*/
}
