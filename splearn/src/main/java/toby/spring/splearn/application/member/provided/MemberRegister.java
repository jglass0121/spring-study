package toby.spring.splearn.application.member.provided;

import jakarta.validation.Valid;
import toby.spring.splearn.domain.member.Member;
import toby.spring.splearn.domain.member.MemberInfoUpdateRequest;
import toby.spring.splearn.domain.member.MemberRegisterRequest;

/**
 *  회원 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest registerRequest);

    Member activate(Long memberId);

    Member deactivate(Long memberId);

    Member updateInfo(Long memberId, @Valid MemberInfoUpdateRequest memberInfoupdateRequest);

}
