package com.luruoyang.nursing.controller.miniprogramwx;

import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.luruoyang.common.core.controller.BaseController;
import com.luruoyang.common.core.domain.R;
import com.luruoyang.common.core.page.TableDataInfo;
import com.luruoyang.nursing.entity.domain.NursingItem;
import com.luruoyang.nursing.entity.dto.NursingItemDto;
import com.luruoyang.nursing.service.MemberOrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author luruoyang
 */
@Api
@RequestMapping("/member/orders")
@RestController
public class MemberOrderController extends BaseController {
  @Autowired
  private MemberOrderService memberOrderService;

  @GetMapping("/project/page")
  public TableDataInfo<List<NursingItem>> wxGetProjectPage(NursingItemDto dto) {
    startPage();
    List<NursingItem> itemList = memberOrderService.wxGetProjectPage(dto);
    return getDataTable(itemList);
  }

  @GetMapping("/project/{projectId:\\d+}")
  public R<NursingItem> wxGetProjectById(@PathVariable("projectId") Long projectId) {
    NursingItem nursingItem = memberOrderService.wxGetProjectById(projectId);
    return R.ok(nursingItem);
  }
}
