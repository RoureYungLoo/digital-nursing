package com.luruoyang;

import com.luruoyang.common.constant.Constants;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileWriter;
import java.util.Properties;


public class VelocityTest {

  public static void main(String args[]) throws Exception {
    //1.1.创建Properties对象
    Properties properties = new Properties();
    //表示告诉我们去哪一个类下找对应的模版资源文件
    properties.setProperty("resource.loader.file.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
    //设置字符集
    properties.setProperty(Velocity.INPUT_ENCODING, Constants.UTF8);
    //1.初始化模版引擎
    Velocity.init(properties);
    //2.获取对应的模版文件
    Template template = Velocity.getTemplate("vm/test/test.vm", Constants.UTF8);

    //3.1 创建数据模型
    Context context = new VelocityContext();
    context.put("title", "首页");
    context.put("headline", "一级标题");
    context.put("subtitle", "三级标题");
    //3.2 指定输出的位置
    FileWriter fileWriter = new FileWriter("digital-nursing-generator\\\\\\\\src\\\\\\\\main\\\\\\\\resources\\\\\\\\index.html");
    //3.合并数据和模版文件
    template.merge(context, fileWriter);
    //4.关闭流
    fileWriter.close();
  }
}
