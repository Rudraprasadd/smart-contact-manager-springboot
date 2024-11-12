package com.scm.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
@RequestMapping("/user/contacts")
public class ContactController {
    private Logger logger=org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;


    @RequestMapping("/add")
    //add contact page:handler
    public String addContactView(Model model){
        ContactForm contactform= new ContactForm();
        contactform.setFavorite(true);
        model.addAttribute("contactForm",contactform);
        return "user/add_contact";
    }


    @RequestMapping(value="/add",method=RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result,Authentication authentication,
                        HttpSession session){

        //validate foem
        if(result.hasErrors()){
            session.setAttribute("message", Message.builder()
            .content("Please correct the following errors")
            .type(MessageType.red)
            .build());
            return "user/add_contact";
        }

        String username=Helper.getEmailOfLoggedinUser(authentication);


       User user = userService.getUserByEmail(username);


      



        Contact contact=new Contact();


        contact.setName(contactForm.getName());
        contact.setFavorite(contactForm.isFavorite());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setUser(user);
        contact.setLinkedinLink(contactForm.getLinkedinLink());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        if (contactForm.getContactimage() != null && !contactForm.getContactimage().isEmpty()) {
            String filename = UUID.randomUUID().toString();
            String fileURL = imageService.uploadImage(contactForm.getContactimage(), filename);
            contact.setPicture(fileURL);
            contact.setCloudinaryImagePublicId(filename);
        }
        contactService.save(contact);


        //process the form data
        System.out.println(
            contactForm
        );


        session.setAttribute("message",Message.builder()
        .content("You have added a new contact Sucessfully")
        .type(MessageType.green)
        .build());

        return "redirect:/user/contacts/add";
        
    }


    @RequestMapping
    public String viewContacts(

        @RequestParam(value="page",defaultValue="0") int page,
        @RequestParam(value="size",defaultValue=AppConstants.PAGE_SIZE+"") int size,
        @RequestParam(value="sortBy",defaultValue="name") String sortBy,
        @RequestParam(value="direction",defaultValue="asc") String direction,

        Model model,Authentication authentication
        
        ){

        String username=Helper.getEmailOfLoggedinUser(authentication);

        User user=userService.getUserByEmail(username);

        Page<Contact> pageContacts=contactService.getByUser(user,page,size,sortBy,direction);


        

        model.addAttribute("pageContacts",pageContacts);

        model.addAttribute("pageSize",AppConstants.PAGE_SIZE);

        model.addAttribute("contactSearchForm", new ContactSearchForm());
        
        return "user/contacts";
    }


    //search handler(){
        @RequestMapping("/search")
    public String searchHandler(
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "size", defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {
        logger.info("field {} keyword {}", contactSearchForm.getField(), contactSearchForm.getValue());
        var user = userService.getUserByEmail(Helper.getEmailOfLoggedinUser(authentication));
        Page<Contact> pageContacts = null;
        if (contactSearchForm.getField().equalsIgnoreCase("name")) {
            pageContacts = contactService.searchByName(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("email")) {
            pageContacts = contactService.searchByEmail(contactSearchForm.getValue(), size, page, sortBy, direction,
                    user);
        } else if (contactSearchForm.getField().equalsIgnoreCase("phone")) {
            pageContacts = contactService.searchByPhoneNumber(contactSearchForm.getValue(), size, page, sortBy,
                    direction, user);
        }
        logger.info("pageContact {}", pageContacts);
        model.addAttribute("contactSearchForm", contactSearchForm);
        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        return "user/search";
    }







    // detete contact
    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable("contactId") String contactId,
            HttpSession session) {
        contactService.delete(contactId);
        logger.info("contactId {} deleted", contactId);
        session.setAttribute("message",
                Message.builder()
                        .content("Contact is Deleted successfully !! ")
                        .type(MessageType.green)
                        .build()
        );
        return "redirect:/user/contacts";
    }
    // update contact form view
    @GetMapping("/view/{contactId}")
    public String updateContactFormView(
            @PathVariable("contactId") String contactId,
            Model model) {
        var contact = contactService.getByid(contactId);
        ContactForm contactForm = new ContactForm();
        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getAddress());
        contactForm.setDescription(contact.getDescription());
        contactForm.setFavorite(contact.isFavorite());
        contactForm.setWebsiteLink(contact.getWebsiteLink());
        contactForm.setLinkedinLink(contact.getLinkedinLink());
        contactForm.setPicture(contact.getPicture());
        ;
        model.addAttribute("contactForm", contactForm);
        model.addAttribute("contactId", contactId);
        return "user/update_contact_view";
    }
    @RequestMapping(value = "/update/{contactId}", method = RequestMethod.POST)
    public String updateContact(@PathVariable("contactId") String contactId,
            @Valid @ModelAttribute ContactForm contactForm,
            BindingResult bindingResult,
            Model model) {
        // update the contact
        if (bindingResult.hasErrors()) {
            return "user/update_contact_view";
        }
        var con = contactService.getByid(contactId);
        con.setId(contactId);
        con.setName(contactForm.getName());
        con.setEmail(contactForm.getEmail());
        con.setPhoneNumber(contactForm.getPhoneNumber());
        con.setAddress(contactForm.getAddress());
        con.setDescription(contactForm.getDescription());
        con.setFavorite(contactForm.isFavorite());
        con.setWebsiteLink(contactForm.getWebsiteLink());
        con.setLinkedinLink(contactForm.getLinkedinLink());
        // process image:
        if (contactForm.getContactimage() != null && !contactForm.getContactimage().isEmpty()) {
            logger.info("file is not empty");
            String fileName = UUID.randomUUID().toString();
            String imageUrl = imageService.uploadImage(contactForm.getContactimage(), fileName);
            con.setCloudinaryImagePublicId(fileName);
            con.setPicture(imageUrl);
            contactForm.setPicture(imageUrl);
        } else {
            logger.info("file is empty");
        }
        var updateCon = contactService.update(con);
        logger.info("updated contact {}", updateCon);
        model.addAttribute("message", Message.builder().content("Contact Updated !!").type(MessageType.green).build());
        return "redirect:/user/contacts/view/" + contactId;
    }

}
