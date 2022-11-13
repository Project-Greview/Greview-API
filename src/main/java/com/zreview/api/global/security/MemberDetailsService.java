package com.zreview.api.global.security;

import com.zreview.api.domain.member.dao.MemberRepository;
import com.zreview.api.domain.member.model.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDetailsService {

    private final MemberRepository memberRepository;

    public MemberDetails loadMemberByEmail(final String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("사용자 정보를 찾을 수 없습니다."));

        return new MemberDetails(member.getEmail(), member.getPassword(), member.getRoles());
    }
}
