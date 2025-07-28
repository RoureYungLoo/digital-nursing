package com.luruoyang.nursing.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luruoyang.common.constant.StatusConstants;
import com.luruoyang.nursing.entity.domain.*;
import com.luruoyang.nursing.entity.dto.*;
import com.luruoyang.nursing.entity.vo.*;
import com.luruoyang.nursing.service.*;
import com.luruoyang.nursing.utils.CodeGenerator;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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

  @Autowired
  private IRoomService roomService;

  @Autowired
  private IFloorService floorService;

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
    log.warn("-----------> CheckInApplyDto: {}", dto);
    List<ElderFamilyDto> familyDtoList = dto.getElderFamilyDtoList();
    CheckInConfigDto checkInConfigDto = dto.getCheckInConfigDto();
    ContractDto contractDto = dto.getCheckInContractDto();
    ElderDto elderDto = dto.getCheckInElderDto();

    Long bedId = checkInConfigDto.getBedId();

    //入住老人
    Elder elder = new Elder();
    String idCardNo = elderDto.getIdCardNo();
    LambdaQueryWrapper<Elder> wrapper = Wrappers.lambdaQuery();
    wrapper.eq(Elder::getIdCardNo, idCardNo)
        .ne(Elder::getStatus, StatusConstants.ELDER_DISABLED)
        .or()
        .ne(Elder::getStatus, StatusConstants.ELDER_CHECKED_OUT);
    Elder elderDb = elderService.getOne(wrapper);

    if (Objects.nonNull(elderDb)) {
      // 更新已存在的老人信息
      BeanUtils.copyProperties(elderDto, elderDb);
      if (!elderService.updateById(elderDb)) {
        log.warn("error =======> 老人入住信息 更新失败");
      }
    } else {
      // 新增老人信息
      BeanUtils.copyProperties(elderDto, elder);
      elder.setBedId(bedId);
      elder.setRemark(JSONObject.toJSONString(familyDtoList));
      if (!elderService.save(elder)) {
        log.warn("error =======> 入住老人保存失败");
      }
    }

    // 床位状态
    LambdaUpdateWrapper<Bed> br = Wrappers.lambdaUpdate();
    br.eq(Bed::getId, bedId)
        .set(Bed::getBedStatus, StatusConstants.BED_IN_USE);
    if (!bedService.update(br)) {
      log.warn("error =======> 床位状态更新失败");
    }

    // 入住表
    CheckIn checkIn = CheckIn.builder()
        .elderName(elder.getName())
        .elderId(elder.getId())
        .idCardNo(elder.getIdCardNo())
        .startDate(LocalDate.from(checkInConfigDto.getStartDate()))
        .endDate(LocalDate.from(checkInConfigDto.getEndDate()))
        .nursingLevelName(checkInConfigDto.getNursingLevelName())
        .bedNumber(bedService.getById(bedId).getBedNumber())
        .status(StatusConstants.CHECKED_IN)
        .sortOrder(checkInConfigDto.getSortOrder())
        .build();

    if (!checkInService.save(checkIn)) {
      log.warn("error =======> 入住信息 保存失败");
    }

    //入住配置表
    CheckInConfig checkInConfig = new CheckInConfig();
    BeanUtils.copyProperties(checkInConfigDto, checkInConfig);
    checkInConfig.setCheckInId(checkIn.getId());
    if (!checkInConfigService.save(checkInConfig)) {
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
    contract.setStatus(StatusConstants.CONTRACT_PENDING);
    if (!contractService.save(contract)) {
      log.warn("error =======> 入住合同 保存失败");
    }

    return 1;
  }

  /**
   * 查询入住申请详情
   *
   * @param checkInId 入住申请ID
   * @return CheckInApplyVo
   */
  @Override
  public CheckInApplyVo getCheckInDetailById(Long checkInId) {

    // 入住表
    CheckIn checkInDb = checkInService.getById(checkInId);

    // 入住配置表
    LambdaQueryWrapper<CheckInConfig> wrapper = Wrappers.lambdaQuery();
    wrapper.eq(CheckInConfig::getCheckInId, checkInId);
    CheckInConfig checkInConfigDb = checkInConfigService.getOne(wrapper);
    CheckInConfigVo checkInConfigVo = new CheckInConfigVo();
    BeanUtils.copyProperties(checkInConfigDb, checkInConfigVo);
    checkInConfigVo.setStartDate(checkInDb.getStartDate().atStartOfDay());
    checkInConfigVo.setEndDate(checkInDb.getEndDate().atStartOfDay());

    String bedNumber = checkInDb.getBedNumber();
    Bed elderBedInfo = bedService.getOne(Wrappers.<Bed>lambdaQuery().eq(Bed::getBedNumber, bedNumber));
    Long bedId = elderBedInfo.getId();
    Long roomId = elderBedInfo.getRoomId();
    Room roomInfo = roomService.getById(roomId);
    Long floorId = roomInfo.getFloorId();
    Floor floorInfo = floorService.getById(floorId);

    checkInConfigVo.setBedId(bedId);
    checkInConfigVo.setCode(roomInfo.getCode());
    checkInConfigVo.setFloorId(floorId);
    checkInConfigVo.setFloorName(floorInfo.getName());
    checkInConfigVo.setRoomId(roomId);


    // 老人表
    Long elderId = checkInDb.getElderId();
    Elder elderDb = elderService.getOne(Wrappers.<Elder>lambdaQuery().eq(Elder::getId, elderId));
    ElderVO elderVO = new ElderVO();
    BeanUtils.copyProperties(elderDb, elderVO);
    String birthYear = elderDb.getIdCardNo().substring(6, 14);
    Period period = Period.between(LocalDate.parse(birthYear, DateTimeFormatter.ofPattern("yyyyMMdd")), LocalDate.now());
    int age = period.getYears();
    elderVO.setAge(String.valueOf(age));

    List<ElderFamilyVo> elderFamilyVoList = null;
    String familyList = elderDb.getRemark();
    if (StringUtils.isNotBlank(familyList)) {
      elderFamilyVoList = JSONObject.parseObject(familyList, List.class);
    }

    // 合同表

    List<Contract> contractList = contractService.list(Wrappers.<Contract>lambdaQuery()
        .eq(Contract::getElderId, elderId)
        .orderByDesc(Contract::getCreateTime)
    );
    ContractVo contractVo = new ContractVo();

    if (CollectionUtils.isNotEmpty(contractList)) {
      BeanUtils.copyProperties(contractList.get(0), contractVo);
    }

    return CheckInApplyVo.builder()
        .checkInConfigVo(checkInConfigVo)
        .checkInElderVo(elderVO)
        .elderFamilyVoList(elderFamilyVoList)
        .contract(contractVo)
        .build();
  }
}
