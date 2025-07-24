package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luruoyang.common.constant.StatusConstants;
import com.luruoyang.nursing.entity.domain.*;
import com.luruoyang.nursing.entity.dto.*;
import com.luruoyang.nursing.service.*;
import com.luruoyang.nursing.util.CodeGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.CheckInMapper;
import org.springframework.transaction.annotation.Transactional;

/**
 * 入住Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-23
 */
@Service
public class CheckInServiceImpl extends ServiceImpl<CheckInMapper, CheckIn> implements ICheckInService {
  @Autowired
  private CheckInMapper checkInMapper;

  @Autowired
  private IBedService bedService;

  @Autowired
  private IElderService elderService;

  @Autowired
  private ICheckInService checkInService;

  @Autowired
  private ICheckInConfigService checkInConfigService;

  @Autowired
  private IContractService contractService;

  /**
   * 查询入住
   *
   * @param id 入住主键
   * @return 入住
   */
  @Override
  public CheckIn selectCheckInById(Long id) {
    return this.getById(id);
  }

  /**
   * 查询入住列表
   *
   * @param checkIn 入住
   * @return 入住
   */
  @Override
  public List<CheckIn> selectCheckInList(CheckIn checkIn) {
    return checkInMapper.selectCheckInList(checkIn);
  }

  /**
   * 新增入住
   *
   * @param checkIn 入住
   * @return 结果
   */
  @Override
  public int insertCheckIn(CheckIn checkIn) {
    return this.save(checkIn) ? 1 : 0;
  }

  /**
   * 修改入住
   *
   * @param checkIn 入住
   * @return 结果
   */
  @Override
  public int updateCheckIn(CheckIn checkIn) {
    return this.updateById(checkIn) ? 1 : 0;
  }

  /**
   * 批量删除入住
   *
   * @param ids 需要删除的入住主键
   * @return 结果
   */
  @Override
  public int deleteCheckInByIds(Long[] ids) {
    return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除入住信息
   *
   * @param id 入住主键
   * @return 结果
   */
  @Override
  public int deleteCheckInById(Long id) {
    return this.removeById(id) ? 1 : 0;
  }

  /**
   * 入住申请
   *
   * @param dto CheckInApplyDto
   * @return 1 success, 0 failed
   */
  @Override
  @Transactional
  public int checkInApply(CheckInApplyDto dto) {

    List<ElderFamilyDto> familyDtoList = dto.getElderFamilyDtoList();
    CheckInConfigDto checkInConfigDto = dto.getCheckInConfigDto();
    ContractDto contractDto = dto.getCheckInContractDto();
    ElderDto elderDto = dto.getCheckInElderDto();

    // 床位状态
    Long bedId = checkInConfigDto.getBedId();
    LambdaUpdateWrapper<Bed> br = Wrappers.lambdaUpdate();
    br.eq(Bed::getId, bedId);
    boolean update = bedService.update(br);
    if (!update) {
      log.warn("error =======> 床位状态更新失败");
    }

    //入住老人
    Elder elder = new Elder();
    BeanUtils.copyProperties(elderDto, elder);
    elder.setBedId(bedId);
    elder.setRemark(JSONObject.toJSONString(dto.getElderFamilyDtoList()));
    boolean saved = elderService.save(elder);
    if (!saved) {
      log.warn("error =======> 入住老人保存失败");
    }

    // 入住表
    CheckIn checkIn = CheckIn.builder()
        .elderName(elder.getName())
        .elderId(elder.getId())
        .idCardNo(elder.getIdCardNo())
        .startDate(checkInConfigDto.getStartDate())
        .endDate(checkInConfigDto.getEndDate())
        .nursingLevelName(checkInConfigDto.getNursingLevelName())
        .bedNumber(bedService.getById(bedId).getBedNumber())
        .status(StatusConstants.CHECKED_IN)
        .sortOrder(checkInConfigDto.getSortOrder())
        .build();

    boolean saveCheckIn = checkInService.save(checkIn);
    if (!saveCheckIn) {
      log.warn("error =======> 入住信息 保存失败");
    }

    //入住配置表
    CheckInConfig checkInConfig = new CheckInConfig();
    BeanUtils.copyProperties(checkInConfigDto, checkInConfig);
    checkInConfig.setCheckInId(checkIn.getId());
    boolean savedCheckInConfig = checkInConfigService.save(checkInConfig);
    if (!savedCheckInConfig) {
      log.warn("error =======> 入住配置 保存失败");
    }

    // 入住合同表
    Contract contract = Contract.builder().build();
    BeanUtils.copyProperties(contractDto, contract);
    contract.setElderId(elder.getId());
    contract.setElderName(elder.getName());
    contract.setContractNumber(CodeGenerator.generateContractNumber());
    contract.setStartDate(checkIn.getStartDate());
    contract.setEndDate(checkIn.getEndDate());
    contract.setStatus(StatusConstants.PENDING);
    boolean savedContract = contractService.save(contract);
    if (!savedContract) {
      log.warn("error =======> 入住合同 保存失败");
    }

    return update && saved && saveCheckIn && savedCheckInConfig && savedContract ? 1 : 0;
  }
}
