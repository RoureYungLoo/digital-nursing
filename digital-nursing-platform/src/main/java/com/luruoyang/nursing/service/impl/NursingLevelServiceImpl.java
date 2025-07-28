package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luruoyang.common.constant.StatusConstants;
import com.luruoyang.nursing.entity.vo.NursingLevelVo;
import com.luruoyang.nursing.constants.RedisKey;
import com.luruoyang.nursing.service.INursingPlanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
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
@Slf4j
public class NursingLevelServiceImpl extends ServiceImpl<NursingLevelMapper, NursingLevel> implements INursingLevelService {
  @Autowired
  private NursingLevelMapper nursingLevelMapper;

  @Autowired
  private INursingPlanService nursingPlanService;

  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;

  /**
   * 查询护理等级
   *
   * @param id 护理等级主键
   * @return 护理等级
   */
  @Override
  public NursingLevel selectNursingLevelById(Integer id) {
    ValueOperations<Object, Object> cache = redisTemplate.opsForValue();
    NursingLevel nursingLevel = (NursingLevel) cache.get(RedisKey.NURSING_LEVEL_ + id);
    if (Objects.nonNull(nursingLevel)) {
      log.info("Hit {} Cache", RedisKey.NURSING_LEVEL_ + id);
      return nursingLevel;
    }

    log.info("Not Hit {} Cache", RedisKey.NURSING_LEVEL_ + id);
    nursingLevel = this.getById(id);
    cache.set(RedisKey.NURSING_LEVEL_ + id, nursingLevel);
    log.info("Set Hit {} Cache", RedisKey.NURSING_LEVEL_ + id);

    return nursingLevel;
  }

  /**
   * 查询护理等级列表
   *
   * @param nursingLevel 护理等级
   * @return 护理等级
   */
  @Override
  public List<NursingLevelVo> selectNursingLevelList(NursingLevel nursingLevel) {
//    ValueOperations<Object, Object> cache = redisTemplate.opsForValue();
//    List<NursingLevelVo> voList = null;
//    try {
//      voList = (List<NursingLevelVo>) cache.get(RedisKey.NURSING_LEVEL_ALL);
//    } catch (Exception e) {
//      throw new RuntimeException(e);
//    }
//    if (CollectionUtils.isNotEmpty(voList)) {
//      log.info("----------> Cache Hit");
//      return voList;
//    }
//
//    log.info("----------> Cache No Hit");
    List<NursingLevel> list = nursingLevelMapper.selectNursingLevelList(nursingLevel);
    List<NursingLevelVo> voList = list.stream().map(i -> {
      NursingLevelVo vo = new NursingLevelVo();
      BeanUtils.copyProperties(i, vo);
      vo.setPlanName(nursingPlanService.getById(i.getLplanId()).getPlanName());
      return vo;
    }).toList();
//    cache.set(RedisKey.NURSING_LEVEL_ALL, voList);

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
    if (this.save(nursingLevel)) {
      redisTemplate.delete(RedisKey.NURSING_LEVEL_ALL);
      log.info("Evict {} Cache", RedisKey.NURSING_LEVEL_ALL);
      redisTemplate.opsForValue().set(RedisKey.NURSING_LEVEL_ + nursingLevel.getId(), nursingLevel);
      log.info("Evict {} Cache", RedisKey.NURSING_LEVEL_ALL);
      return 1;
    }
    return 0;
  }

  /**
   * 修改护理等级
   *
   * @param nursingLevel 护理等级
   * @return 结果
   */
  @Override
  public int updateNursingLevel(NursingLevel nursingLevel) {
    if (this.updateById(nursingLevel)) {
      redisTemplate.delete(RedisKey.NURSING_LEVEL_ALL);
      log.info("Evict {} Cache", RedisKey.NURSING_LEVEL_ALL);
      redisTemplate.delete(RedisKey.NURSING_LEVEL_ + nursingLevel.getId());
      log.info("Evict {} Cache", RedisKey.NURSING_LEVEL_ + nursingLevel.getId());
      return 1;
    }
    return 0;
  }

  /**
   * 批量删除护理等级
   *
   * @param ids 需要删除的护理等级主键
   * @return 结果
   */
  @Override
  public int deleteNursingLevelByIds(Integer[] ids) {
    if (this.removeByIds(Arrays.asList(ids))) {
      redisTemplate.delete(RedisKey.NURSING_LEVEL_ALL);
      log.info("Evict {} Cache", RedisKey.NURSING_LEVEL_ALL);
      for (Integer id : ids) {
        redisTemplate.delete(RedisKey.NURSING_LEVEL_ + id);
        log.info("Evict {} Cache", RedisKey.NURSING_LEVEL_ + id);
      }
      return 1;
    }
    return 0;

  }

  /**
   * 删除护理等级信息
   *
   * @param id 护理等级主键
   * @return 结果
   */
  @Override
  public int deleteNursingLevelById(Integer id) {
    if (this.removeById(id)) {
      redisTemplate.delete(RedisKey.NURSING_LEVEL_ALL);
      log.info("Evict {} Cache", RedisKey.NURSING_LEVEL_ALL);
      redisTemplate.delete(RedisKey.NURSING_LEVEL_ + id);
      log.info("Evict {} Cache", RedisKey.NURSING_LEVEL_ + id);
      return 1;
    }
    return 0;
  }

  /**
   * 查询所有护理等级列表
   *
   * @return List<NursingLevelVo>
   */
  @Override
  public List<NursingLevel> findAll() {
    ValueOperations<Object, Object> cache = redisTemplate.opsForValue();
    List<NursingLevel> list = (List<NursingLevel>) cache.get(RedisKey.NURSING_LEVEL_ALL);
    if (CollectionUtils.isNotEmpty(list)) {
      log.info("Hit {} Cache", RedisKey.NURSING_LEVEL_ALL);
      return list;
    }

    log.info("Not Hit {} Cache", RedisKey.NURSING_LEVEL_ALL);
    LambdaQueryWrapper<NursingLevel> wrapper = Wrappers.lambdaQuery();
    wrapper.eq(NursingLevel::getStatus, StatusConstants.ENABLE);
    list = this.list(wrapper);
    cache.set(RedisKey.NURSING_LEVEL_ALL, list);
    log.info("Set {} Cache", RedisKey.NURSING_LEVEL_ALL);

    return list;
  }
}
