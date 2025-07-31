package com.luruoyang.nursing.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.luruoyang.nursing.entity.domain.Reservation;
import com.luruoyang.nursing.entity.dto.ReservationDto;

/**
 * 预约信息Service接口
 *
 * @author luruoyang
 * @date 2025-07-31
 */
public interface IReservationService extends IService<Reservation> {
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
   * 批量删除预约信息
   *
   * @param ids 需要删除的预约信息主键集合
   * @return 结果
   */
  public int deleteReservationByIds(Long[] ids);

  /**
   * 删除预约信息信息
   *
   * @param id 预约信息主键
   * @return 结果
   */
  public int deleteReservationById(Long id);

  List<Reservation> selectReservationPage(ReservationDto dto);

  int cancel(Long id);

  int getCancelTimes();

}
