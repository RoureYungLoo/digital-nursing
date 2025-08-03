package com.luruoyang.nursing.schedule;

import com.luruoyang.nursing.service.IContractService;
import com.luruoyang.nursing.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luruoyang
 */
@Component
public class ContractJob {

  @Autowired
  private IContractService contractService;

  @Autowired
  private IReservationService reservationService;

  /**
   * 更新合同状态
   */
  public void updateContractStatus() {
    contractService.updateContractStatus();
  }

  /**
   * 更新预约状态
   */
  public void updateReservationStatus() {
    reservationService.updateReservationStatus();
  }
}
