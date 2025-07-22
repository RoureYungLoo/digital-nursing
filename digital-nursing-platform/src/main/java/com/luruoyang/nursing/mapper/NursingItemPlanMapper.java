package com.luruoyang.nursing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luruoyang.nursing.domain.NursingItemPlan;


/**
 * 护理计划和项目关联Mapper接口
 *
 * @author luruoyang
 * @date 2025-07-22
 */
@Mapper
public interface NursingItemPlanMapper extends BaseMapper<NursingItemPlan> {
  /**
   * 查询护理计划和项目关联
   *
   * @param id 护理计划和项目关联主键
   * @return 护理计划和项目关联
   */
  public NursingItemPlan selectNursingItemPlanById(Integer id);

  /**
   * 查询护理计划和项目关联列表
   *
   * @param nursingItemPlan 护理计划和项目关联
   * @return 护理计划和项目关联集合
   */
  public List<NursingItemPlan> selectNursingItemPlanList(NursingItemPlan nursingItemPlan);

  /**
   * 新增护理计划和项目关联
   *
   * @param nursingItemPlan 护理计划和项目关联
   * @return 结果
   */
  public int insertNursingItemPlan(NursingItemPlan nursingItemPlan);

  /**
   * 修改护理计划和项目关联
   *
   * @param nursingItemPlan 护理计划和项目关联
   * @return 结果
   */
  public int updateNursingItemPlan(NursingItemPlan nursingItemPlan);

  /**
   * 删除护理计划和项目关联
   *
   * @param id 护理计划和项目关联主键
   * @return 结果
   */
  public int deleteNursingItemPlanById(Integer id);

  /**
   * 批量删除护理计划和项目关联
   *
   * @param ids 需要删除的数据主键集合
   * @return 结果
   */
  public int deleteNursingItemPlanByIds(Integer[] ids);
}
