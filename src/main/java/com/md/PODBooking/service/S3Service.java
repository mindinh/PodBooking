package com.md.PODBooking.service;

import com.md.PODBooking.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.IOException;

@Service
public class S3Service {

    private final S3Client s3Client;
    private final String bucketName;
    private final String region;

    public S3Service() {
        this.bucketName = System.getenv("AWS_S3_BUCKET");
        this.region = System.getenv("AWS_REGION");

        this.s3Client = S3Client.builder()
                .region(Region.of(System.getenv("AWS_REGION")))
                .credentialsProvider(EnvironmentVariableCredentialsProvider.create())
                .build();
    }

    public String formatKey(String locationName, String spaceName, String fileName) {
        String folder = locationName.replace(" ", "-") + "/" + spaceName.replace(" ", "-");

        return "gallery/" + folder + "/" + fileName;
    }

    public String uploadFile(String locationName, String spaceName, MultipartFile file) throws IOException {
        String folder = locationName.replace(" ", "-") + "/" + spaceName.replace(" ", "-");
        String key = "gallery/" + folder + "/" + file.getOriginalFilename();

        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + key;
    }

    public boolean doesObjectExist(String key) {
        try {
            HeadObjectRequest request = HeadObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.headObject(request);
            return true;
        } catch (NoSuchKeyException e) {
            return false;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Location Space Image", "Space Image", key);
        }
    }

    public boolean deleteFile(String key) {
        if (!doesObjectExist(key)) {
            return false;
        }

        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.deleteObject(request);
            return true;

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete object: " + key, e);
        }
    }
}
