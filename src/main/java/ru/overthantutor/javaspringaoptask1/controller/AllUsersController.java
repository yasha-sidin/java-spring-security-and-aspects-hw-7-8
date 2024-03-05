package ru.overthantutor.javaspringaoptask1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.overthantutor.javaspringaoptask1.aspect.TrackUserAction;
import ru.overthantutor.javaspringaoptask1.domain.AuthUserDetails;
import ru.overthantutor.javaspringaoptask1.service.JpaUserDetailsManager;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class AllUsersController {
    private final JpaUserDetailsManager jpaUserDetailsManager;

    @TrackUserAction
    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {
        String username = principal.getName();
        AuthUserDetails userDetails = (AuthUserDetails) jpaUserDetailsManager.loadUserByUsername(username);
        model.addAttribute("user", userDetails);
        return "profile";
    }
}
