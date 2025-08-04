package com.luruoyang.nursing.entity.domain;

import com.luruoyang.common.core.domain.BaseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel("护理员-老人关联表实体")
public class NursingElder extends BaseEntity {
  @ApiModelProperty("主键")
  private Long id;
  @ApiModelProperty("护理员ID")
  private Long nursingId;
  @ApiModelProperty("老人ID")
  private Long elderId;
}
