package com.mybury.waver.aspect;

import com.mybury.waver.annotation.Public;
import com.mybury.waver.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthGuardAspect {

  private final JwtTokenProvider jwtTokenProvider;

  @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
  public void restController() {
  }

  @Around("restController()")
  public Object checkTokenIfRequired(ProceedingJoinPoint joinPoint) throws Throwable {
    HttpServletRequest request = ((ServletRequestAttributes)
        Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

    if (!request.getRequestURI().startsWith("/waver")) {
      return joinPoint.proceed();
    }

    Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
    Class<?> clazz = method.getDeclaringClass();

    boolean isPublic = method.isAnnotationPresent(Public.class) || clazz.isAnnotationPresent(Public.class);

    if (!isPublic) {
      String token = jwtTokenProvider.extractToken(request.getHeader("Authorization"));
      jwtTokenProvider.getUserIdFromToken(token);
    }

    return joinPoint.proceed();
  }
}
