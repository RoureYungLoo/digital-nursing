package com.luruoyang.nursing.entity.resp;// DevicePropertyEvent.java
import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * @author luruoyang
 */
@Data
public class DevicePropertyEvent {
    private String resource;
    private String event;
    private String eventTime;
    private String eventTimeMs;
    private String requestId;
    private NotifyData notifyData;
}