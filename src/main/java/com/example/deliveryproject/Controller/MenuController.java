package com.example.deliveryproject.Controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 변경: RestController -> Controller
public class MenuController {

    @GetMapping("/home/menus")
    public String getMenuPage() {
        return "menus"; // templates/menus.html 반환
    }
}
