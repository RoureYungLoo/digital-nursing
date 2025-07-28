package com.luruoyang.nursing.schedule;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
//@EnableScheduling
public class MyTask {

  /**
   * 定时任务 每隔1秒触发一次
   */
//  @Scheduled(cron = "*/1 * * * * ?")
  public void executeTask() {
    log.info("###############################定时任务开始执行：{}", LocalDateTime.now());
  }

  public void scanError() {
    log.info("****************************scan error at " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
  }
}