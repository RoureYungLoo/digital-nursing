package com.luruoyang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;

/**
 * @author luruoyang
 */
@Service
public class AliOssServiceImpl implements AliOssService {
  @Autowired
  private OssUtils ossUtils;

  @Override
  public String upload(MultipartFile multipartFile) {
    return ossUtils.upload(multipartFile);
  }

  @Override
  public boolean deleteByFilename(String filename) {
    return ossUtils.deleteByFilename(filename);
  }

  @Override
  public void download(String filename, HttpServletResponse response) {
    ossUtils.download(filename,response);
  }
}
