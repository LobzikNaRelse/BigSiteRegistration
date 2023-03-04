package com.kurs.shop.repo;

import com.kurs.shop.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> // джпрепоз больше подходит для регистрации
{
    // для работы с таблицей всегда нужна модель и репозирорий
    User findByUsername(String username); // такой синтаксис, джава понимает что мы хотим
}
