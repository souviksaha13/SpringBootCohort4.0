package com.springbootCohort.Module1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Module1Application implements CommandLineRunner {

//    @Autowired
    // but @Autowired is a field Dependency injection and this should be avoided.
    // Instead, one should use Constructor dependency injection, where they can make the variable final
    // we need to make the variable immutable.

    //  ------------------------------   ------------------------------

//    final NotificationService notification;
//
//    public Module1Application(NotificationService notification) {
//        this.notification = notification;
//    }

    //  ------------------------------   ------------------------------
    //  What if we want both the notification services?

    @Autowired
    Map<String, NotificationService> allServices = new HashMap<>();

	public static void main(String[] args) {
		SpringApplication.run(Module1Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {
//        notification.send("HI hello, how are you?");
        for(NotificationService notificationService : allServices.values()) {
            notificationService.send("Hi Hello, how are you?");
        }
    }
}
