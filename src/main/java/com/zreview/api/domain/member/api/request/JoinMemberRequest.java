package com.zreview.api.domain.member.api.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class JoinMemberRequest {

    private String email;
    private String password;
    private String phone;
    private String name;
    private String nickname;
}
