package com.luruoyang.nursing.service;

import com.luruoyang.nursing.entity.domain.NursingProject;
import com.luruoyang.nursing.entity.dto.NursingItemDto;

import java.util.List;

/**
 * @author luruoyang
 */
public interface MemberOrderService {
  List<NursingProject> wxGetProjectPage(NursingItemDto dto);

  NursingProject wxGetProjectById(Long projectId);
}
