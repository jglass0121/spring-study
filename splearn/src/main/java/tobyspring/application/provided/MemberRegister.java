package tobyspring.application.provided;

import tobyspring.domain.Member;
import tobyspring.domain.MemberRegisterRequest;

/**
 *  회원 등록과 관련된 기능을 제공한다.
 */
public interface MemberRegister {
    Member register(MemberRegisterRequest registerRequest);

}
