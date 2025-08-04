package com.luruoyang.nursing.entity.resp;


import lombok.Data;

import java.util.Map;

@Data
public class Service {
  private String serviceId;
  private Map<String, Object> properties;
  private String eventTime;
}