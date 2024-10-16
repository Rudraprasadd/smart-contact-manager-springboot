package com.scm.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.helpers.Helper;
import com.scm.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger=LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    
    //user dashbord
    @RequestMapping(value="/dashboard")
    public String userDashboard() {
        System.out.println("User Dashboard");
        return "user/dashboard";
    }

    //User profile page
    @RequestMapping(value="/profile")
    public String userProfile(Authentication authentication) {
        // String name=principal.getName();

        String username=Helper.getEmailOfLoggedinUser(authentication);



        logger.info("User logged in {}",username);

        // User user=userService.getUserByEmail(username);
        com.scm.entities.User user=userService.getUserByEmail(username);
        //database se data fetch user to database

        System.out.println(user.getName());
        System.out.println(user.getEmail());
        return "user/profile";
    }
    

    //User contact page 

    //user view page 

    //user edit contact

    //user delete contact

    //user search contact


}
