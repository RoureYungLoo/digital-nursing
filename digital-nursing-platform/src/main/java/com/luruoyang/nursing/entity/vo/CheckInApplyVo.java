package com.luruoyang.nursing.entity.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.luruoyang.nursing.entity.domain.CheckInConfig;
import com.luruoyang.nursing.entity.dto.ElderDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author luruoyang
 */
@Data
@Builder
public class CheckInApplyVo implements Serializable {
  @Serial
  private static final long serialVersionUID = 1L;

  private ElderVO checkInElderVo;
  private List<ElderFamilyVo> elderFamilyVoList;
  private CheckInConfigVo checkInConfigVo;
  private ContractVo contract;
}