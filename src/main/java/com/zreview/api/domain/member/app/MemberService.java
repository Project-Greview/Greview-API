package com.zreview.api.domain.member.app;

import com.zreview.api.domain.member.api.request.JoinMemberRequest;
import com.zreview.api.domain.member.api.request.LoginMemberRequest;
import com.zreview.api.domain.member.dao.MemberRepository;
import com.zreview.api.domain.member.model.Member;
import com.zreview.api.global.security.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public void join(final JoinMemberRequest request) {
        Member member = Member.newMember(
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                request.getPhone(),
                request.getName(),
                request.getNickname());

        memberRepository.save(member);
    }

    public String login(LoginMemberRequest request) {
        Member member = memberRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("로그인 실패."));

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("로그인 실패.");
        }
        return jwtTokenProvider.createAccessToken(member.getEmail(), member.getNickname());
    }
}
