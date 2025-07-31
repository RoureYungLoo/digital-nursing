package com.luruoyang.nursing.service.impl;

import java.util.Arrays;
import java.util.List;
    import com.luruoyang.common.utils.DateUtils;
import com.luruoyang.nursing.entity.dto.WxLoginDto;
import com.luruoyang.nursing.entity.vo.WxLoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.luruoyang.nursing.mapper.FamilyMemberMapper;
import com.luruoyang.nursing.entity.domain.FamilyMember;
import com.luruoyang.nursing.service.IFamilyMemberService;

/**
 * 老人家属Service业务层处理
 *
 * @author luruoyang
 * @date 2025-07-31
 */
@Service
public class FamilyMemberServiceImpl extends ServiceImpl<FamilyMemberMapper, FamilyMember> implements IFamilyMemberService {
  @Autowired
  private FamilyMemberMapper familyMemberMapper;

  /**
   * 查询老人家属
   *
   * @param id 老人家属主键
   * @return 老人家属
   */
  @Override
  public FamilyMember selectFamilyMemberById(Long id) {
        return this.getById(id);
  }

  /**
   * 查询老人家属列表
   *
   * @param familyMember 老人家属
   * @return 老人家属
   */
  @Override
  public List<FamilyMember> selectFamilyMemberList(FamilyMember familyMember) {
    return familyMemberMapper.selectFamilyMemberList(familyMember);
  }

  /**
   * 新增老人家属
   *
   * @param familyMember 老人家属
   * @return 结果
   */
  @Override
  public int insertFamilyMember(FamilyMember familyMember) {
                            return this.save(familyMember) ? 1 : 0;
  }

  /**
   * 修改老人家属
   *
   * @param familyMember 老人家属
   * @return 结果
   */
  @Override
  public int updateFamilyMember(FamilyMember familyMember) {
                        return this.updateById(familyMember) ? 1 : 0;
  }

  /**
   * 批量删除老人家属
   *
   * @param ids 需要删除的老人家属主键
   * @return 结果
   */
  @Override
  public int deleteFamilyMemberByIds(Long[] ids) {
        return this.removeByIds(Arrays.asList(ids)) ? 1 : 0;

  }

  /**
   * 删除老人家属信息
   *
   * @param id 老人家属主键
   * @return 结果
   */
  @Override
  public int deleteFamilyMemberById(Long id) {
        return this.removeById(id) ? 1 : 0;
  }

  @Override
  public WxLoginVo login(WxLoginDto loginDto) {
    return null;
  }
}
