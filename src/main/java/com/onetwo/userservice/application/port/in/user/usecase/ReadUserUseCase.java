package com.onetwo.userservice.application.port.in.user.usecase;

import com.onetwo.userservice.application.port.in.user.response.UserDetailResponseDto;
import com.onetwo.userservice.application.port.in.user.response.UserIdExistCheckDto;
import com.onetwo.userservice.application.port.in.user.response.UserInfoResponseDto;

public interface ReadUserUseCase {

    /**
     * User id exist check use case,
     * user id is unique, so check before register user
     *
     * @param userId userId
     * @return Boolean about user id already exist
     */
    UserIdExistCheckDto userIdExistCheck(String userId);

    /**
     * Get about user detail information use case
     *
     * @param userId userId
     * @return Detail Information about User
     */
    UserDetailResponseDto getUserDetailInfo(String userId);

    /**
     * Get about user information use case
     *
     * @param userId userId
     * @return Information about User
     */
    UserInfoResponseDto getUserInfo(String userId);

    /**
     * Get user nickname use case
     *
     * @param userId userId
     * @return user nickname
     */
    String getUserNickName(String userId);
}
