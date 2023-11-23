package com.validationtutorial.task1;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class UserRequestTest {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void usernameがnullのときにバリデーションエラーとなること() {
        UserRequest userRequest = new UserRequest(null, "password", "password");
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("username", "ユーザー名を入力してください")
                );
    }

    @Test
    public void passwordがnullのときにバリデーションエラーとなること() {
        UserRequest userRequest = new UserRequest("user", null, "password");
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("password", "パスワードを入力してください"),
                        tuple("passwordMatching", "パスワードと確認用パスワードが一致していません")
                );
    }

    @Test
    public void passwordとconfirmPasswordが一致しないときにバリデーションエラーとなること() {
        UserRequest userRequest = new UserRequest("user", "password", "falsepassword");
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("passwordMatching", "パスワードと確認用パスワードが一致していません")
                );
    }

    @Test
    public void usernameが2文字以下のときにバリデーションエラーとなること() {
        UserRequest userRequest = new UserRequest("us", "password", "password");
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("username", "ユーザー名は3文字以上20文字以下である必要があります")
                );
    }

    @Test
    public void usernameが21文字以上のときにバリデーションエラーとなること() {
        UserRequest userRequest = new UserRequest("u".repeat(21), "password", "password");
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("username", "ユーザー名は3文字以上20文字以下である必要があります")
                );
    }

    @Test
    public void passwordが7文字以下のときにバリデーションエラーとなること() {
        UserRequest userRequest = new UserRequest("user", "9".repeat(31), "9".repeat(31));
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("password", "パスワードは8文字以上30文字以下である必要があります")
                );
    }

    @Test
    public void passwordが31文字以のときにバリデーションエラーとなること() {
        UserRequest userRequest = new UserRequest("user", "passwor", "passwor");
        Set<ConstraintViolation<UserRequest>> violations = validator.validate(userRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("password", "パスワードは8文字以上30文字以下である必要があります")
                );
    }
}
