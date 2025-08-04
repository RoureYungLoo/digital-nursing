package com.luruoyang.nursing.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luruoyang.nursing.entity.domain.NursingProjectPlan;

/**
 * 护理计划和项目关联Service接口
 *
 * @author luruoyang
 * @date 2025-07-22
 */
public interface INursingItemPlanService extends IService<NursingProjectPlan> {
  /**
   * 查询护理计划和项目关联
   *
   * @param id 护理计划和项目关联主键
   * @return 护理计划和项目关联
   */
  public NursingProjectPlan selectNursingItemPlanById(Integer id);

  /**
   * 查询护理计划和项目关联列表
   *
   * @param nursingItemPlan 护理计划和项目关联
   * @return 护理计划和项目关联集合
   */
  public List<NursingProjectPlan> selectNursingItemPlanList(NursingProjectPlan nursingItemPlan);

  /**
   * 新增护理计划和项目关联
   *
   * @param nursingItemPlan 护理计划和项目关联
   * @return 结果
   */
  public int insertNursingItemPlan(NursingProjectPlan nursingItemPlan);

  /**
   * 修改护理计划和项目关联
   *
   * @param nursingItemPlan 护理计划和项目关联
   * @return 结果
   */
  public int updateNursingItemPlan(NursingProjectPlan nursingItemPlan);

  /**
   * 批量删除护理计划和项目关联
   *
   * @param ids 需要删除的护理计划和项目关联主键集合
   * @return 结果
   */
  public int deleteNursingItemPlanByIds(Integer[] ids);

  /**
   * 删除护理计划和项目关联信息
   *
   * @param id 护理计划和项目关联主键
   * @return 结果
   */
  public int deleteNursingItemPlanById(Integer id);
}
