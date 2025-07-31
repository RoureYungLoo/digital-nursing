package com.luruoyang.nursing.service;

import com.luruoyang.nursing.entity.domain.NursingItem;
import com.luruoyang.nursing.entity.dto.NursingItemDto;

import java.util.List;

/**
 * @author luruoyang
 */
public interface MemberOrderService {
  List<NursingItem> wxGetProjectPage(NursingItemDto dto);

  NursingItem wxGetProjectById(Long projectId);
}
