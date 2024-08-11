package com.scm.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

import jakarta.persistence.EnumType;
import jakarta.persistence.FetchType;


@Entity(name="user")
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    @Id
    private String userid;
    @Column(name="user_name",nullable=false)

    private String name;
    @Column(unique=true,nullable=false)
    private String email;
    private String password;
    private String about;
    @Column(length=1000)
    private String profilePic;
    private String phoneNumber;

    //info
    private boolean enabled = false;
    private boolean emailVerified=false;
    private boolean phoneVerified=false;


    @Enumerated(value = EnumType.STRING)
    //self,google,git
    private Providers provider=Providers.SELF;
    private String providerUserId;


    //one to many
    @OneToMany(mappedBy="user",cascade=CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval=true)
    private List<Contact> contacts=new ArrayList<>();

    
}
