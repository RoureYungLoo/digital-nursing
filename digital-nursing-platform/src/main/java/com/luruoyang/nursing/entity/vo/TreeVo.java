package com.luruoyang.nursing.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @author luruoyang
 */
@Data
public class TreeVo {
  /**
   * 菜单名
   */
  private String label;
  /**
   * 菜单ID
   */
  private String value;

  /**
   * 子菜单
   */
  List<TreeVo> children;
}
