package com.kurs.shop.controllers;

import com.kurs.shop.models.Item;
import com.kurs.shop.models.User;
import com.kurs.shop.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ItemController
{
    @Autowired // подключаем репозиторий
    private ItemRepository itemRepository;

    @GetMapping("/item/add")
    public String add()
    {
        return "add-item";
    }

    @GetMapping("/item/{id}") // укаываем с динамическом параметром, название любое
    public String showitem(@PathVariable(value = "id") long id, Model model) // но тут название надо такое же
    {
        Item item = itemRepository.findById(id).orElse(new Item()); // находим по айди, если не находим создаем пустой, так надо
        model.addAttribute("item", item); // передаем внутрь шаблона item
        return "show-item";
    }

    @GetMapping("/item/{id}/update") // укаываем с динамическом параметром, название любое
    public String update(@PathVariable(value = "id") long id, Model model) // но тут название надо такое же
    {
        Item item = itemRepository.findById(id).orElse(new Item()); // находим по айди, если не находим создаем пустой, так надо
        model.addAttribute("item", item); // передаем внутрь шаблона item
        return "item-update";
    }

    @PostMapping("/item/add")
    public String store(@AuthenticationPrincipal User user,
            @RequestParam String title, @RequestParam String image,
                        @RequestParam String price, @RequestParam String info)
    {
        Item item = new Item(title, info, image, Short.parseShort(price), user); // передаем данные в конструктор который создали, что возвращает тоже самое
        itemRepository.save(item); // сохранение объекта
        return "redirect:/"; // после сохранения данных будем переходить на главную страницу
    }

    @PostMapping("/item/{id}/update")
    public String updateItem(@PathVariable(value = "id") long id, @RequestParam String title, @RequestParam String image,
                        @RequestParam String price, @RequestParam String info)
    {
        Item item = itemRepository.findById(id).orElse(new Item()); // находим по айди, если не находим создаем пустой, так надо
        item.setTitle(title);
        item.setImage(image);
        item.setPrice(Short.parseShort(price));
        item.setInfo(info);
        itemRepository.save(item); // сохранение позволяет и сохранить и сохранить отредактированную запись
        return "redirect:/item/" + id;
    }

    @PostMapping("/item/{id}/delete")
    public String deleteItem(@PathVariable(value = "id") long id)
    {
        Item item = itemRepository.findById(id).orElse(new Item()); // находим по айди, если не находим создаем пустой, так надо
        itemRepository.delete(item);
        return "redirect:/";
    }

}
