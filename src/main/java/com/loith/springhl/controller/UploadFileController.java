package com.loith.springhl.controller;

import com.loith.springhl.service.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/upload-files")
public class UploadFileController {
  private final UploadFileService uploadFileService;

  @PostMapping()
  @ResponseStatus(HttpStatus.CREATED)
  public String createFile(@RequestParam("file") MultipartFile file) {
    return uploadFileService.uploadFile(file);
  }

  @PostMapping("/multiple")
  @ResponseStatus(HttpStatus.CREATED)
  public List<String> createFiles(@RequestParam("files") List<MultipartFile> files) {
    return uploadFileService.uploadMultipleFiles(files);
  }


  
}
