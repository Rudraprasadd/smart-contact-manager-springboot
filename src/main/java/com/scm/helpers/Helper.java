package com.scm.helpers;



import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;

public class Helper {

    public static String getEmailOfLoggedinUser(Authentication authentication){

        // AuthenticationPrincipal Principal=(AuthenticationPrincipal)authentication.getPrincipal();

        // if loggedin by email and password then how to get the email 
        if(authentication instanceof OAuth2AuthenticationToken){

            var aOAuth2AuthenticationToken=(OAuth2AuthenticationToken)authentication;
            var clientid=aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();

            if(clientid.equalsIgnoreCase("google")){
                //sign with google
                System.out.println("Getting email from google");
            }
            else if(clientid.equalsIgnoreCase("github")){
                //sign with github
                System.out.println("Getting email from github");
            }
            
            return "";
        
        //sign with facebook


        }else{
            System.out.println("Getting data from local database");
            return authentication.getName();
        }
        
    } 
}
