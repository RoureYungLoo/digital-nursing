package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import com.luruoyang.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.NursingItemMapper;
import com.luruoyang.nursing.domain.NursingItem;
import com.luruoyang.nursing.service.INursingItemService;

/**
 * 护理项目Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-20
 */
@Service
public class NursingItemServiceImpl extends ServiceImpl<NursingItemMapper, NursingItem> implements INursingItemService {
  @Autowired
  private NursingItemMapper nursingItemMapper;

  /**
   * 查询护理项目
   *
   * @param id 护理项目主键
   * @return 护理项目
   */
  @Override
  public NursingItem selectNursingItemById(Long id) {
    return this.getById(id);
  }

  /**
   * 查询护理项目列表
   *
   * @param nursingItem 护理项目
   * @return 护理项目
   */
  @Override
  public List<NursingItem> selectNursingItemList(NursingItem nursingItem) {
    return nursingItemMapper.selectNursingItemList(nursingItem);
  }

  /**
   * 新增护理项目
   *
   * @param nursingItem 护理项目
   * @return 结果
   */
  @Override
  public int insertNursingItem(NursingItem nursingItem) {
    // nursingItem.setCreateTime(DateUtils.getNowDate());
    return this.save(nursingItem) ? 1 : 0;
  }

  /**
   * 修改护理项目
   *
   * @param nursingItem 护理项目
   * @return 结果
   */
  @Override
  public int updateNursingItem(NursingItem nursingItem) {
    // nursingItem.setUpdateTime(DateUtils.getNowDate());
    return this.updateById(nursingItem) ? 1 : 0;
  }

  /**
   * 批量删除护理项目
   *
   * @param ids 需要删除的护理项目主键
   * @return 结果
   */
  @Override
  public int deleteNursingItemByIds(Long[] ids) {
    return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除护理项目信息
   *
   * @param id 护理项目主键
   * @return 结果
   */
  @Override
  public int deleteNursingItemById(Long id) {
    return this.removeById(id) ? 1 : 0;
  }
}
