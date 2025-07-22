package com.luruoyang.nursing.domain;

import com.luruoyang.common.annotation.Excel;
import lombok.*;
import com.luruoyang.common.core.domain.BaseEntity;

import java.io.Serial;

/**
 * 护理计划对象 nursing_plan
 *
 * @author luruoyang
 * @date 2025-07-20
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NursingPlan extends BaseEntity {
  @Serial
  private static final long serialVersionUID = 1L;
  /**
   * 编号
   */
  protected Integer id;
  /**
   * 排序号
   */
  protected Integer sortNo;
  /**
   * 名称
   */
  @Excel(name = "名称")
  protected String planName;
  /**
   * 状态 0禁用 1启用
   */
  @Excel(name = "状态 0禁用 1启用")
  protected Integer status;
}
