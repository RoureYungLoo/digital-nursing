package com.luruoyang.nursing.service.impl;

import java.util.List;
import com.luruoyang.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.luruoyang.nursing.mapper.NursingItemPlanMapper;
import com.luruoyang.nursing.domain.NursingItemPlan;
import com.luruoyang.nursing.service.INursingItemPlanService;

/**
 * 护理计划和项目关联Service业务层处理
 * 
 * @author luruoyang
 * @date 2025-07-19
 */
@Service
public class NursingItemPlanServiceImpl implements INursingItemPlanService 
{
    @Autowired
    private NursingItemPlanMapper nursingItemPlanMapper;

    /**
     * 查询护理计划和项目关联
     * 
     * @param id 护理计划和项目关联主键
     * @return 护理计划和项目关联
     */
    @Override
    public NursingItemPlan selectNursingItemPlanById(Long id)
    {
        return nursingItemPlanMapper.selectNursingItemPlanById(id);
    }

    /**
     * 查询护理计划和项目关联列表
     * 
     * @param nursingItemPlan 护理计划和项目关联
     * @return 护理计划和项目关联
     */
    @Override
    public List<NursingItemPlan> selectNursingItemPlanList(NursingItemPlan nursingItemPlan)
    {
        return nursingItemPlanMapper.selectNursingItemPlanList(nursingItemPlan);
    }

    /**
     * 新增护理计划和项目关联
     * 
     * @param nursingItemPlan 护理计划和项目关联
     * @return 结果
     */
    @Override
    public int insertNursingItemPlan(NursingItemPlan nursingItemPlan)
    {
        nursingItemPlan.setCreateTime(DateUtils.getNowDate());
        return nursingItemPlanMapper.insertNursingItemPlan(nursingItemPlan);
    }

    /**
     * 修改护理计划和项目关联
     * 
     * @param nursingItemPlan 护理计划和项目关联
     * @return 结果
     */
    @Override
    public int updateNursingItemPlan(NursingItemPlan nursingItemPlan)
    {
        nursingItemPlan.setUpdateTime(DateUtils.getNowDate());
        return nursingItemPlanMapper.updateNursingItemPlan(nursingItemPlan);
    }

    /**
     * 批量删除护理计划和项目关联
     * 
     * @param ids 需要删除的护理计划和项目关联主键
     * @return 结果
     */
    @Override
    public int deleteNursingItemPlanByIds(Long[] ids)
    {
        return nursingItemPlanMapper.deleteNursingItemPlanByIds(ids);
    }

    /**
     * 删除护理计划和项目关联信息
     * 
     * @param id 护理计划和项目关联主键
     * @return 结果
     */
    @Override
    public int deleteNursingItemPlanById(Long id)
    {
        return nursingItemPlanMapper.deleteNursingItemPlanById(id);
    }
}
