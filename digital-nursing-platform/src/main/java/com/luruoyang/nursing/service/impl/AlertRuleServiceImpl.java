package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;
    import com.luruoyang.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.AlertRuleMapper;
import com.luruoyang.nursing.entity.domain.AlertRule;
import com.luruoyang.nursing.service.IAlertRuleService;

/**
 * 报警规则Service业务层处理
 *
 * @author luruoyang
 * @date 2025-08-04
 */
@Service
public class AlertRuleServiceImpl extends ServiceImpl<AlertRuleMapper, AlertRule> implements IAlertRuleService {
  @Autowired
  private AlertRuleMapper alertRuleMapper;

  /**
   * 查询报警规则
   *
   * @param id 报警规则主键
   * @return 报警规则
   */
  @Override
  public AlertRule selectAlertRuleById(Long id) {
        return this.getById(id);
  }

  /**
   * 查询报警规则列表
   *
   * @param alertRule 报警规则
   * @return 报警规则
   */
  @Override
  public List<AlertRule> selectAlertRuleList(AlertRule alertRule) {
    return alertRuleMapper.selectAlertRuleList(alertRule);
  }

  /**
   * 新增报警规则
   *
   * @param alertRule 报警规则
   * @return 结果
   */
  @Override
  public int insertAlertRule(AlertRule alertRule) {
                            return this.save(alertRule) ? 1 : 0;
  }

  /**
   * 修改报警规则
   *
   * @param alertRule 报警规则
   * @return 结果
   */
  @Override
  public int updateAlertRule(AlertRule alertRule) {
                        return this.updateById(alertRule) ? 1 : 0;
  }

  /**
   * 批量删除报警规则
   *
   * @param ids 需要删除的报警规则主键
   * @return 结果
   */
  @Override
  public int deleteAlertRuleByIds(Long[] ids) {
        return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除报警规则信息
   *
   * @param id 报警规则主键
   * @return 结果
   */
  @Override
  public int deleteAlertRuleById(Long id) {
        return this.removeById(id) ? 1 : 0;
  }
}
