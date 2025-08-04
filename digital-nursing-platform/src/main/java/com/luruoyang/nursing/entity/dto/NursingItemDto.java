package com.luruoyang.nursing.entity.dto;

import com.luruoyang.nursing.entity.domain.NursingProject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NursingItemDto extends NursingProject {
  private Integer pageNum;
  private Integer pageSize;
}
