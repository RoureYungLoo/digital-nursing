package com.luruoyang.nursing.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luruoyang.nursing.entity.domain.NursingPlan;
import com.luruoyang.nursing.entity.dto.NursingPlanDto;
import com.luruoyang.nursing.entity.vo.NursingPlanVo;
import org.springframework.transaction.annotation.Transactional;

/**
 * 护理计划Service接口
 *
 * @author luruoyang
 * @date 2025-07-20
 */
public interface INursingPlanService extends IService<NursingPlan> {
  /**
   * 查询护理计划
   *
   * @param id 护理计划主键
   * @return 护理计划
   */
  public NursingPlanVo selectNursingPlanById(Integer id);

  /**
   * 查询护理计划列表
   *
   * @param nursingPlan 护理计划
   * @return 护理计划集合
   */
  public List<NursingPlan> selectNursingPlanList(NursingPlan nursingPlan);

  /**
   * 新增护理计划
   *
   * @param nursingPlanDto 护理计划DTO
   * @return 结果
   */
  public int insertNursingPlan(NursingPlanDto nursingPlanDto);

  /**
   * 修改护理计划
   *
   * @param nursingPlanVo 护理计划
   * @return 结果
   */
  @Transactional
  public int updateNursingPlan(NursingPlanVo nursingPlanVo);

  /**
   * 批量删除护理计划
   *
   * @param ids 需要删除的护理计划主键集合
   * @return 结果
   */
  @Transactional
  public int deleteNursingPlanByIds(Integer[] ids);

  /**
   * 删除护理计划信息
   *
   * @param id 护理计划主键
   * @return 结果
   */
  public int deleteNursingPlanById(Integer id);
}
