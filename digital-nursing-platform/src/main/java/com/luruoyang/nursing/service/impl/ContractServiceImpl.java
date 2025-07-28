package com.luruoyang.nursing.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luruoyang.common.constant.Constants;
import com.luruoyang.common.constant.StatusConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.ContractMapper;
import com.luruoyang.nursing.entity.domain.Contract;
import com.luruoyang.nursing.service.IContractService;

/**
 * 合同Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-23
 */
@Service
public class ContractServiceImpl extends ServiceImpl<ContractMapper, Contract> implements IContractService {
  @Autowired
  private ContractMapper contractMapper;

  /**
   * 查询合同
   *
   * @param id 合同主键
   * @return 合同
   */
  @Override
  public Contract selectContractById(Long id) {
    return this.getById(id);
  }

  /**
   * 查询合同列表
   *
   * @param contract 合同
   * @return 合同
   */
  @Override
  public List<Contract> selectContractList(Contract contract) {
    return contractMapper.selectContractList(contract);
  }

  /**
   * 新增合同
   *
   * @param contract 合同
   * @return 结果
   */
  @Override
  public int insertContract(Contract contract) {
    return this.save(contract) ? 1 : 0;
  }

  /**
   * 修改合同
   *
   * @param contract 合同
   * @return 结果
   */
  @Override
  public int updateContract(Contract contract) {
    return this.updateById(contract) ? 1 : 0;
  }

  /**
   * 批量删除合同
   *
   * @param ids 需要删除的合同主键
   * @return 结果
   */
  @Override
  public int deleteContractByIds(Long[] ids) {
    return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除合同信息
   *
   * @param id 合同主键
   * @return 结果
   */
  @Override
  public int deleteContractById(Long id) {
    return this.removeById(id) ? 1 : 0;
  }

  /**
   * 更新合同状态
   */
  @Override
  public void updateContractStatus() {
    // 更新合同表
    // 未生效:  status = 0, 且:   过去   signDate --- CurrentDate 将来
    LambdaQueryWrapper<Contract> wrapper = Wrappers.lambdaQuery();
    wrapper.eq(Contract::getStatus, StatusConstants.CONTRACT_PENDING)
        .le(Contract::getSignDate, LocalDate.now());
    List<Contract> contractList = this.list(wrapper);

    contractList.forEach(c -> c.setStatus(StatusConstants.CONTRACT_ACTIVE));

    this.updateBatchById(contractList);

  }
}
