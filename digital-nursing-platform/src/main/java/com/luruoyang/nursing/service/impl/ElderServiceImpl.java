package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luruoyang.nursing.entity.dto.ElderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.ElderMapper;
import com.luruoyang.nursing.entity.domain.Elder;
import com.luruoyang.nursing.service.IElderService;

/**
 * 老人Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-23
 */
@Service
public class ElderServiceImpl extends ServiceImpl<ElderMapper, Elder> implements IElderService {
  @Autowired
  private ElderMapper elderMapper;

  /**
   * 查询老人
   *
   * @param id 老人主键
   * @return 老人
   */
  @Override
  public Elder selectElderById(Long id) {
    return this.getById(id);
  }

  /**
   * 查询老人列表
   *
   * @param elder 老人
   * @return 老人
   */
  @Override
  public List<Elder> selectElderList(Elder elder) {
    return elderMapper.selectElderList(elder);
  }

  /**
   * 新增老人
   *
   * @param elder 老人
   * @return 结果
   */
  @Override
  public int insertElder(Elder elder) {
    return this.save(elder) ? 1 : 0;
  }

  /**
   * 修改老人
   *
   * @param elder 老人
   * @return 结果
   */
  @Override
  public int updateElder(Elder elder) {
    return this.updateById(elder) ? 1 : 0;
  }

  /**
   * 批量删除老人
   *
   * @param ids 需要删除的老人主键
   * @return 结果
   */
  @Override
  public int deleteElderByIds(Long[] ids) {
    return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除老人信息
   *
   * @param id 老人主键
   * @return 结果
   */
  @Override
  public int deleteElderById(Long id) {
    return this.removeById(id) ? 1 : 0;
  }

  @Override
  public Page<Elder> selectElderPage(ElderDto dto) {
    dto.setStatus(1);
    String name = dto.getName();
    String idCardNo = dto.getIdCardNo();

    Page<Elder> page = new Page<>(dto.getPageNum(), dto.getPageSize());

    LambdaQueryWrapper<Elder> wrapper = Wrappers.lambdaQuery();
    wrapper.eq(Elder::getStatus, dto.getStatus())
        .like(StringUtils.isNotBlank(name), Elder::getName, name)
        .eq(StringUtils.isNotBlank(idCardNo), Elder::getIdCardNo, idCardNo)
        .select(Elder::getId, Elder::getName, Elder::getIdCardNo, Elder::getBedNumber);
    page = this.page(page, wrapper);
    return page;
  }
}
