package toby.spring.splearn.domain.member;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;
import toby.spring.splearn.domain.AbstractEntity;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;

//xml은 Annotation 설정을  override한다 = 문법 명확,
// 우선순위 : xml > Annotation
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {
    // 조건을 가진 값이라면 VO로 만드는 것도 좋다
    @Embedded
    private Profile profile;

    private String introduction;

    //member가 등록될 때 같이 사용
    private LocalDateTime registeredAt;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivateAt;

    //default 로 함으로써 같은 패키지만 접근할 수 있도록 한다
    static MemberDetail create() {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.registeredAt = LocalDateTime.now();
        return memberDetail;
    }

     void activated() {
        Assert.isTrue(activatedAt == null, "이미 activatedAt은 설정되었습니다.");
        this.activatedAt = LocalDateTime.now();
    }

     void deactivate() {
        Assert.isTrue(deactivateAt == null, "이미 deactivatedAt은 설정되었습니다.");

        this.deactivateAt = LocalDateTime.now();
    }

    void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.profile = new Profile(updateRequest.profileAddress());
        this.introduction = Objects.requireNonNull(updateRequest.introduction());
    }
}
