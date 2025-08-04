package com.luruoyang;

import org.junit.jupiter.api.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestThreadPoolExecutor {
  public static void main(String[] args) {
    new TestThreadPoolExecutor().testThreadPoolExecutor();
  }


  public void testThreadPoolTaskExecutor() {
    ThreadPoolTaskExecutor tpte = new ThreadPoolTaskExecutor();

  }


  public void testThreadPoolExecutor() {
    ThreadPoolExecutor tpe = new ThreadPoolExecutor(
        50, 100, 10, TimeUnit.MINUTES,
        new ArrayBlockingQueue<>(200), Executors.defaultThreadFactory(),
        new ThreadPoolExecutor.AbortPolicy()
    );

    Thread thread = new Thread(() -> {
      while (true) {
        try {
          Thread.sleep(1000);
          System.out.println(Thread.currentThread().getId() + " " + new Random().nextGaussian());
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      }
    });

    tpe.submit(thread);

  }


}
