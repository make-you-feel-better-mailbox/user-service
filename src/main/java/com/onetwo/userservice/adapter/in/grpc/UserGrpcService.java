package com.onetwo.userservice.adapter.in.grpc;

import com.onetwo.rpc.user.UserGrpc;
import com.onetwo.rpc.user.UserId;
import com.onetwo.rpc.user.UserNickname;
import com.onetwo.userservice.application.port.in.user.usecase.ReadUserUseCase;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
@RequiredArgsConstructor
public class UserGrpcService extends UserGrpc.UserImplBase {

    private final ReadUserUseCase readUserUseCase;

    /**
     * get user nickname grpc inbound adapter
     *
     * @param userId           user id
     * @param responseObserver user nickname
     */
    @Override
    public void getUserNickname(UserId userId, StreamObserver<UserNickname> responseObserver) {
        String userNickname = readUserUseCase.getUserNickName(userId.getUserId());
        UserNickname userNicknameObj = UserNickname.newBuilder().setUserNickname(userNickname).build();

        log.info("grpc server get user nick name - request user id = {}, response user nickname = {}", userId.getUserId(), userNicknameObj.getUserNickname());

        responseObserver.onNext(userNicknameObj);
        responseObserver.onCompleted();
    }
}
