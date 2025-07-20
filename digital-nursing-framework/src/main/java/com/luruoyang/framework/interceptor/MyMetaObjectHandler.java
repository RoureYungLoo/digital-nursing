package com.luruoyang.framework.interceptor;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.luruoyang.common.core.domain.model.LoginUser;
import com.luruoyang.common.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {


  @Override
  public void insertFill(MetaObject metaObject) {
    strictInsertFill(metaObject, "createTime", Date.class, new Date());
    strictInsertFill(metaObject, "createBy", String.class, String.valueOf(getLoginUserId()));
  }

  @Override
  public void updateFill(MetaObject metaObject) {
    // strictUpdateFill(metaObject, "updateTime", Date.class, new Date());
    // strictUpdateFill(metaObject, "updateBy", String.class, String.valueOf(getLoginUserId()));
    this.setFieldValByName("updateTime", new Date(), metaObject);
    this.setFieldValByName("updateBy", String.valueOf(getLoginUserId()), metaObject);
  }

  private Long getLoginUserId() {
    LoginUser user = SecurityUtils.getLoginUser();
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
