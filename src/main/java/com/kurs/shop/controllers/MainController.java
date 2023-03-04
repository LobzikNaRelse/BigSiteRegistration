package com.kurs.shop.controllers;

import com.kurs.shop.models.Item;
import com.kurs.shop.repo.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller // теперь может отслеживать переход по url адресу
public class MainController
{

    @Autowired // подключаем репозиторий
    private ItemRepository itemRepository;

    @GetMapping // для отслеживания url, главной страницы
    public String index(Model model) // хотим получать данные из таблицы, вписываем model
    {
        Iterable<Item> items = itemRepository.findAll(); // класс в который можно вписать записи, в список items
        model.addAttribute("items", items); // присваеваем в модель
        return "index"; // шаблон который будем отображать, шаблоны только в resources->templates (не переименовывать)
    }

    @GetMapping("/about-us") // отслеживание url /about-us
    public String about(@RequestParam(name = "userName", required = false, defaultValue = "World") String userName, Model model) // отслеживание параметра из url адреса, false если параметра нету выполнится корректно, дефаулт если нету
    {
        model.addAttribute("name", "About"); // первое название, второе значение(строка или число или массив)
        // <p th:text="${name}"></p> так выводится переменная
        model.addAttribute("name1", userName);

        return "about";
    }

}
