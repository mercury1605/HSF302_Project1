package nguyen.example.hsf302_1.controller;

import jakarta.servlet.http.HttpSession;
import nguyen.example.hsf302_1.entity.UserAccount;
import nguyen.example.hsf302_1.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthController {

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping({"/", "/login"})
    public String showLoginPage() {
        return "login";
    }


    @PostMapping("/login")
    public String handleLogin(Model model,
                              HttpSession session,
                              @RequestParam("username") String username,
                              @RequestParam("password") String password) {

        UserAccount acc = userAccountService.login(username, password);
        if (acc == null) {
            model.addAttribute("errMsg", "username or password is invalid!");
            return "login";
        }

        if (acc.isGuest()) {
            model.addAttribute("errMsg", "You are unauthorized to access this system");
            return "login";
        }

        session.setAttribute("user", acc);
        return "redirect:/students";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa session
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/login";
    }
}
