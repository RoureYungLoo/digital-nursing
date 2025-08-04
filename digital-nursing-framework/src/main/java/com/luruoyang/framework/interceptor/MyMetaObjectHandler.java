package com.luruoyang.framework.interceptor;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.luruoyang.common.core.domain.model.LoginUser;
import com.luruoyang.common.utils.DateTimeZoneConverter;
import com.luruoyang.common.utils.SecurityUtils;
import com.luruoyang.common.utils.UserThreadLocal;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {

  @Autowired
  private HttpServletRequest request;

//  @SneakyThrows
//  public boolean isExclude() {
//    String requestUri = request.getRequestURI();
//    if (requestUri.startsWith("/member")) {
//      log.error("---------> 1 ");
//      return true;
//    }
//    log.error("---------> 2 ");
//    return false;
//  }

  @SneakyThrows
  public boolean isExclude() {
    // 使用Optional简化处理
    return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
        .filter(requestAttributes -> requestAttributes instanceof ServletRequestAttributes)
        .map(requestAttributes -> ((ServletRequestAttributes) requestAttributes).getRequest())
        .map(HttpServletRequest::getRequestURI)
        .filter(uri -> uri.startsWith("/member"))
        .isPresent();
  }

  @Override
  public void insertFill(MetaObject metaObject) {
    strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    if (!isExclude()) {
      this.strictInsertFill(metaObject, "createBy", String.class, getLoginUserId() + "");
    }

    if (isExclude()) {
      log.info("添加小程序用户");
      this.strictInsertFill(metaObject, "createBy", String.class, getLoginUserId() + "");
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    DateTimeZoneConverter.utcToShanghai(LocalDateTime.now());
    // strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    // strictUpdateFill(metaObject, "updateBy", String.class, String.valueOf(getLoginUserId()));
    this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    if (!isExclude()) {
      this.setFieldValByName("updateBy", getLoginUserId() + "", metaObject);
    }
    if (isExclude()) {
      log.info("更新小程序用户");
      this.setFieldValByName("updateBy", getLoginUserId() + "", metaObject);
    }
  }

  private Long getLoginUserId() {
    LoginUser user = null;
    try {
      user = SecurityUtils.getLoginUser();
      if (ObjectUtil.isNotEmpty(user)) {
        Long userId = user.getUserId();
        log.warn("已登录用户的ID:{}", userId);
        return userId;
      }
    } catch (Exception e) {
      log.warn("获取登录用户失败");
      return 1L;
    }
    log.warn("没有已登录用户");
    return 1L;
  }
}
