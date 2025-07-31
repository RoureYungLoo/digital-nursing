package com.luruoyang.nursing.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luruoyang.common.constant.StatusConstants;
import com.luruoyang.common.exception.base.BaseException;
import com.luruoyang.common.utils.DateUtils;
import com.luruoyang.common.utils.UserThreadLocal;
import com.luruoyang.nursing.entity.dto.ReservationDto;
import com.luruoyang.nursing.entity.vo.ReserveVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.ReservationMapper;
import com.luruoyang.nursing.entity.domain.Reservation;
import com.luruoyang.nursing.service.IReservationService;

/**
 * 预约信息Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-31
 */
@Service
public class ReservationServiceImpl extends ServiceImpl<ReservationMapper, Reservation> implements IReservationService {
  @Autowired
  private ReservationMapper reservationMapper;

  /**
   * 查询预约信息
   *
   * @param id 预约信息主键
   * @return 预约信息
   */
  @Override
  public Reservation selectReservationById(Long id) {
    return this.getById(id);
  }

  /**
   * 查询预约信息列表
   *
   * @param reservation 预约信息
   * @return 预约信息
   */
  @Override
  public List<Reservation> selectReservationList(Reservation reservation) {
    return reservationMapper.selectReservationList(reservation);
  }

  /**
   * 新增预约信息
   *
   * @param reservation 预约信息
   * @return 结果
   */
  @Override
  public int insertReservation(Reservation reservation) {
    return this.save(reservation) ? 1 : 0;
  }

  /**
   * 修改预约信息
   *
   * @param reservation 预约信息
   * @return 结果
   */
  @Override
  public int updateReservation(Reservation reservation) {
    return this.updateById(reservation) ? 1 : 0;
  }

  /**
   * 批量删除预约信息
   *
   * @param ids 需要删除的预约信息主键
   * @return 结果
   */
  @Override
  public int deleteReservationByIds(Long[] ids) {
    return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除预约信息信息
   *
   * @param id 预约信息主键
   * @return 结果
   */
  @Override
  public int deleteReservationById(Long id) {
    return this.removeById(id) ? 1 : 0;
  }

  /**
   * 预约信息分页查询
   *
   * @param dto dto
   * @return 结果
   */
  @Override
  public List<Reservation> selectReservationPage(ReservationDto dto) {
    return this.list(Wrappers.<Reservation>lambdaQuery().eq(Reservation::getStatus, dto.getStatus()));
  }

  /**
   * 取消预约
   *
   * @param id 预约ID
   * @return 返回值
   */
  @Override
  public int cancel(Long id) {
    Reservation reservation = this.getById(id);
    if (Objects.isNull(reservation)) {
      throw new BaseException("预约信息不存在");
    }

    reservation.setStatus(StatusConstants.RESERVE_CANCELLED);
    this.updateById(reservation);
    return 1;
  }

  @Override
  public Long getCancelTimes() {
    Long userId = UserThreadLocal.getUserId();
    LocalDateTime now = LocalDateTime.now();
    LocalDate localDate = now.toLocalDate();
    LocalDate nextDay = localDate.plusDays(1);

    long count = this.count(Wrappers.<Reservation>lambdaQuery()
        .eq(Reservation::getStatus, StatusConstants.RESERVE_CANCELLED)
        .eq(Reservation::getUpdateBy, userId)
        .between(Reservation::getUpdateTime, localDate, nextDay)
    );

    return count;
  }

  @Override
  public List<ReserveVo> countByTime(Long time) {
    Instant instant = Instant.ofEpochMilli(time);
    LocalDateTime start = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    LocalDateTime end = start.plusMinutes(30L);

    List<ReserveVo> reserveVo = reservationMapper.countByTime();
    return reserveVo;
  }
}
