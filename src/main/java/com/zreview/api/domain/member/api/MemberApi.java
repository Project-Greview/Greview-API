package com.zreview.api.domain.member.api;

import com.zreview.api.domain.member.api.request.JoinMemberRequest;
import com.zreview.api.domain.member.api.request.LoginMemberRequest;
import com.zreview.api.domain.member.app.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberApi {

    private final MemberService memberService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody final JoinMemberRequest request) {
        memberService.join(request);
        return ResponseEntity.ok(null);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody final LoginMemberRequest request) {
        String token = memberService.login(request);
        return ResponseEntity.ok(token);
    }

    @GetMapping
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().build();
    }
}
