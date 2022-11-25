package com.zreview.api.domain.upload;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@RequiredArgsConstructor
@Tag(name = "upload", description = "파일 업로드 API")
public class FileController {
     private final S3UploadService s3UploadService;

     @Operation(summary = "파일 업로드", tags = { "File Controller" })
     @PostMapping("/file/{reviewId}")
     public ResponseEntity<?> uploadFile(@RequestParam("images") List<MultipartFile> multipartFiles, @PathVariable Long reviewId) throws IOException {
         List<String> urls = s3UploadService.upload(multipartFiles);
         s3UploadService.saveReviewUrl(urls,reviewId);

         return ResponseEntity.ok(urls);
     }

    @Operation(summary = "파일 s3 presigned url 반환", tags = { "File Controller" })
    @GetMapping("/file/{reviewId}")
    public ResponseEntity<?> getFile(@PathVariable Long reviewId) throws IOException {
        List<String> urls=s3UploadService.getFile(reviewId);

        return ResponseEntity.ok(urls);
    }
}
