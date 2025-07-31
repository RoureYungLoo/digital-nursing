package com.luruoyang.nursing.controller.miniprogramwx;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.luruoyang.common.core.domain.R;
import com.luruoyang.common.utils.UserThreadLocal;
import com.luruoyang.nursing.entity.dto.ReservationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luruoyang.common.annotation.Log;
import com.luruoyang.common.core.controller.BaseController;
import com.luruoyang.common.core.domain.AjaxResult;
import com.luruoyang.common.enums.BusinessType;
import com.luruoyang.nursing.entity.domain.Reservation;
import com.luruoyang.nursing.service.IReservationService;
import com.luruoyang.common.utils.poi.ExcelUtil;
import com.luruoyang.common.core.page.TableDataInfo;

/**
 * 预约信息Controller
 *
 * @author luruoyang
 * @date 2025-07-31
 */
@RestController
@RequestMapping("/member/reservation")
@Api(tags = "预约信息相关接口")
public class ReservationController extends BaseController {
  @Autowired
  private IReservationService reservationService;

  /**
   * 查询预约信息列表
   */
  @PreAuthorize("@ss.hasPermi('member:reservation:list')")
  @GetMapping("/list")
  @ApiOperation("查询预约信息列表")
  public TableDataInfo<List<Reservation>> list(Reservation reservation) {
    startPage();
    List<Reservation> list = reservationService.selectReservationList(reservation);
    return getDataTable(list);
  }

  /**
   * 查询预约信息列表
   */
  @GetMapping("/page")
  @ApiOperation("查询预约信息列表")
  public TableDataInfo<List<Reservation>> page(ReservationDto dto) {
    startPage();
    List<Reservation> list = reservationService.selectReservationPage(dto);
    return getDataTable(list);
  }

  /**
   * 导出预约信息列表
   */
  @PreAuthorize("@ss.hasPermi('member:reservation:export')")
  @Log(title = "预约信息", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  @ApiOperation("导出预约信息列表")
  public void export(HttpServletResponse response, Reservation reservation) {
    List<Reservation> list = reservationService.selectReservationList(reservation);
    ExcelUtil<Reservation> util = new ExcelUtil<Reservation>(Reservation.class);
    util.exportExcel(response, list, "预约信息数据");
  }

  /**
   * 获取预约信息详细信息
   */
  @PreAuthorize("@ss.hasPermi('member:reservation:query')")
  @GetMapping(value = "/{id}")
  @ApiOperation("获取预约信息详细信息")
  public R<Reservation> getInfo(@ApiParam(value = "预约信息ID", required = true) @PathVariable("id") Long id) {
    return R.ok(reservationService.selectReservationById(id));
  }

  /**
   * 新增预约信息
   */
  @PreAuthorize("@ss.hasPermi('member:reservation:add')")
  @Log(title = "预约信息", businessType = BusinessType.INSERT)
  @PostMapping
  @ApiOperation("新增预约信息")
  public AjaxResult add(@ApiParam(value = "预约信息实体", required = true) @RequestBody Reservation reservation) {
    return toAjax(reservationService.insertReservation(reservation));
  }

  /**
   * 修改预约信息
   */
  @PreAuthorize("@ss.hasPermi('member:reservation:edit')")
  @Log(title = "预约信息", businessType = BusinessType.UPDATE)
  @PutMapping
  @ApiOperation("修改预约信息")
  public AjaxResult edit(@ApiParam(required = true, value = "预约信息实体") @RequestBody Reservation reservation) {
    return toAjax(reservationService.updateReservation(reservation));
  }

  /**
   * 删除预约信息
   */
  @PreAuthorize("@ss.hasPermi('member:reservation:remove')")
  @Log(title = "预约信息", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  @ApiOperation("删除预约信息")
  public AjaxResult remove(@PathVariable Long[] ids) {
    return toAjax(reservationService.deleteReservationByIds(ids));
  }

  @Log(title = "取消预约", businessType = BusinessType.UPDATE)
  @GetMapping("/{id:\\d+}/cancel")
  @ApiOperation("取消预约")
  public AjaxResult cancel(@PathVariable Long id) {
    return toAjax(reservationService.cancel(id));
  }

  @Log(title = "查询当天取消预约数量", businessType = BusinessType.SELECT)
  @GetMapping("/cancelled-count")
  @ApiOperation("查询当天取消预约数量")
  public AjaxResult getCancelTimes() {
    Long userId = UserThreadLocal.getUserId();
    System.out.println("------" + userId);
    return success(reservationService.getCancelTimes());
  }

  @Log(title = "查询每个时间段剩余预约次数", businessType = BusinessType.SELECT)
  @GetMapping("/countByTime")
  @ApiOperation("查询每个时间段剩余预约次数")
  public AjaxResult countByTime(Long time) {
    return success(reservationService.countByTime(time));
  }
}
