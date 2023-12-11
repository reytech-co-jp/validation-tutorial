package com.validationtutorial.task2;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ProductRequestTest {
    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @ParameterizedTest
    @CsvSource({
            "2, Electronics, 1",
            "2, Clothing, 1",
            "20, Books, 1000000",
    })
    public void 有効なproductNameとcategoryとprice場合はバリデーションエラーとならないこと(String productNameCount, String category, String priceCount) {
        ProductRequest productRequest = new ProductRequest(
                "p".repeat(Integer.valueOf(productNameCount)),
                category,
                Integer.valueOf(priceCount)
        );
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).isEmpty();
    }

    @Test
    public void productNameがnullのときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest(null, "Books", 100);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("productName", "商品名を入力してください")
                );
    }

    @Test
    public void productNameが空文字のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("", "Books", 100);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("productName", "商品名を入力してください"),
                        tuple("productName", "商品名は2文字以上20文字以下である必要があります")
                );
    }

    @Test
    public void productNameが半角スペースのときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest(" ", "Books", 100);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("productName", "商品名を入力してください"),
                        tuple("productName", "商品名は2文字以上20文字以下である必要があります")
                );
    }

    @Test
    public void productNameが2文字未満のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("p", "Books", 100);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("productName", "商品名は2文字以上20文字以下である必要があります")
                );
    }

    @Test
    public void productNameが20文字を超えるときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("p".repeat(21), "Books", 100);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("productName", "商品名は2文字以上20文字以下である必要があります")
                );
    }

    @Test
    public void categoryがnullのときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", null, 100);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("category", "カテゴリを入力してください"),
                        tuple("category", "無効なカテゴリです")
                );
    }

    @Test
    public void categoryが空文字のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "", 100);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("category", "カテゴリを入力してください"),
                        tuple("category", "無効なカテゴリです")
                );
    }

    @Test
    public void categoryが半角スペースのときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", " ", 100);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("category", "カテゴリを入力してください"),
                        tuple("category", "無効なカテゴリです")
                );
    }

    @Test
    public void categoryが無効なもののときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Foods", 100);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("category", "無効なカテゴリです")
                );
    }

    @Test
    public void priceがnullのときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", null);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("price", "価格を入力してください")
                );
    }

    @Test
    public void priceが0以下のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 0);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("price", "価格は0より大きい値である必要があります")
                );
    }

    @Test
    public void priceが1000000を超えるときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 1000001);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("price", "価格は1000000以下である必要があります")
                );
    }
}
