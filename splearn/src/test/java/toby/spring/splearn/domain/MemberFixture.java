package toby.spring.splearn.domain;

/**
 *
 * util 클래스로
 */
public class MemberFixture {

    public static MemberRegisterRequest createMemberRegisterRequest(String email) {
        return new MemberRegisterRequest(email,"Charlie","longsecret");
    }
    public static MemberRegisterRequest createMemberRegisterRequest() {

        return createMemberRegisterRequest("jac@splear.app");
    }

    public static PasswordEncoder createPasswordEncoder() {
        return new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }

        };
    }

}
