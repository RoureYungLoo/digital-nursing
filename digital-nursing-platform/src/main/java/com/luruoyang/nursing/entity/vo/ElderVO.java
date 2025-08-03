package com.luruoyang.nursing.entity.vo;

import com.luruoyang.nursing.entity.domain.Elder;
import lombok.*;

/**
 * @author luruoyang
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElderVO extends Elder {
  private String age;
}
