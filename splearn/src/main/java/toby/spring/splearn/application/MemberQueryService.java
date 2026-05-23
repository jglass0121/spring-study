package toby.spring.splearn.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import toby.spring.splearn.application.provided.MemberFinder;
import toby.spring.splearn.application.required.MemberRepository;
import toby.spring.splearn.domain.Member;

@Service
@Transactional(readOnly = true)
@Validated
@RequiredArgsConstructor
public class MemberQueryService implements MemberFinder {
    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(
                () -> new IllegalArgumentException("회원을 찾을 수 없습니다. id :" + memberId));
    }
}
