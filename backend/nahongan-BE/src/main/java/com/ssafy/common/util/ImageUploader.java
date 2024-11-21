package com.ssafy.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageUploader {
    @Value("${file.path.images}") // 실제 저장 경로 (예: "C:/NHG/upload/image")
    private String imageFilePath;

    @Value("${resource.images}") // 웹에서 접근할 수 있는 상대 경로 (예: "/upload/image")
    private String imageResourcePath;

    public String upload(List<MultipartFile> images, String uploadPath) {
        StringBuilder savedFilePaths = new StringBuilder();

        try {
            if (images != null && images.size() > 0) {
                // 오늘 날짜로 폴더 생성
                String saveFolderRelative = (uploadPath != null ? uploadPath : "default");

                // 절대 경로와 상대 경로를 구분
                String saveFolderAbsolute = imageFilePath + File.separator + saveFolderRelative;
                File folder = new File(saveFolderAbsolute);

                // 폴더 생성
                if (!folder.exists() && !folder.mkdirs()) {
                    throw new IOException("폴더 생성 실패: " + saveFolderAbsolute);
                }

                // 파일 처리
                for (MultipartFile file : images) {
                    String originalFileName = file.getOriginalFilename();
                    if (originalFileName != null && !originalFileName.isEmpty()) {
                        // 확장자 추출
                        String extension = "";
                        if (originalFileName.contains(".")) {
                            extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                        }
                        String saveFileName = UUID.randomUUID().toString() + extension;

                        // 파일 저장
                        File destinationFile = new File(folder, saveFileName);
                        file.transferTo(destinationFile);

                        // 상대 경로 저장
                        if (savedFilePaths.length() > 0) {
                            savedFilePaths.append(", ");
                        }
                        savedFilePaths.append(imageResourcePath + "/" + saveFolderRelative.replace(File.separator, "/") + "/" + saveFileName);
                    }
                }

                // 파일 상대 경로 반환
                return savedFilePaths.toString();
            }
        } catch (IOException e) {
            // 예외 처리
            e.printStackTrace(); // 로그 시스템 권장
        }
        return null;
    }
}


