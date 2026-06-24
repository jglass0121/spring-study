package toby.spring.splearn.adapter.webapi;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import toby.spring.splearn.adapter.webapi.dto.MemberRegisterResponse;
import toby.spring.splearn.application.member.provided.MemberRegister;
import toby.spring.splearn.domain.member.Member;
import toby.spring.splearn.domain.member.MemberRegisterRequest;

@RestController
@RequiredArgsConstructor
public class MemberApi {
    private final MemberRegister memberRegister;

    //register api -> /members POST
    @PostMapping("/api/members")
    public MemberRegisterResponse register(@RequestBody @Valid MemberRegisterRequest request) {
        Member member = memberRegister.register(request);

        return MemberRegisterResponse.of(member);
    }



}
