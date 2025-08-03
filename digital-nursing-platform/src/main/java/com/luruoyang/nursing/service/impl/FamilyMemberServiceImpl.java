package com.luruoyang.nursing.service.impl;

import java.util.*;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.luruoyang.common.exception.base.BaseException;
import com.luruoyang.common.utils.DateUtils;
import com.luruoyang.framework.web.service.TokenService;
import com.luruoyang.nursing.entity.dto.WxLoginDto;
import com.luruoyang.nursing.entity.vo.WxLoginVo;
import com.luruoyang.nursing.wx.properties.WxService;
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

  @Autowired
  private WxService wxService;

  @Autowired
  private TokenService tokenService;

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

  /**
   * 小程序用户登录
   *
   * @param loginDto dto
   * @return loginvo
   */
  @Override
  public WxLoginVo login(WxLoginDto loginDto) {
    //1. 获取openId
    String openId = wxService.fetchOpenId(loginDto);

    //2. 获取手机号
    String phoneNumber = wxService.fetchPhoneNumber(loginDto);

    String name = "家属" + phoneNumber.substring(7, 11);

    //3. 查询家属用户信息
    FamilyMember member;
    List<FamilyMember> memberList = this.list(Wrappers.<FamilyMember>lambdaQuery().eq(FamilyMember::getOpenId, openId));
    if (memberList.size() > 1) {
      throw new BaseException("系统异常 - openId 错误");
    }

    //4. 更新
    if (memberList.size() == 1) {
      member = memberList.get(0);
      member.setPhone(phoneNumber);
      this.updateById(member);
    } else {
      // 新增
      member = FamilyMember.builder()
          .openId(openId)
          .phone(phoneNumber)
          .name(name)
          .build();
      this.save(member);
    }

    //5. 返回登录结果
    Map<String, Object> claims = new HashMap<>();
    claims.put("userId", member.getId());
    claims.put("nickName", name);
    WxLoginVo wxLoginVo = WxLoginVo.builder()
        .nickName(name)
        .token(tokenService.createToken(claims))
        .build();

    return wxLoginVo;
  }
}
