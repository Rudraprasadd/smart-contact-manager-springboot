package com.scm.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.forms.UserForm;




@Controller
public class PageController {

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("Home page handler");
        model.addAttribute("name","Rudraprasad Satapathy");
        return "home";
    }

    //about

    @RequestMapping("/about")
    public String aboutPage(Model model){
        model.addAttribute("isLogin", true);
        System.out.println("About page loading");
        return "About";
    }

    //Services

    @RequestMapping("/services")
    public String servicesPage(){
        System.out.println("services page   loading");
        return "services";
    }

    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }
    
    @GetMapping("/login")
    public String login() {
        return new String("login");
    }
    
    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm=new UserForm();
        // userForm.setName("Rudra");
        // userForm.setAbout("This is about");
        model.addAttribute("userForm",userForm);
        return "register";
    }
    
    //reg process
    @RequestMapping(value="/do-register" ,method=RequestMethod.POST)
    public String processRegister(@ModelAttribute UserForm userForm){
        System.out.println("Processing");
        //fetch data
        //user form
        System.out.println(userForm);
        //validate
        //save to db

        //userservice
        //success message
        //redirect to Login
        return "redirect:/register";
    }

}
