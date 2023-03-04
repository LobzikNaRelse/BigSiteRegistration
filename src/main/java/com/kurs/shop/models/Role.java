package com.kurs.shop.models;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority
{
    USER, ADMIN, REDACTOR;
    // разный функционал от роли
    // для работы с таблицей всегда нужна модель и репозирорий

    @Override
    public String getAuthority()
    {
        return name();
    }
}
