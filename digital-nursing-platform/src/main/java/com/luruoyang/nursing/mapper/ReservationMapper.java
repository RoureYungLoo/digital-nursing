package com.luruoyang.nursing.mapper;

import java.time.LocalDateTime;
import java.util.List;

import com.luruoyang.nursing.entity.vo.ReserveVo;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.luruoyang.nursing.entity.domain.Reservation;
import org.apache.ibatis.annotations.Param;


/**
 * 预约信息Mapper接口
 *
 * @author luruoyang
 * @date 2025-07-31
 */
@Mapper
public interface ReservationMapper extends BaseMapper<Reservation> {
  /**
   * 查询预约信息
   *
   * @param id 预约信息主键
   * @return 预约信息
   */
  public Reservation selectReservationById(Long id);

  /**
   * 查询预约信息列表
   *
   * @param reservation 预约信息
   * @return 预约信息集合
   */
  public List<Reservation> selectReservationList(Reservation reservation);

  /**
   * 新增预约信息
   *
   * @param reservation 预约信息
   * @return 结果
   */
  public int insertReservation(Reservation reservation);

  /**
   * 修改预约信息
   *
   * @param reservation 预约信息
   * @return 结果
   */
  public int updateReservation(Reservation reservation);

  /**
   * 删除预约信息
   *
   * @param id 预约信息主键
   * @return 结果
   */
  public int deleteReservationById(Long id);

  /**
   * 批量删除预约信息
   *
   * @param ids 需要删除的数据主键集合
   * @return 结果
   */
  public int deleteReservationByIds(Long[] ids);

//  ReserveVo countByTime(@Param("time") Long time, @Param("start")LocalDateTime start, @Param("end")LocalDateTime end, @Param("status")Long status);
List<ReserveVo> countByTime();
}
