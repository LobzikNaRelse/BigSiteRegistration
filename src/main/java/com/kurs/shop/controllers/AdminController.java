package com.kurs.shop.controllers;

import com.kurs.shop.models.Item;
import com.kurs.shop.models.Role;
import com.kurs.shop.models.User;
import com.kurs.shop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

@Controller
public class AdminController
{

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/panel-admin")
    public String paneladmin(Principal principal, Model model)
    {
        User user = userRepository.findByUsername(principal.getName());
        Set<Role> admin = Collections.singleton(Role.ADMIN);
        Set<Role> role = user.getRoles();
        if(!role.equals(admin))
        {
            return "admindenied";
        }
        else
        {
            // в классе юзер соединяются таблицы
            Iterable<User> users = userRepository.findAll();
            model.addAttribute("users", users);
            return "panel-admin";
        }
    }

    @GetMapping("/admin-user/user-{id}")
    public String userItems(@PathVariable(value = "id") long userId, Model model)
    {
        User user = userRepository.findById(userId).orElse(new User());
        model.addAttribute("items", user.getItems());
        return "admin-user";
    }
}
