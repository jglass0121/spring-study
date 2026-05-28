package toby.spring.splearn.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;
import static toby.spring.splearn.domain.MemberStatus.*;

//xml은 Annotation 설정을  override한다 = 문법 명확,
// 우선순위 : xml > Annotation
@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NaturalIdCache // DB에 가지 않고 영속성 컨테스트에서 가져옴
public class Member extends AbstractEntity {
    @NaturalId
    private Email  email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;


    public static  Member register(MemberRegisterRequest createRequest, PasswordEncoder passwordEncoder ){
        Member member = new Member();

        member.email = new Email(createRequest.email());
        member.nickname = requireNonNull(createRequest.nickname());
        member.passwordHash = requireNonNull(passwordEncoder.encode(createRequest.password()));

        member.status = PENDING;

        return member;
    }
    public void activate() {
//        if (status != MemberStatus.PENDING) {
//            throw new IllegalStateException("PENDING 상태가 아닙니다.");
//        }
        // 코드 간결
        state(status == PENDING, "PENDING 상태가 아닙니다.");

        this.status = ACTIVE;
    }

    public void deactivate() {
        state(status == ACTIVE, "ACTIVE 상태가 아닙니다.");

        this.status = DEACTIVATED;

    }

    public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(password,this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = requireNonNull(nickname);
    }

    public void changePassword(String password,PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == ACTIVE;

    }
}
