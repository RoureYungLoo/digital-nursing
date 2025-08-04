package com.luruoyang.nursing.entity.vo;

import com.luruoyang.common.core.domain.entity.SysUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author luruoyang
 */
@Data
@ApiModel("床位Vo")
public class BedVo {

  @ApiModelProperty(value = "床位ID")
  private Long id;

  @ApiModelProperty(value = "床位号")
  private String bedNumber;

  @ApiModelProperty(value = "床位状态: 未入住0, 已入住1 入住申请中2")
  private Integer bedStatus;

  @ApiModelProperty(value = "房间ID")
  private Long roomId;

  @ApiModelProperty(value = "老人姓名")
  private String ename;

  @ApiModelProperty(value = "老人id")
  private Long elderId;

  @ApiModelProperty(value = "护理员")
  private List<SysUser> userVos;

  private List<DeviceVo> deviceVos;

}
