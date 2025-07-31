package com.luruoyang.nursing.entity.dto;

import com.luruoyang.nursing.entity.domain.NursingItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NursingItemDto extends NursingItem {
  private Integer pageNum;
  private Integer pageSize;
}
