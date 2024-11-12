package com.scm.validators;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<ValidFile,MultipartFile>{

    

    private static final long MAX_FILE_SIZE =1024 * 1024 *5;//5MB

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        

        if(file==null|| file.isEmpty()){

           
            return true;
        }

        if(file.getSize()>MAX_FILE_SIZE){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("file Size should be less than 2MB").addConstraintViolation();
            return false;
        }

        //resolution
        // try {
        //     BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

        //     if()

        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        



        return true;
    }

}
