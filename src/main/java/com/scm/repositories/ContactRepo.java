package com.scm.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm.entities.Contact;
import com.scm.entities.User;



@Repository
public interface ContactRepo extends JpaRepository<Contact,String>{

    Page<Contact> findByUser(User user,Pageable page);

    @Query("SELECT c FROM Contact c WHERE c.user.id =:userid")
    List<Contact>findByUserId(@Param("userid")String userid);

    Page<Contact> findByUserAndNameContaining(User user, String namekeyword, Pageable pageable);

    Page<Contact> findByUserAndEmailContaining(User user, String emailkeyword, Pageable pageable);
    
    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phonekeyword, Pageable pageable);


}
