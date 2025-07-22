package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.luruoyang.common.utils.DateUtils;
import com.luruoyang.nursing.domain.NursingItemPlan;
import com.luruoyang.nursing.dto.NursingPlanDto;
import com.luruoyang.nursing.service.INursingItemPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.NursingPlanMapper;
import com.luruoyang.nursing.domain.NursingPlan;
import com.luruoyang.nursing.service.INursingPlanService;

/**
 * 护理计划Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-20
 */
@Service
@Slf4j
public class NursingPlanServiceImpl extends ServiceImpl<NursingPlanMapper, NursingPlan> implements INursingPlanService {
  @Autowired
  private NursingPlanMapper nursingPlanMapper;

  @Autowired
  private INursingItemPlanService nursingItemPlanService;

  /**
   * 查询护理计划
   *
   * @param id 护理计划主键
   * @return 护理计划
   */
  @Override
  public NursingPlan selectNursingPlanById(Integer id) {
    return this.getById(id);
  }

  /**
   * 查询护理计划列表
   *
   * @param nursingPlan 护理计划
   * @return 护理计划
   */
  @Override
  public List<NursingPlan> selectNursingPlanList(NursingPlan nursingPlan) {
    return nursingPlanMapper.selectNursingPlanList(nursingPlan);
  }

  /**
   * 新增护理计划
   *
   * @param nursingPlanDto 护理计划 dto
   * @return 结果
   */
  @Override
  public int insertNursingPlan(NursingPlanDto nursingPlanDto) {
    // 护理计划表
    NursingPlan nursingPlan = new NursingPlan();
    BeanUtils.copyProperties(nursingPlanDto, nursingPlan);
    if (!this.save(nursingPlan)) {
      log.error("insertNursingPlan fail!");
    }

    // 护理计划 护理项目关联表
    List<NursingItemPlan> itemPlans = nursingPlanDto.getItemPlans();
    boolean savedBatch = nursingItemPlanService.saveBatch(itemPlans);
    return savedBatch ? 1 : 0;
  }

  /**
   * 修改护理计划
   *
   * @param nursingPlan 护理计划
   * @return 结果
   */
  @Override
  public int updateNursingPlan(NursingPlan nursingPlan) {
    return this.updateById(nursingPlan) ? 1 : 0;
  }

  /**
   * 批量删除护理计划
   *
   * @param ids 需要删除的护理计划主键
   * @return 结果
   */
  @Override
  public int deleteNursingPlanByIds(Integer[] ids) {
    return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除护理计划信息
   *
   * @param id 护理计划主键
   * @return 结果
   */
  @Override
  public int deleteNursingPlanById(Integer id) {
    return this.removeById(id) ? 1 : 0;
  }
}
