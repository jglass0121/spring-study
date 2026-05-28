package toby.spring.splearn.domain;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import java.time.LocalDateTime;

import static java.util.Objects.requireNonNull;
import static org.springframework.util.Assert.state;
import static toby.spring.splearn.domain.MemberStatus.*;

//xml은 Annotation 설정을  override한다 = 문법 명확,
// 우선순위 : xml > Annotation
@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberDetail extends AbstractEntity {
    private String profile;
    private LocalDateTime registeredAt;
    private LocalDateTime activatedAt;
    private LocalDateTime deactivateAt;



}
