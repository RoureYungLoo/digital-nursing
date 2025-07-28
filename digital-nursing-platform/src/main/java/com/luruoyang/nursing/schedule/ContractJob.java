package com.luruoyang.nursing.schedule;

import com.luruoyang.nursing.service.IContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author luruoyang
 */
@Component
public class ContractJob {

  @Autowired
  private IContractService contractService;

  public void updateContractStatus() {
    contractService.updateContractStatus();
  }
}
