package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luruoyang.nursing.entity.domain.NursingItemPlan;
import com.luruoyang.nursing.entity.dto.NursingPlanDto;
import com.luruoyang.nursing.entity.vo.NursingItemPlanVo;
import com.luruoyang.nursing.entity.vo.NursingItemVo;
import com.luruoyang.nursing.entity.vo.NursingPlanVo;
import com.luruoyang.nursing.service.INursingItemPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.NursingPlanMapper;
import com.luruoyang.nursing.entity.domain.NursingPlan;
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
  public NursingPlanVo selectNursingPlanById(Integer id) {
    // 护理计划
    NursingPlan nursingPlan = this.getById(id);
    NursingPlanVo nursingPlanVo = new NursingPlanVo();
    BeanUtils.copyProperties(nursingPlan, nursingPlanVo);

    // 护理项目-护理计划关联表
    LambdaQueryWrapper<NursingItemPlan> wrapper = Wrappers.lambdaQuery();
    wrapper.eq(NursingItemPlan::getPlanId, id);

    List<NursingItemPlan> nursingItemPlanList = nursingItemPlanService.list(wrapper);
    List<NursingItemPlanVo> nursingItemPlanVoList = nursingItemPlanList.stream().map(new Function<NursingItemPlan, NursingItemPlanVo>() {
      @Override
      public NursingItemPlanVo apply(NursingItemPlan nursingItemPlan) {
        NursingItemPlanVo nursingItemPlanVo = new NursingItemPlanVo();
        BeanUtils.copyProperties(nursingItemPlan, nursingItemPlanVo);
        return nursingItemPlanVo;
      }
    }).collect(Collectors.toList());
    nursingPlanVo.setItemPlanVoList(nursingItemPlanVoList);

    return nursingPlanVo;
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
    boolean savedBatch = false;
    List<NursingItemPlan> itemPlanList = nursingPlanDto.getNursingItemPlanList();
    if (CollectionUtils.isNotEmpty(itemPlanList)) {
      itemPlanList.forEach(i -> i.setPlanId(nursingPlan.getId()));
      savedBatch = nursingItemPlanService.saveBatch(itemPlanList);
    }
    return savedBatch ? 1 : 0;
  }

  /**
   * 修改护理计划
   *
   * @param nursingPlanVo 护理计划
   * @return 结果
   */
  @Override
  public int updateNursingPlan(NursingPlanVo nursingPlanVo) {
    Integer planId = nursingPlanVo.getId();
    NursingPlan nursingPlan = new NursingPlan();
    BeanUtils.copyProperties(nursingPlanVo, nursingPlan);
    boolean updateById = this.updateById(nursingPlan);
    if (!updateById) {
      log.error("updateById(nursingPlan) failed");
    }

    List<NursingItemPlanVo> itemPlanVoList = nursingPlanVo.getItemPlanVoList();
    if (CollectionUtils.isNotEmpty(itemPlanVoList)) {
      boolean savedBatch;
      // 护理项目-护理计划 关联表:  删除旧的
      LambdaUpdateWrapper<NursingItemPlan> wrapper = Wrappers.lambdaUpdate();
      wrapper.eq(NursingItemPlan::getPlanId, planId);
      boolean removed = nursingItemPlanService.remove(wrapper);
      if (!removed) {
        log.error("remove failed in updateNursingPlan");
      }

      // 护理项目-护理计划 关联表:  添加新的
      List<NursingItemPlan> nursingItemPlanList = itemPlanVoList.stream().map(i -> {
        NursingItemPlan nursingItemPlan = new NursingItemPlan();
        BeanUtils.copyProperties(i, nursingItemPlan);
        nursingItemPlan.setPlanId(planId);
        return nursingItemPlan;
      }).toList();
      savedBatch = nursingItemPlanService.saveBatch(nursingItemPlanList);
      // 修改护理计划
      return savedBatch ? 1 : 0;
    } else {
      // 启用 or 禁用
      return updateById ? 1 : 0;
    }

  }

  /**
   * 批量删除护理计划
   *
   * @param ids 需要删除的护理计划主键
   * @return 结果
   */
  @Override
  public int deleteNursingPlanByIds(Integer[] ids) {
    // 护理计划表
    List<Integer> planIds = Arrays.asList(ids);
    this.removeBatchByIds(planIds);

    // 护理计划 - 护理项目 关联表
    LambdaUpdateWrapper<NursingItemPlan> wrapper = Wrappers.lambdaUpdate();
    wrapper.in(NursingItemPlan::getPlanId, planIds);
    boolean removed = nursingItemPlanService.remove(wrapper);

    return removed ? 1 : 0;
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
