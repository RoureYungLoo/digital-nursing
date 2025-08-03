package com.luruoyang.framework.interceptor;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.luruoyang.common.constant.HttpStatus;
import com.luruoyang.common.exception.base.BaseException;
import com.luruoyang.common.utils.StringUtils;
import com.luruoyang.common.utils.UserThreadLocal;
import com.luruoyang.framework.web.service.TokenService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author luruoyang
 */
@Component
public class WxUserInterceptor implements HandlerInterceptor {

  @Autowired
  private TokenService tokenService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = request.getHeader("authorization");
    if (StringUtils.isEmpty(token)) {
      response.setStatus(HttpStatus.UNAUTHORIZED);
      throw new BaseException("认证失败 - token is empty");
    }

    Claims claims = null;
    try {
      claims = tokenService.parseToken(token);
    } catch (Exception e) {
      response.setStatus(HttpStatus.UNAUTHORIZED);
      throw new BaseException("认证失败 - token parse fail");
    }

    if (MapUtil.isEmpty(claims)) {
      response.setStatus(HttpStatus.UNAUTHORIZED);
      throw new BaseException("认证失败 - claims is empty");
    }

    Long userId = MapUtil.get(claims, "userId", Long.class);
    if (ObjectUtil.isEmpty(userId)) {
      response.setStatus(HttpStatus.UNAUTHORIZED);
      throw new BaseException("认证失败 - no userId ");
    }

    UserThreadLocal.set(userId);

    return true;
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    UserThreadLocal.remove();
  }
}
