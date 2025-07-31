package com.luruoyang.framework.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.luruoyang.common.core.domain.model.LoginUser;
import com.luruoyang.common.utils.SecurityUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {


  @Autowired
  private HttpServletRequest request;

  @SneakyThrows
  public boolean isExclude() {
    String requestUri = request.getRequestURI();
    if (requestUri.startsWith("/member")) {
      return true;
    }
    return false;
  }

  @Override
  public void insertFill(MetaObject metaObject) {
    strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
    if (!isExclude()) {
      this.strictInsertFill(metaObject, "createBy", String.class, getLoginUserId() + "");
    }
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    // strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    // strictUpdateFill(metaObject, "updateBy", String.class, String.valueOf(getLoginUserId()));
    this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
    if (!isExclude()) {
      this.setFieldValByName("updateBy", getLoginUserId() + "", metaObject);
    }
  }

  private Long getLoginUserId() {
    LoginUser user = null;
    try {
      user = SecurityUtils.getLoginUser();
    } catch (Exception e) {
      user = new LoginUser();
      user.setUserId(1L);
      log.warn("获取登录用户失败");
    }
    if (Objects.nonNull(user)) {
      Long userId = user.getUserId();
      log.warn("已登录用户的ID:{}", userId);
      return userId;
    } else {
      log.warn("没有已登录用户");
      return 1L;
    }
  }
}
