package com.scm.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    String uploadImage(MultipartFile contactimage,String filename);


    String getUrlFromPublicId(String publicId);



}
