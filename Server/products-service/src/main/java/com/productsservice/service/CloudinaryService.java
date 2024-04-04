package com.productsservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class CloudinaryService {

    Cloudinary cloudinary;
    private Map<String, String> valuesMap = new HashMap<>();


    @Value("${name}")
    private String cloudinary_name;
    @Value("${key}")
    private String cloudinary_apiKey;
    @Value("${secret}")
    private String cloudinary_apiSecret;

    public CloudinaryService(@Value("${name}") String cloudinary_name,
                             @Value("${key}") String cloudinary_apiKey,
                             @Value("${secret}") String cloudinary_apiSecret) {
        this.cloudinary_name = cloudinary_name;
        this.cloudinary_apiKey = cloudinary_apiKey;
        this.cloudinary_apiSecret = cloudinary_apiSecret;
        valuesMap.put("cloud_name", cloudinary_name);
        valuesMap.put("api_key", cloudinary_apiKey);
        valuesMap.put("api_secret", cloudinary_apiSecret);
        cloudinary = new Cloudinary(valuesMap);
    }

    public Map upload(MultipartFile multipartFile) throws IOException {
        File file = convert(multipartFile);
        Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        file.delete();
        return result;
    }

    public Map delete(String id) throws IOException {
        Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

}
