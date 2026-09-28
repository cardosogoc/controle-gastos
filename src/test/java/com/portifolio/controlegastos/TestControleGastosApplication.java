package com.portifolio.controlegastos;

import org.springframework.boot.SpringApplication;

public class TestControleGastosApplication {

	public static void main(String[] args) {
		SpringApplication.from(StartUp::main).with(TestcontainersConfiguration.class).run(args);
	}

}
