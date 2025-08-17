package com.loith.springhl.controller;

import com.loith.springhl.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {
    private final UploadFileService uploadFileService;

    @PostMapping
    public List<String> upload(@RequestParam("files") List<MultipartFile> files) {
        return uploadFileService.uploadMultipleFiles(files);
    }
}
