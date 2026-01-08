package com.interviewmate.resumeservice.util;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class FileUploader {

   private final Cloudinary cloudinary;


    public UploadResult uploadFile(MultipartFile file) throws IOException {
         if (file.isEmpty()) {
        throw new IllegalArgumentException("File is empty");
    }

    String cleanName = Paths.get(file.getOriginalFilename())
                            .getFileName().toString();

    Map<?, ?> uploadResult = cloudinary.uploader().upload(
            file.getBytes(),
            ObjectUtils.asMap(
                    "resource_type", "raw",
                    "folder", "resumes",
                    "public_id", cleanName,
                    "use_filename", true,
                    "unique_filename", true,
                    "overwrite", true,
                    "invalidate", true
            )
    );

    String publicId = (String) uploadResult.get("public_id");
    String url = (String) uploadResult.get("secure_url"); //  trusted URL

    return new UploadResult(publicId, url);
    }

    public void deleteFile(String publicId) throws IOException {
        cloudinary.uploader().destroy(
                publicId,
                ObjectUtils.asMap("resource_type", "raw")
        );
    }


    public String uploadPdf(byte[] pdfBytes, String fileName){
        try {
            Map<?, ?> uploadResult = cloudinary.uploader().upload(
                    pdfBytes,
                    ObjectUtils.asMap(
                            "resource_type", "raw",
                            "folder", "resumes",
                            "public_id", fileName,
                            "use_filename", true,
                            "unique_filename", true,
                            "overwrite", true,
                            "invalidate", true
                    )
            );

            return (String) uploadResult.get("secure_url");
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload PDF", e);
        }
    }


    public record UploadResult(String publicId, String url) {}
}
