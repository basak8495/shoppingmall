package com.project.shoppingmall.product_image;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.ReadChannel;
import com.google.cloud.storage.*;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/upload")
@Log4j2
public class UploadController {

    @Value("${com.project.upload.path}") // application.properties의 변수
    private String uploadPath;

    @Value("${spring.cloud.gcp.storage.bucket}") // application.properties의 변수
    private String bucketName;

    private String jsonFile = "stone-poetry-276214-1a8b8182d624.json";

    @PostMapping("/imageUpload")
    public ResponseEntity<List<UploadResultDto>> uploadFile(MultipartFile[] uploadFiles) {

        List<UploadResultDto> resultDTOList = new ArrayList<>();

        for (MultipartFile uploadFile: uploadFiles) {

            if(uploadFile.getContentType().startsWith("image") == false) {
                log.warn("this file is not image type");
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }

            String originalName = uploadFile.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            log.info("fileName: " + fileName);

            String uuid = UUID.randomUUID().toString();
            String blobFileName = uuid + "_" + fileName;

            try {
                // 구글 클라우드 스토리지 업로드 시작
                GoogleCredentials credentials = GoogleCredentials.fromStream(getClass().getResourceAsStream("/" + jsonFile));
                Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

                // GCS에 업로드할 Blob 생성
                BlobId blobId = BlobId.of(bucketName, blobFileName);
                BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();

                // MultipartFile의 내용을 바로 업로드
                storage.create(blobInfo, uploadFile.getBytes());

                System.out.println("File uploaded to Google Cloud Storage.");

                resultDTOList.add(new UploadResultDto(fileName, uuid, null));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return new ResponseEntity<>(resultDTOList, HttpStatus.OK);
    }

    private String makeFolder() {
        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String folderPath = str.replace("/", File.separator);

        File uploadPathFolder = new File(uploadPath, folderPath);

        if (uploadPathFolder.exists() == false) {
            uploadPathFolder.mkdirs();
        }
        return folderPath;
    }

    @GetMapping("/display")
    public ResponseEntity<byte[]> getFile(String fileName, String size) {

        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");

            log.info("srcFileName: " + srcFileName);

            // 구글 클라우드 스토리지에서 이미지 가져오기 시작
            GoogleCredentials credentials = GoogleCredentials.fromStream(getClass().getResourceAsStream("/" + jsonFile));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

            // GCS에서 Blob 가져오기
            Blob blob = storage.get(bucketName, srcFileName);

            byte[] fileData = null;
            if (blob != null) {
                ReadChannel reader = blob.reader();
                ByteBuffer bytes = ByteBuffer.allocate((int) Math.min(blob.getSize(), Integer.MAX_VALUE));
                reader.read(bytes);
                fileData = bytes.array();
                reader.close();
            } else {
                log.error("File not found in Google Cloud Storage.");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // 구글 클라우드 스토리지에서 이미지 가져오기 끝

            HttpHeaders header = new HttpHeaders();
            // MIME 타입 처리
            header.add("Content-Type", blob.getContentType());
            // 파일 데이터 처리
            result = new ResponseEntity<>(fileData, header, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return result;
    }

    @PostMapping("/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName) {
        String srcFileName = null;
        try {
            srcFileName = URLDecoder.decode(fileName,"UTF-8");

            GoogleCredentials credentials = GoogleCredentials.fromStream(getClass().getResourceAsStream("/" + jsonFile));
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

            BlobId blobId = BlobId.of(bucketName, srcFileName);
            boolean result = storage.delete(blobId);

            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
