package com.crud.myFinalCrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




/* @SpringBootApplication аннотация содержит в себе 3:
@Configuration: указывает Spring, что данный класс является конфигурационным классом.
@EnableAutoConfiguration: включает автоконфигурацию Spring Boot.
@ComponentScan: указывает Spring, какие пакеты следует сканировать для поиска бинов.
 */



@SpringBootApplication
public class MyFinalCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyFinalCrudApplication.class, args);
	}

}
