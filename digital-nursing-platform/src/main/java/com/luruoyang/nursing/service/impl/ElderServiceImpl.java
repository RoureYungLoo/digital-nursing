package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;
    import com.luruoyang.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.ElderMapper;
import com.luruoyang.nursing.domain.Elder;
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
}
