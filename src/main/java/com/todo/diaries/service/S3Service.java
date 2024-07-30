package com.todo.diaries.service;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

    private final AmazonS3 s3Client;

    @Value("${cloud.aws.s3.bucket.url}")
    private String bucketUrl;

    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    private static final Logger logger = Logger.getLogger(S3Service.class.getName());

    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public String uploadFile(MultipartFile file) {
        String fileUrl = "";
        try {
            String fileName = "diaries/" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
            fileUrl = bucketUrl + fileName;
            
            // S3에 파일 업로드
            logger.log(Level.INFO, "Uploading file to S3: " + fileName);
            s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null));
            
            logger.log(Level.INFO, "File uploaded to S3 successfully: " + fileUrl);
        } catch (AmazonServiceException e) {
            logger.log(Level.SEVERE, "Amazon service exception: " + e.getMessage());
        } catch (SdkClientException e) {
            logger.log(Level.SEVERE, "SDK client exception: " + e.getMessage());
        } catch (IOException e) {
            logger.log(Level.SEVERE, "IOException: " + e.getMessage());
        }
        return fileUrl;
    }
}
