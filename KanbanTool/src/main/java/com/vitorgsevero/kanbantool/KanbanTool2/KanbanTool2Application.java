package com.vitorgsevero.kanbantool.KanbanTool2;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class KanbanTool2Application {
	
    @PostConstruct
    void started() {
      TimeZone.setDefault(TimeZone.getTimeZone("TimeZone"));
    }
    
	public static void main(String[] args) {
		SpringApplication.run(KanbanTool2Application.class, args);

	}

}
