package com.scm.services.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helpers.AppConstants;
import com.scm.services.ImageService;

@Service
public class ImageServiceimpl implements ImageService{


    private Cloudinary cloudinary;

    



    public ImageServiceimpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }





    @Override
    public String uploadImage(MultipartFile contactimage,String filename) {
        //code jo img upload kare server me or cloud

    


    try{
        byte[] data=new byte[contactimage.getInputStream().available()];

        contactimage.getInputStream().read(data);
        cloudinary.uploader().upload(data,ObjectUtils.asMap(
            "public_id",filename
        ));

        return this.getUrlFromPublicId(filename);

    }
    catch (IOException e){
        e.printStackTrace();
        return null;
    }



        
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary
        .url()
        .transformation(
            new Transformation<>()
            .width(AppConstants.CONTACT_IMAGE_WIDTH)
            .height(AppConstants.CONTACT_IMAGE_HEIGHT)
            .crop(AppConstants.CONTACT_IMAGE_CROP)
        )
        .generate(publicId);

    }

}
