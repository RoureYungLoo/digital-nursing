package com.luruoyang.nursing.domain;

import com.luruoyang.common.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.luruoyang.common.core.domain.BaseEntity;

/**
 * 护理计划对象 nursing_plan
 *
 * @author luruoyang
 * @date 2025-07-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NursingPlan extends BaseEntity{
private static final long serialVersionUID = 1L;
    /** 编号 */
    private Integer id;
    /** 排序号 */
    private Integer sortNo;
    /** 名称 */
        @Excel(name = "名称")
    private String planName;
    /** 状态 0禁用 1启用 */
        @Excel(name = "状态 0禁用 1启用")
    private Integer status;
}
