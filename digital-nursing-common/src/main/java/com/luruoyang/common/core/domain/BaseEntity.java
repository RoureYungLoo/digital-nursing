package com.luruoyang.common.core.domain;

//import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Entity基类
 *
 * @author ruoyi
 */
@Data
@ApiModel(description = "基础Entity", value = "BaseEntity")
public class BaseEntity implements Serializable {
  //@Serial
  private static final long serialVersionUID = 1L;

  /**
   * 搜索值
   */
  @JsonIgnore
  @TableField(exist = false)
  private String searchValue;

  /**
   * 创建者
   */
  @ApiModelProperty("创建者")
  @TableField(fill = FieldFill.INSERT)
  private String createBy;

  /**
   * 创建时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty("创建时间")
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;

  /**
   * 更新者
   */
  @ApiModelProperty("更新者")
  @TableField(fill = FieldFill.UPDATE)
  private String updateBy;

  /**
   * 更新时间
   */
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ApiModelProperty("更新时间")
  @TableField(fill = FieldFill.UPDATE)
  private LocalDateTime updateTime;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String remark;

  /**
   * 请求参数
   */
  @ApiModelProperty("请求参数")
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @TableField(exist = false)
  private Map<String, Object> params;

  public Map<String, Object> getParams() {
    if (params == null) {
      params = new HashMap<>();
    }
    return params;
  }
}
