package com.maher.utils;

import com.maher.enitites.AppUser;
import com.maher.services.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class FirstTimeInitializer implements CommandLineRunner {

    private final Log log = LogFactory.getLog(FirstTimeInitializer.class);

    private final UserService userService;

    public FirstTimeInitializer(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {

        if(userService.getAllUsers().isEmpty()) {
            log.info("No users found, creating default user");
            userService.save(new AppUser("user1","user123456", "user1@temp.com"));
            userService.save(new AppUser("user2","user234567", "user2@temp.com"));
        }

    }
}
