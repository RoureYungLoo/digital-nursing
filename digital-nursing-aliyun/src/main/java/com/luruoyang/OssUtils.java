package com.luruoyang;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.OSSObject;
import com.aliyuncs.exceptions.ClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author luruoyang
 */
@Component
@Slf4j
public class OssUtils {

  @Autowired
  private AliOssProperties aliOssProperties;

  public String upload(MultipartFile multipartFile) {
    String endpoint = aliOssProperties.getEndpoint();
    String bucketName = aliOssProperties.getBucketName();
    OSS ossClient = getOssClient();
    String objectName = multipartFile.getOriginalFilename();

    if (Objects.nonNull(objectName)) {
      objectName = getDirsByFileName(objectName) + objectName;
    }

    try {
      ossClient.putObject(bucketName, objectName, multipartFile.getInputStream());
    } catch (IOException e) {
      log.error("{}", e.getMessage());
      throw new RuntimeException(e);
    }

    ossClient.shutdown();

    //https://BucketName.Endpoint/ObjectName
    return String.format("https://%s.%s/%s", bucketName, endpoint, objectName);
  }

  private OSS getOssClient() {
    String endpoint = aliOssProperties.getEndpoint();
    String region = aliOssProperties.getRegion();
    EnvironmentVariableCredentialsProvider credentialsProvider = null;
    try {
      credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
    } catch (ClientException e) {
      log.error("{}", e.getMessage());
      throw new RuntimeException(e);
    }

    ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
    clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
    return OSSClientBuilder.create()
        .endpoint(endpoint)
        .credentialsProvider(credentialsProvider)
        .region(region)
        .build();
  }

  public boolean deleteByFilename(String filename) {
    OSS ossClient = getOssClient();
    boolean res = false;
    try {
      String objectName = getDirsByFileName(filename) + filename;
      ossClient.deleteObject(aliOssProperties.getBucketName(), objectName);
    } catch (OSSException oe) {
      log.error("{}", oe.getMessage());
      throw oe;
    } finally {
      res = true;
      log.info("删除成功 bucket: {} {}", aliOssProperties.getBucketName(), filename);
      ossClient.shutdown();
    }
    return res;
  }

  public void download(String filename, HttpServletResponse response) {
    String fname = null;
    fname = URLEncoder.encode(filename, StandardCharsets.UTF_8);
    response.setContentType("application/octet-stream");
    response.setHeader("Content-Disposition", "attachment;filename=" + fname);

    try {
      // 获取OSS文件对象
      String objectName = getDirsByFileName(filename) + filename;
      OSS ossClient = getOssClient();
      OSSObject object = ossClient.getObject(aliOssProperties.getBucketName(), objectName);

      try (InputStream inputStream = object.getObjectContent();
           OutputStream outputStream = response.getOutputStream()) {
        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1) {
          outputStream.write(buffer, 0, len);
        }
        outputStream.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException("文件下载失败");
    } catch (OSSException e) {
      e.printStackTrace();
      throw new RuntimeException("文件不存在或下载失败");
    }
  }

  private static String getDirsByFileName(String filename) {
    int filenameHash = filename.hashCode();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < 8; i++) {
      int subDir = filenameHash & 15;
      sb.append(subDir).append("/");
      filenameHash >>= 4;
    }
    return sb.toString();
  }
}