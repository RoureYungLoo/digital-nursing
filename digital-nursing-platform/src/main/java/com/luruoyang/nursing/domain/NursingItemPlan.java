package com.luruoyang.nursing.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.luruoyang.common.annotation.Excel;
import com.luruoyang.common.core.domain.BaseEntity;

/**
 * 护理计划和项目关联对象 nursing_item_plan
 * 
 * @author luruoyang
 * @date 2025-07-19
 */
public class NursingItemPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 计划id */
    @Excel(name = "计划id")
    private Integer planId;

    /** 项目id */
    @Excel(name = "项目id")
    private Integer itemId;

    /** 计划执行时间 */
    @Excel(name = "计划执行时间")
    private String executeTime;

    /** 执行周期 0 天 1 周 2月 */
    @Excel(name = "执行周期 0 天 1 周 2月")
    private Long executeCycle;

    /** 执行频次 */
    @Excel(name = "执行频次")
    private Long executeFrequency;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }

    public void setPlanId(Integer planId) 
    {
        this.planId = planId;
    }

    public Integer getPlanId() 
    {
        return planId;
    }

    public void setItemId(Integer itemId) 
    {
        this.itemId = itemId;
    }

    public Integer getItemId() 
    {
        return itemId;
    }

    public void setExecuteTime(String executeTime) 
    {
        this.executeTime = executeTime;
    }

    public String getExecuteTime() 
    {
        return executeTime;
    }

    public void setExecuteCycle(Long executeCycle) 
    {
        this.executeCycle = executeCycle;
    }

    public Long getExecuteCycle() 
    {
        return executeCycle;
    }

    public void setExecuteFrequency(Long executeFrequency) 
    {
        this.executeFrequency = executeFrequency;
    }

    public Long getExecuteFrequency() 
    {
        return executeFrequency;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("planId", getPlanId())
            .append("itemId", getItemId())
            .append("executeTime", getExecuteTime())
            .append("executeCycle", getExecuteCycle())
            .append("executeFrequency", getExecuteFrequency())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createBy", getCreateBy())
            .append("updateBy", getUpdateBy())
            .append("remark", getRemark())
            .toString();
    }
}
