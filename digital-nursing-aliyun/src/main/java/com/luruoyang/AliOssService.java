package com.luruoyang;

import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

/**
 * @author luruoyang
 */
public interface AliOssService {
  String upload(MultipartFile multipartFile);

  boolean deleteByFilename(String filename);

  void download(String filename, HttpServletResponse response);
}
