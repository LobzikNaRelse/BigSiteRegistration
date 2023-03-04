package com.kurs.shop.repo;

import com.kurs.shop.models.Item;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<Item, Long> // наследование для редактирования и т.д. // тип данных указывается для Id // в наследовании уже многое есть
{

}
