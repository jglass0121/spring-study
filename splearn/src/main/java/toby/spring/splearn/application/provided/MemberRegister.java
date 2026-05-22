package toby.spring.splearn.application.provided;

import jakarta.validation.Valid;
import toby.spring.splearn.domain.MemberRegisterRequest;
import toby.spring.splearn.domain.Member;

/**
 *  회원 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest registerRequest);

}
