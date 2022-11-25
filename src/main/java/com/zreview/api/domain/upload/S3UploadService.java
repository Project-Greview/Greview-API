package com.zreview.api.domain.upload;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AbstractAmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.zreview.api.domain.member.dao.MemberRepository;
import com.zreview.api.domain.member.model.Member;
import com.zreview.api.domain.review.dao.ReviewRepository;
import com.zreview.api.domain.review.model.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.amazonaws.services.s3.AmazonS3;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.URL;
import java.util.*;

@RequiredArgsConstructor
@Service
public class S3UploadService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final FileRepository fileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazons3;

    public List<String> upload(List<MultipartFile> multipartFiles) throws IOException {

        //s3 파일이름 정보를 담을 비어있는 리스트
        List<String> filenameList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles){
            // 파일 이름 중복되지 않기 위해서 UUID로 생성한 랜덤 값과 파일 이름을 연결하여 S3에 업로드
            String s3FileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

            // Spring Server에서 S3로 파일을 업로드해야 하는데,
            // 이 때 파일의 사이즈를 ContentLength로 S3에 알려주기 위해서 ObjectMetadata를 사용
            ObjectMetadata objMeta = new ObjectMetadata();
            objMeta.setContentLength(multipartFile.getInputStream().available());

            // S3 API 메소드인 putObject를 이용하여 파일 Stream을 열어서 S3에 파일을 업로드
            amazons3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);

            // getUrl 메소드를 통해서 S3에 업로드된 사진 URL을 가져오는 방식
            //String url= amazons3.getUrl(bucket, s3FileName).toString();
            filenameList.add(s3FileName);
        }

        return filenameList;

    }

    public void saveReviewUrl(List<String> urls,Long reviewId){
        for (String url:urls){
            File file =new File(url,reviewId);
            fileRepository.save(file);
        }

    }
    public String getPreSignedUrl(String bucket, String prefix, String fileName) {
        if (!prefix.equals("")) {
            fileName = prefix + "/" + fileName;
        }
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(bucket, fileName);
        URL url = amazons3.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String bucket, String fileName) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucket, fileName)
                        .withMethod(HttpMethod.GET)
                        .withExpiration(getPreSignedUrlExpiration());
        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());
        return generatePresignedUrlRequest;
    }

    private Date getPreSignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    public List<String> getFile(Long id) {
        List<File> files=fileRepository.findAllByreviewId(id);
        List<String> urls=new ArrayList<>();
        List<String> presigned_urls=new ArrayList<>();
        for (File file:files){
            urls.add(file.getUrl());
        }
        for (String url:urls){
            String val=getPreSignedUrl(bucket,"",url);
            presigned_urls.add(val);
            System.out.println(val);
        }

        return presigned_urls;
    }
}
