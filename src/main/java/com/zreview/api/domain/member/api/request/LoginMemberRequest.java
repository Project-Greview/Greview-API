package com.zreview.api.domain.member.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginMemberRequest {

    private final String email;
    private final String password;
}
