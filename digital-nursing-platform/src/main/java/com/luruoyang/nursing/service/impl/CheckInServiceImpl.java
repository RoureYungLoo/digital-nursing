package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;
    import com.luruoyang.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.CheckInMapper;
import com.luruoyang.nursing.domain.CheckIn;
import com.luruoyang.nursing.service.ICheckInService;

/**
 * 入住Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-23
 */
@Service
public class CheckInServiceImpl extends ServiceImpl<CheckInMapper, CheckIn> implements ICheckInService {
  @Autowired
  private CheckInMapper checkInMapper;

  /**
   * 查询入住
   *
   * @param id 入住主键
   * @return 入住
   */
  @Override
  public CheckIn selectCheckInById(Long id) {
        return this.getById(id);
  }

  /**
   * 查询入住列表
   *
   * @param checkIn 入住
   * @return 入住
   */
  @Override
  public List<CheckIn> selectCheckInList(CheckIn checkIn) {
    return checkInMapper.selectCheckInList(checkIn);
  }

  /**
   * 新增入住
   *
   * @param checkIn 入住
   * @return 结果
   */
  @Override
  public int insertCheckIn(CheckIn checkIn) {
                            return this.save(checkIn) ? 1 : 0;
  }

  /**
   * 修改入住
   *
   * @param checkIn 入住
   * @return 结果
   */
  @Override
  public int updateCheckIn(CheckIn checkIn) {
                        return this.updateById(checkIn) ? 1 : 0;
  }

  /**
   * 批量删除入住
   *
   * @param ids 需要删除的入住主键
   * @return 结果
   */
  @Override
  public int deleteCheckInByIds(Long[] ids) {
        return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除入住信息
   *
   * @param id 入住主键
   * @return 结果
   */
  @Override
  public int deleteCheckInById(Long id) {
        return this.removeById(id) ? 1 : 0;
  }
}
