//package com.luruoyang;
//
//import com.luruoyang.utils.Result;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//
//@RestController
//@RequestMapping
////@RequestMapping("/oss")
//public class OssController {
//
//  @Autowired
//  private AliOssService aliOssService;
//
//  /**
//   * 文件上传
//   *
//   * @param multipartFile
//   * @return
//   */
//  @PostMapping("/upload")
//  public Result uploadOSS(@RequestParam("file") MultipartFile multipartFile) {
//    String publicPath;
//    try {
//      publicPath = aliOssService.upload(multipartFile);
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//    return publicPath.isEmpty() ? Result.fail() : Result.success(publicPath);
//  }
//
//  /**
//   * 文件删除
//   */
//  @DeleteMapping("/oss/delete")
//  public Result deleteByFilename(@RequestParam("file") String filename) {
//    if (aliOssService.deleteByFilename(filename)) {
//      return Result.success("delete success");
//    } else {
//      return Result.fail();
//    }
//  }
//
//  /**
//   * 文件下载
//   */
//
//  @GetMapping("/oss/download")
//  public void downloadFile(@RequestParam("file") String objectName, HttpServletResponse response)  {
//    aliOssService.download(objectName, response);
//  }
//
//
//}