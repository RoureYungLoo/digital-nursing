package com.luruoyang.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luruoyang.nursing.domain.Elder;


/**
 * 老人Mapper接口
 *
 * @author luruoyang
 * @date 2025-07-23
 */
@Mapper
public interface ElderMapper extends BaseMapper<Elder> {
  /**
   * 查询老人
   *
   * @param id 老人主键
   * @return 老人
   */
  public Elder selectElderById(Long id);

  /**
   * 查询老人列表
   *
   * @param elder 老人
   * @return 老人集合
   */
  public List<Elder> selectElderList(Elder elder);

  /**
   * 新增老人
   *
   * @param elder 老人
   * @return 结果
   */
  public int insertElder(Elder elder);

  /**
   * 修改老人
   *
   * @param elder 老人
   * @return 结果
   */
  public int updateElder(Elder elder);

  /**
   * 删除老人
   *
   * @param id 老人主键
   * @return 结果
   */
  public int deleteElderById(Long id);

  /**
   * 批量删除老人
   *
   * @param ids 需要删除的数据主键集合
   * @return 结果
   */
  public int deleteElderByIds(Long[] ids);
}
