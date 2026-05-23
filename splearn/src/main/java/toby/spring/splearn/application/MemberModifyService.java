package toby.spring.splearn.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import toby.spring.splearn.application.provided.MemberFinder;
import toby.spring.splearn.application.provided.MemberRegister;
import toby.spring.splearn.application.required.EmailSender;
import toby.spring.splearn.application.required.MemberRepository;
import toby.spring.splearn.domain.*;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
/**
 * 문제 : 조회 변경을 함께 두면 의존하는 오브젝트가 점점 달라지기 시작하여 혼란스럽더
 * 해결 : 조회와 변경을 각각 분리
 */
public class MemberModifyService implements MemberRegister {

    private final MemberFinder memberFinder;
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest registerRequest) {

        checkDuplicateEmail(registerRequest);

        Member member = Member.register(registerRequest, passwordEncoder);

        memberRepository.save(member);

        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        Member member = memberFinder.find(memberId); //필요한 조회기능을 가져와서 사용

        member.activate();

        return memberRepository.save(member);
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록", "아래 링크를 통해 완료해주세여");
    }

    private void checkDuplicateEmail(MemberRegisterRequest registerRequest) {
        if (memberRepository.findByEmail(new Email(registerRequest.email())).isPresent()) {
            throw new DuplicateEmailException("이미 사용중인 이메일입니다. : " + registerRequest.email());
        }
    }


}
