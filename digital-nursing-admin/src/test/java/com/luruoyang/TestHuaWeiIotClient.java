package com.luruoyang;

import com.huaweicloud.sdk.iotda.v5.IoTDAClient;
import com.huaweicloud.sdk.iotda.v5.model.ListProductsRequest;
import com.huaweicloud.sdk.iotda.v5.model.ListProductsResponse;
import com.huaweicloud.sdk.iotda.v5.model.ProductSummary;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestHuaWeiIotClient {
  @Autowired
  private IoTDAClient client;

  @Test
  public void selectProductList() {
    ListProductsRequest req = new ListProductsRequest();
    req.setLimit(50);
    ListProductsResponse resp = client.listProducts(req);
    int statusCode = resp.getHttpStatusCode();
    if (statusCode == 200) {
      List<ProductSummary> summaryList = resp.getProducts();
      summaryList.forEach(System.out::println);
    } else {
      System.out.println("statusCode: " + statusCode);
    }
  }
}
