package com.zreview.api.domain.member.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class JoinMemberRequest {

    private final String email;
    private final String password;
    private final String phone;
    private final String name;
    private final String nickname;
}
