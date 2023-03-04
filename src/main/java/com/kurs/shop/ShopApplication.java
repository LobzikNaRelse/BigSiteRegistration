package com.kurs.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShopApplication
{

	public static void main(String[] args)
	{
		SpringApplication.run(ShopApplication.class, args);
		// в resources -> application.properties меняется порт для сайта
		// вписываем server.port = 8000
	}

}
