package com.mohaji.hackathon.domain.Image.dto;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.web.multipart.MultipartFile;

public class CustomMultipartFile implements MultipartFile {
  private final byte[] fileContent;
  private final String name;
  private final String originalFilename;
  private final String contentType;

  public CustomMultipartFile(byte[] fileContent, String name, String originalFilename, String contentType) {
    this.fileContent = fileContent;
    this.name = name;
    this.originalFilename = originalFilename;
    this.contentType = contentType;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getOriginalFilename() {
    return originalFilename;
  }

  @Override
  public String getContentType() {
    return contentType;
  }

  @Override
  public boolean isEmpty() {
    return fileContent.length == 0;
  }

  @Override
  public long getSize() {
    return fileContent.length;
  }

  @Override
  public byte[] getBytes() throws IOException {
    return fileContent;
  }

  @Override
  public InputStream getInputStream() throws IOException {
    return new ByteArrayInputStream(fileContent);
  }

  @Override
  public void transferTo(java.io.File dest) throws IOException, IllegalStateException {
    throw new UnsupportedOperationException("CustomMultipartFile doesn't support transferTo method");
  }
}