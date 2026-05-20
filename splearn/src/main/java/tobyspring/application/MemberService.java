package tobyspring.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tobyspring.application.provided.MemberRegister;
import tobyspring.application.required.EmailSender;
import tobyspring.application.required.MemberRepository;
import tobyspring.domain.Member;
import tobyspring.domain.MemberRegisterRequest;
import tobyspring.domain.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {

    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {
        // check

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        emailSender.send(member.getEmail(), "등록", "아래 링크를 통해 완료해주세여");

        return member;
    }
}
