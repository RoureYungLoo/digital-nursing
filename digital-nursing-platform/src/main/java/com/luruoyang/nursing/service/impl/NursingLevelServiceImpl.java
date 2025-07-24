package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luruoyang.common.constant.StatusConstants;
import com.luruoyang.nursing.entity.vo.NursingLevelVo;
import com.luruoyang.nursing.service.INursingPlanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.NursingLevelMapper;
import com.luruoyang.nursing.entity.domain.NursingLevel;
import com.luruoyang.nursing.service.INursingLevelService;

/**
 * 护理等级Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-22
 */
@Service
public class NursingLevelServiceImpl extends ServiceImpl<NursingLevelMapper, NursingLevel> implements INursingLevelService {
  @Autowired
  private NursingLevelMapper nursingLevelMapper;

  @Autowired
  private INursingPlanService nursingPlanService;

  /**
   * 查询护理等级
   *
   * @param id 护理等级主键
   * @return 护理等级
   */
  @Override
  public NursingLevel selectNursingLevelById(Integer id) {
    return this.getById(id);
  }

  /**
   * 查询护理等级列表
   *
   * @param nursingLevel 护理等级
   * @return 护理等级
   */
  @Override
  public List<NursingLevelVo> selectNursingLevelList(NursingLevel nursingLevel) {
    List<NursingLevel> list = nursingLevelMapper.selectNursingLevelList(nursingLevel);
    List<NursingLevelVo> voList = list.stream().map(i -> {
      NursingLevelVo vo = new NursingLevelVo();
      BeanUtils.copyProperties(i, vo);
      vo.setPlanName(nursingPlanService.getById(i.getLplanId()).getPlanName());
      return vo;
    }).toList();
    return voList;
  }

  /**
   * 新增护理等级
   *
   * @param nursingLevel 护理等级
   * @return 结果
   */
  @Override
  public int insertNursingLevel(NursingLevel nursingLevel) {
    return this.save(nursingLevel) ? 1 : 0;
  }

  /**
   * 修改护理等级
   *
   * @param nursingLevel 护理等级
   * @return 结果
   */
  @Override
  public int updateNursingLevel(NursingLevel nursingLevel) {
    return this.updateById(nursingLevel) ? 1 : 0;
  }

  /**
   * 批量删除护理等级
   *
   * @param ids 需要删除的护理等级主键
   * @return 结果
   */
  @Override
  public int deleteNursingLevelByIds(Integer[] ids) {
    return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除护理等级信息
   *
   * @param id 护理等级主键
   * @return 结果
   */
  @Override
  public int deleteNursingLevelById(Integer id) {
    return this.removeById(id) ? 1 : 0;
  }

  /**
   * 查询所有护理等级列表
   *
   * @return List<NursingLevelVo>
   */
  @Override
  public List<NursingLevel> findAll() {
    LambdaQueryWrapper<NursingLevel> wrapper = Wrappers.lambdaQuery();
    wrapper.eq(NursingLevel::getStatus, StatusConstants.ENABLE);
    return this.list(wrapper);
  }
}
