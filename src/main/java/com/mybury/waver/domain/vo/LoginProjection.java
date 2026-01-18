package com.mybury.waver.domain.vo;

import com.mybury.waver.common.code.PremiumStatus;
import com.mybury.waver.common.code.YesNo;

public interface LoginProjection {

  Long getId();

  PremiumStatus getPremiumStatus();

  YesNo getDeleteYn();
}
