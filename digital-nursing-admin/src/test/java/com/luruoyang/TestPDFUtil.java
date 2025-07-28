package com.luruoyang;


import com.luruoyang.common.utils.PDFUtil;

import java.io.File;
import java.io.FileInputStream;

public class TestPDFUtil {
  public static void main(String[] args) throws Exception {
    File file = new File("F:\\001_学习资料\\项目一\\08. 智能评估-集成AI大模型\\资料\\体检报告样例\\体检报告-张芳-女-72岁.pdf");
    String pdf = PDFUtil.pdfToString(new FileInputStream(file));
    System.out.println(pdf);
  }
}
