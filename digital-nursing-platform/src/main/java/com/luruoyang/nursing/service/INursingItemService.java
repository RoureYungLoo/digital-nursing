package com.luruoyang.nursing.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luruoyang.nursing.entity.vo.NursingItemVo;
import com.luruoyang.nursing.entity.domain.NursingProject;

/**
 * 护理项目Service接口
 *
 * @author luruoyang
 * @date 2025-07-20
 */
public interface INursingItemService extends IService<NursingProject> {
  /**
   * 查询护理项目
   *
   * @param id 护理项目主键
   * @return 护理项目
   */
  public NursingProject selectNursingItemById(Long id);

  /**
   * 查询护理项目列表
   *
   * @param nursingItem 护理项目
   * @return 护理项目集合
   */
  public List<NursingProject> selectNursingItemList(NursingProject nursingItem);

  /**
   * 新增护理项目
   *
   * @param nursingItem 护理项目
   * @return 结果
   */
  public int insertNursingItem(NursingProject nursingItem);

  /**
   * 修改护理项目
   *
   * @param nursingItem 护理项目
   * @return 结果
   */
  public int updateNursingItem(NursingProject nursingItem);

  /**
   * 批量删除护理项目
   *
   * @param ids 需要删除的护理项目主键集合
   * @return 结果
   */
  public int deleteNursingItemByIds(Long[] ids);

  /**
   * 删除护理项目信息
   *
   * @param id 护理项目主键
   * @return 结果
   */
  public int deleteNursingItemById(Long id);

  public List<NursingItemVo> findAll();
}
