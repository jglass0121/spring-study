package toby.spring.splearn.application.member.required;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import toby.spring.splearn.domain.member.Profile;
import toby.spring.splearn.domain.shared.Email;
import toby.spring.splearn.domain.member.Member;

import java.util.Optional;

/**
 * 회원 정보를 저장하거나 조회한다.
 */
public interface MemberRepository extends Repository<Member,Long> {

    Member save(Member member);

    Optional<Member> findByEmail(Email email);

    Optional<Member> findById(Long memberId);

    @Query("SELECT m FROM Member m where m.detail.profile = :profile")
    Optional<Member> findByProfile(@Param("profile") Profile profile);
}

