package com.luruoyang.nursing.entity.vo;

import lombok.Builder;
import lombok.Data;

/**
 * @author luruoyang
 */
@Data
@Builder
public class ProductVo {
  private String productId;
  private String name;
}
