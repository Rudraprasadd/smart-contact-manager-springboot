package com.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.scm.entities.Contact;
import com.scm.entities.User;

public interface ContactService {


    //Save contacts
    Contact save(Contact contact);
    
    //update contact
    Contact update(Contact contact);

    //get Contacts
    List<Contact> getAll();

    //Contact by id
    Contact getByid(String id);

    //delete contact
    void delete(String id);

    //Search COntact
    Page<Contact> searchByName(String nameKeyword, int size, int page, String sortBy, String order, User user);



    Page<Contact> searchByEmail(String emailKeyword, int size, int page, String sortBy, String order, User user);

    
    Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int size, int page, String sortBy, String order,
            User user);

    //get user by userid
    List<Contact> getByUserid(String userid);


    Page<Contact> getByUser(User user,int page,int size,String sortField,String sortDirection);
}
