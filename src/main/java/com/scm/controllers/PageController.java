package com.scm.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter



@Controller
public class PageController {

    @Autowired
    private UserService userService;

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
    public String processRegister(@ModelAttribute UserForm userForm,HttpSession session){
        System.out.println("Processing");
        //fetch data
        //user form
        System.out.println(userForm);
        // validate
        // save to db
        // User user=User.builder()
        // .name(userForm.getName())
        // .email(userForm.getEmail())
        // .password(userForm.getPassword())
        // .about(userForm.getAbout())
        // .phoneNumber(userForm.getPhoneNumber())
        // .build();

        User user =new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("profilePic");

        User SavedUser=userService.saveUser(user);
        System.out.println("User saved");
        //userservice
        //success message
        Message message=Message.builder().content("Registration Sucessful").type(MessageType.yellow).build();
        session.setAttribute("message", message);
        //redirect to Login
        return "redirect:/register";
    }

}
