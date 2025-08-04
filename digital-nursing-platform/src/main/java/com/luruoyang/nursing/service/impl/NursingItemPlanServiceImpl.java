package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.NursingItemPlanMapper;
import com.luruoyang.nursing.entity.domain.NursingProjectPlan;
import com.luruoyang.nursing.service.INursingItemPlanService;

/**
 * 护理计划和项目关联Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-22
 */
@Service
public class NursingItemPlanServiceImpl extends ServiceImpl<NursingItemPlanMapper, NursingProjectPlan> implements INursingItemPlanService {
  @Autowired
  private NursingItemPlanMapper nursingItemPlanMapper;

  /**
   * 查询护理计划和项目关联
   *
   * @param id 护理计划和项目关联主键
   * @return 护理计划和项目关联
   */
  @Override
  public NursingProjectPlan selectNursingItemPlanById(Integer id) {
        return this.getById(id);
  }

  /**
   * 查询护理计划和项目关联列表
   *
   * @param nursingItemPlan 护理计划和项目关联
   * @return 护理计划和项目关联
   */
  @Override
  public List<NursingProjectPlan> selectNursingItemPlanList(NursingProjectPlan nursingItemPlan) {
    return nursingItemPlanMapper.selectNursingItemPlanList(nursingItemPlan);
  }

  /**
   * 新增护理计划和项目关联
   *
   * @param nursingItemPlan 护理计划和项目关联
   * @return 结果
   */
  @Override
  public int insertNursingItemPlan(NursingProjectPlan nursingItemPlan) {
                            return this.save(nursingItemPlan) ? 1 : 0;
  }

  /**
   * 修改护理计划和项目关联
   *
   * @param nursingItemPlan 护理计划和项目关联
   * @return 结果
   */
  @Override
  public int updateNursingItemPlan(NursingProjectPlan nursingItemPlan) {
                        return this.updateById(nursingItemPlan) ? 1 : 0;
  }

  /**
   * 批量删除护理计划和项目关联
   *
   * @param ids 需要删除的护理计划和项目关联主键
   * @return 结果
   */
  @Override
  public int deleteNursingItemPlanByIds(Integer[] ids) {
        return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除护理计划和项目关联信息
   *
   * @param id 护理计划和项目关联主键
   * @return 结果
   */
  @Override
  public int deleteNursingItemPlanById(Integer id) {
        return this.removeById(id) ? 1 : 0;
  }
}
