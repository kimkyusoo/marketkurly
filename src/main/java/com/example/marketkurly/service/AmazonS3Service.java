package com.example.marketkurly.service;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;


@Slf4j
@RequiredArgsConstructor
@Service
public class AmazonS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    /* 이미지 업로드 기능 */
    @Transactional
    public String upload(MultipartFile multipartFile, String filename) throws IOException {
        amazonS3Client.putObject(new PutObjectRequest(bucket, filename, multipartFile.getInputStream(), null)
                .withCannedAcl(CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, filename).toString();

    }

    /* 이미지 삭제 기능 */
    @Transactional
    public void deleteFile(String filename) {
        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("ap-northeast-2").build();
        s3.deleteObject("devcat-bucket", filename);
    }


    /* 고유한 파일 이름 생성 */
    public String createFilename(MultipartFile multipartFile) {
        return UUID.randomUUID() + multipartFile.getOriginalFilename();
    }

}
