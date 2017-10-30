package pl.SpringStore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.SpringStore.models.forms.LoginForm;
import pl.SpringStore.models.repositories.UserRepository;

import javax.validation.Valid;

/**
 * Created by arabk on 26.10.2017.
 */
@Controller
@SessionAttributes({"sessionName","sessionIsLogged"})
public class LoginController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/signin")
    public String singnInGet(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "signin";
    }

    @PostMapping("/signin")
    public String signInPost(@ModelAttribute("loginForm") @Valid LoginForm loginForm, BindingResult result, Model model){
        if (result.hasErrors()) {
            return "signin";
        }

        if(userRepository.findByLoginAndPassword(loginForm.getLogin(), loginForm.getPassword()).size() > 0) {
            System.out.println("dobry login");
            String sessionName = (userRepository.findByLoginAndPassword(loginForm.getLogin(), loginForm.getPassword()).get(0)).getName();
            model.addAttribute("sessionName",sessionName);
            model.addAttribute("sessionIsLogged", true);
            return "redirect:/";
        } else {
            System.out.println("nie ma takiego logina");
            return "signin";
        }

    }


}