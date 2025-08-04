package com.luruoyang.nursing.entity.vo;

import com.luruoyang.nursing.entity.domain.DeviceData;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DeviceDataVo extends DeviceData {
  private String nickname;
}
