package com.zreview.api.domain.member.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class LoginMemberRequest {

    private String email;
    private String password;
}
