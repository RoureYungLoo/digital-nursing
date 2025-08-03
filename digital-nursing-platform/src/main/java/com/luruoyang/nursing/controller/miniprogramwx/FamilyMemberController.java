package com.luruoyang.nursing.controller.miniprogramwx;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.luruoyang.common.core.domain.R;
import com.luruoyang.nursing.entity.dto.WxLoginDto;
import com.luruoyang.nursing.entity.vo.WxLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luruoyang.common.annotation.Log;
import com.luruoyang.common.core.controller.BaseController;
import com.luruoyang.common.core.domain.AjaxResult;
import com.luruoyang.common.enums.BusinessType;
import com.luruoyang.nursing.entity.domain.FamilyMember;
import com.luruoyang.nursing.service.IFamilyMemberService;
import com.luruoyang.common.utils.poi.ExcelUtil;
import com.luruoyang.common.core.page.TableDataInfo;

/**
 * 老人家属Controller
 *
 * @author luruoyang
 * @date 2025-07-31
 */
@RestController
@RequestMapping("/member/user")
@Api(tags = "老人家属相关接口")
public class FamilyMemberController extends BaseController {
  @Autowired
  private IFamilyMemberService familyMemberService;

  /**
   * 查询老人家属列表
   */
  @PreAuthorize("@ss.hasPermi('member:user:list')")
  @GetMapping("/list")
  @ApiOperation("查询老人家属列表")
  public TableDataInfo<FamilyMember> list(FamilyMember familyMember) {
    startPage();
    List<FamilyMember> list = familyMemberService.selectFamilyMemberList(familyMember);
    return getDataTable(list);
  }

  /**
   * 导出老人家属列表
   */
  @PreAuthorize("@ss.hasPermi('member:user:export')")
  @Log(title = "老人家属", businessType = BusinessType.EXPORT)
  @PostMapping("/export")
  @ApiOperation("导出老人家属列表")
  public void export(HttpServletResponse response, FamilyMember familyMember) {
    List<FamilyMember> list = familyMemberService.selectFamilyMemberList(familyMember);
    ExcelUtil<FamilyMember> util = new ExcelUtil<FamilyMember>(FamilyMember.class);
    util.exportExcel(response, list, "老人家属数据");
  }

  /**
   * 获取老人家属详细信息
   */
  @PreAuthorize("@ss.hasPermi('member:user:query')")
  @GetMapping(value = "/{id}")
  @ApiOperation("获取老人家属详细信息")
  public R<FamilyMember> getInfo(@ApiParam(value = "老人家属ID", required = true) @PathVariable("id") Long id) {
    return R.ok(familyMemberService.selectFamilyMemberById(id));
  }

  /**
   * 新增老人家属
   */
  @PreAuthorize("@ss.hasPermi('member:user:add')")
  @Log(title = "老人家属", businessType = BusinessType.INSERT)
  @PostMapping
  @ApiOperation("新增老人家属")
  public AjaxResult add(@ApiParam(value = "老人家属实体", required = true) @RequestBody FamilyMember familyMember) {
    return toAjax(familyMemberService.insertFamilyMember(familyMember));
  }

  /**
   * 修改老人家属
   */
  @PreAuthorize("@ss.hasPermi('member:user:edit')")
  @Log(title = "老人家属", businessType = BusinessType.UPDATE)
  @PutMapping
  @ApiOperation("修改老人家属")
  public AjaxResult edit(@ApiParam(required = true, value = "老人家属实体") @RequestBody FamilyMember familyMember) {
    return toAjax(familyMemberService.updateFamilyMember(familyMember));
  }

  /**
   * 删除老人家属
   */
  @PreAuthorize("@ss.hasPermi('member:user:remove')")
  @Log(title = "老人家属", businessType = BusinessType.DELETE)
  @DeleteMapping("/{ids}")
  @ApiOperation("删除老人家属")
  public AjaxResult remove(@PathVariable Long[] ids) {
    return toAjax(familyMemberService.deleteFamilyMemberByIds(ids));
  }

  @ApiOperation("小程序用户登录")
  @PostMapping("/login")
  public AjaxResult login(@RequestBody WxLoginDto loginDto) {
    WxLoginVo loginVo = familyMemberService.login(loginDto);
    return success(loginVo);
  }
}
