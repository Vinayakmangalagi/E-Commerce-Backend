package com.cartbackend.Shoppingcart.Data;

import com.cartbackend.Shoppingcart.model.User;
import com.cartbackend.Shoppingcart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
   @Autowired
   private UserRepository userRepository;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExits();
    }

    private void createDefaultUserIfNotExits() {
        for (int i=1;i<5;i++){
            String defaultEmail = "user"+i+"@email.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The user");
            user.setLastName("user"+i);
            user.setEmail(defaultEmail);
            user.setPassword("12345");
            userRepository.save(user);
            System.out.println("Default user"+i+"created successfully");
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
