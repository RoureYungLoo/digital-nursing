package com.luruoyang.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luruoyang.nursing.entity.domain.NursingProject;


/**
 * 护理项目Mapper接口
 *
 * @author luruoyang
 * @date 2025-07-20
 */
@Mapper
public interface NursingItemMapper extends BaseMapper<NursingProject> {
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
   * 删除护理项目
   *
   * @param id 护理项目主键
   * @return 结果
   */
  public int deleteNursingItemById(Long id);

  /**
   * 批量删除护理项目
   *
   * @param ids 需要删除的数据主键集合
   * @return 结果
   */
  public int deleteNursingItemByIds(Long[] ids);
}
