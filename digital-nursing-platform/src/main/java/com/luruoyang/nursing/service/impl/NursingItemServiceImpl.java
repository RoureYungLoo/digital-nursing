package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luruoyang.nursing.constants.RedisKey;
import com.luruoyang.nursing.entity.vo.NursingItemVo;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.NursingItemMapper;
import com.luruoyang.nursing.entity.domain.NursingItem;
import com.luruoyang.nursing.service.INursingItemService;

/**
 * 护理项目Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-20
 */
@Service
@Slf4j
public class NursingItemServiceImpl extends ServiceImpl<NursingItemMapper, NursingItem> implements INursingItemService {
  @Autowired
  private NursingItemMapper nursingItemMapper;

  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;

  /**
   * 查询护理项目
   *
   * @param id 护理项目主键
   * @return 护理项目
   */
  @Override
  public NursingItem selectNursingItemById(Long id) {
    ValueOperations<Object, Object> cache = redisTemplate.opsForValue();
    NursingItem nursingItem = (NursingItem) cache.get(RedisKey.NURSING_ITEM_ + id);
    if (Objects.nonNull(nursingItem)) {
      log.info("Hit Cache {} ", RedisKey.NURSING_ITEM_ + id);
      return nursingItem;
    }
    log.info("Not Hit Cache {} ", RedisKey.NURSING_ITEM_ + id);
    nursingItem = this.getById(id);
    cache.set(RedisKey.NURSING_ITEM_ + id, nursingItem);
    log.info("Set Cache {} ", RedisKey.NURSING_ITEM_ + id);

    return nursingItem;
  }

  /**
   * 查询护理项目列表
   *
   * @param nursingItem 护理项目
   * @return 护理项目
   */
  @Override
  public List<NursingItem> selectNursingItemList(NursingItem nursingItem) {
    List<NursingItem> nursingItemList = nursingItemMapper.selectNursingItemList(nursingItem);
    return nursingItemList;
  }

  /**
   * 新增护理项目
   *
   * @param nursingItem 护理项目
   * @return 结果
   */
  @Override
  public int insertNursingItem(NursingItem nursingItem) {
    if (this.save(nursingItem)) {
      redisTemplate.delete(RedisKey.NURSING_ITEM_ALL);
      log.info("Evict Cache {}", RedisKey.NURSING_ITEM_ALL);
      return 1;
    }
    return 0;
  }

  /**
   * 修改护理项目
   *
   * @param nursingItem 护理项目
   * @return 结果
   */
  @Override
  public int updateNursingItem(NursingItem nursingItem) {
    Long id = nursingItem.getId();
    if (this.updateById(nursingItem)) {
      redisTemplate.delete(RedisKey.NURSING_ITEM_ALL);
      redisTemplate.delete(RedisKey.NURSING_ITEM_ + id);
      log.info("Evict Cache {}", RedisKey.NURSING_ITEM_ALL);
      log.info("Evict Cache {}:{}", RedisKey.NURSING_ITEM_, id);
      return 1;
    }
    return 0;
  }

  /**
   * 批量删除护理项目
   *
   * @param ids 需要删除的护理项目主键
   * @return 结果
   */
  @Override
  public int deleteNursingItemByIds(Long[] ids) {
    if (this.removeByIds(Arrays.asList(ids))) {
      redisTemplate.delete(RedisKey.NURSING_ITEM_ALL);
      log.info("Evict Cache {}", RedisKey.NURSING_ITEM_ALL);
      for (Long id : ids) {
        redisTemplate.delete(RedisKey.NURSING_ITEM_ + id);
        log.info("Evict Cache {}", RedisKey.NURSING_ITEM_ + id);
      }
      return 1;
    }
    return 0;

  }

  /**
   * 删除护理项目信息
   *
   * @param id 护理项目主键
   * @return 结果
   */
  @Override
  public int deleteNursingItemById(Long id) {
    if (this.removeById(id)) {
      redisTemplate.delete(RedisKey.NURSING_ITEM_ALL);
      log.info("Evict Cache {}", RedisKey.NURSING_ITEM_ALL);
      redisTemplate.delete(RedisKey.NURSING_ITEM_ + id);
      log.info("Evict Cache {}", RedisKey.NURSING_ITEM_ + id);
      return 1;
    }
    return 0;
  }

  @Override
  public List<NursingItemVo> findAll() {
    ValueOperations<Object, Object> cache = redisTemplate.opsForValue();
    List<NursingItemVo> itemVoList = (List<NursingItemVo>) cache.get(RedisKey.NURSING_ITEM_ALL);
    if (!Collections.isEmpty(itemVoList)) {
      log.info("Hit Cache {}", RedisKey.NURSING_ITEM_ALL);
      return itemVoList;
    }
    log.info("Not Hit Cache {}", RedisKey.NURSING_ITEM_ALL);
    LambdaQueryWrapper<NursingItem> wrapper = Wrappers.lambdaQuery();
    wrapper
        .select(NursingItem::getId, NursingItem::getName)
        .eq(NursingItem::getStatus, 1);
    List<NursingItem> itemList = this.list(wrapper);
    itemVoList = itemList.stream().map(nursingItem -> new NursingItemVo(nursingItem.getName(), nursingItem.getId())).collect(Collectors.toList());

    cache.set(RedisKey.NURSING_ITEM_ALL, itemVoList);

    log.info("Set Cache {}", RedisKey.NURSING_ITEM_ALL);

    return itemVoList;
  }
}
