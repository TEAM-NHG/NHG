package com.ssafy.common.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

public class ImageUploader {

	public String upload(MultipartFile[] files, String uploadPath) {
        StringBuilder savedFilePaths = new StringBuilder();

        try {
            if (files != null && files.length > 0) {
                String today = new SimpleDateFormat("yyMMdd").format(new Date());
                String saveFolder = uploadPath + File.separator + today;
                File folder = new File(saveFolder);

                // 폴더가 없으면 생성
                if (!folder.exists()) {
                    folder.mkdirs();
                }

                // 파일 처리
                for (MultipartFile file : files) {
                    String originalFileName = file.getOriginalFilename();
                    if (originalFileName != null && !originalFileName.isEmpty()) {
                        // 확장자 추출
                        String extension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                        String saveFileName = UUID.randomUUID().toString() + extension;

                        // 파일 저장
                        File destinationFile = new File(folder, saveFileName);
                        file.transferTo(destinationFile);

                        // 경로 저장 (여러 개의 파일 경로를 반환하려면, 경로를 저장)
                        if (savedFilePaths.length() > 0) {
                            savedFilePaths.append(", ");
                        }
                        savedFilePaths.append(saveFolder + File.separator + saveFileName);
                    }
                }

                // 파일 경로 반환
                return savedFilePaths.toString();
            } 
        } catch (IOException e) {
                e.printStackTrace(); // 로그 시스템을 사용하는 것이 좋습니다
        }
        return null;
    }
}