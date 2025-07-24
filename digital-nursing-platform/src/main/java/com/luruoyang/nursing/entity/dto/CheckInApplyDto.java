package com.luruoyang.nursing.entity.dto;

import com.luruoyang.nursing.entity.domain.CheckInConfig;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author luruoyang
 */
@Data
public class CheckInApplyDto implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private ElderDto checkInElderDto;
  private List<ElderFamilyDto> elderFamilyDtoList;
  private CheckInConfigDto checkInConfigDto;
  private ContractDto checkInContractDto;
}
