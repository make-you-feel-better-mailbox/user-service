package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.service.response.UserDetailResponseDto;
import com.onetwo.userservice.application.service.response.UserIdExistCheckDto;

public interface ReadUserUseCase {

    UserIdExistCheckDto userIdExistCheck(String userId);

    UserDetailResponseDto getUserDetailInfo(String userId);
}
