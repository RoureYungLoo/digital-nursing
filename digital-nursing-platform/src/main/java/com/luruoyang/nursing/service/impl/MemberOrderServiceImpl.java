package com.luruoyang.nursing.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luruoyang.nursing.entity.domain.NursingProject;
import com.luruoyang.nursing.entity.dto.NursingItemDto;
import com.luruoyang.nursing.service.INursingItemService;
import com.luruoyang.nursing.service.MemberOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author luruoyang
 */
@Service
public class MemberOrderServiceImpl implements MemberOrderService {
  @Autowired
  private INursingItemService itemService;

  @Override
  public List<NursingProject> wxGetProjectPage(NursingItemDto dto) {

    String name = dto.getName();
    Integer status = dto.getStatus();
    List<NursingProject> list = itemService.list(Wrappers.<NursingProject>lambdaQuery()
        .eq(StringUtils.isNotBlank(name), NursingProject::getStatus, name)
        .like(Objects.nonNull(status), NursingProject::getName, status));
    return list;
  }

  @Override
  public NursingProject wxGetProjectById(Long projectId) {
    NursingProject nursingItem = itemService.getById(projectId);
    return nursingItem;
  }
}
