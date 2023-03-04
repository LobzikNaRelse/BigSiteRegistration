package com.kurs.shop.controllers;

import com.kurs.shop.models.Item;
import com.kurs.shop.models.Role;
import com.kurs.shop.models.User;
import com.kurs.shop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.constant.Constable;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Controller
public class UserController
{
    String a = "";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

    @GetMapping("/reg")
    public String reg(@RequestParam(name = "error", defaultValue = "", required = false) String error, Model model)
    {
        if (error.equals("username"))
        {
            model.addAttribute("error", "Такой логин уже занят");
        }
        return "reg";
    }

    @PostMapping("/reg")
    public String addUser(@AuthenticationPrincipal  Set<Item> item, @RequestParam String username, @RequestParam String password,
                          @RequestParam String email)
    {
        if (userRepository.findByUsername(username) != null)
        {
            return "redirect:/reg?error=username";
        }
            password = passwordEncoder.encode(password);
            User user = new User(username, password, email, true, Collections.singleton(Role.USER), item);
            userRepository.save(user);
            return "redirect:/login";
    }

    @GetMapping("/user")
    public String user(Principal principal, Model model)
    {
        User user = userRepository.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("a", a);
        a = ""; // тупо, но хитро)))) А главное работает))))
        return "user";
    }

    @PostMapping("/user")
    public String userUpdate(Principal principal, @RequestParam String username, @RequestParam String password,
                             @RequestParam String email, @RequestParam Set<Role> roles)
    {
        // я понял что нужно использовать <form:form method="post" modelAttribute="userForm"> & <form:input></form:input> & <spring:bind path="username">
        //@ModelAttribute("userForm") User userForm
        // но у меня не отображаются поля для ввода в таком виде(импорт html я делал), я пока не нашел этому решение, решил сделать по старинке
        // но после сохранения нового логина не работал Principal из-за смены данных, пришлось извращаться
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        password = passwordEncoder.encode(password);
        User user = userRepository.findByUsername(principal.getName());
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoles(roles);
        userRepository.save(user);


        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authRequest);
        a = "Успешно сохранено"; // тут я решил сделать максимально тупо в лоб))) для отображения
        return "redirect:/user";
    }


}
