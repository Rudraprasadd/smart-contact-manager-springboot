package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/user")
public class UserController {
    
    //user dashbord
    @RequestMapping(value="/dashboard")
    public String userDashboard() {
        return "user/dashboard";
    }

    //User profile page
    @RequestMapping(value="/profile")
    public String userProfile() {
        return "user/profile";
    }
    

    //User contact page 

    //user view page 

    //user edit contact

    //user delete contact

    //user search contact


}