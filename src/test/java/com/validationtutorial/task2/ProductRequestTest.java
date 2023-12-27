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
            "2, Electronics, 1, 2",
            "2, Clothing, 1, 2",
            "20, Books, 1000000, 20",
    })
    public void 有効なproductNameとcategoryとpriceとsellerの場合はバリデーションエラーとならないこと(String productNameCount, String category, String priceCount, String sellerCount) {
        ProductRequest productRequest = new ProductRequest(
                "p".repeat(Integer.valueOf(productNameCount)),
                category,
                Integer.valueOf(priceCount),
                "s".repeat(Integer.valueOf(sellerCount))
        );
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).isEmpty();
    }

    @Test
    public void productNameがnullのときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest(null, "Books", 100 , "seller");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("productName", "入力してください")
                );
    }

    @Test
    public void productNameが空文字のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("", "Books", 100, "seller");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("productName", "入力してください"),
                        tuple("productName", "2文字以上20文字以下である必要があります")
                );
    }

    @Test
    public void productNameが半角スペースのときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest(" ", "Books", 100, "seller");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("productName", "入力してください"),
                        tuple("productName", "2文字以上20文字以下である必要があります")
                );
    }

    @Test
    public void productNameが2文字未満のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("p", "Books", 100, "seller");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("productName", "2文字以上20文字以下である必要があります")
                );
    }

    @Test
    public void productNameが20文字を超えるときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("p".repeat(21), "Books", 100, "seller");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("productName", "2文字以上20文字以下である必要があります")
                );
    }

    @Test
    public void categoryがnullのときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", null, 100, "seller");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("category", "入力してください"),
                        tuple("category", "無効なカテゴリです")
                );
    }

    @Test
    public void categoryが空文字のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "", 100, "seller");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("category", "入力してください"),
                        tuple("category", "無効なカテゴリです")
                );
    }

    @Test
    public void categoryが半角スペースのときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", " ", 100, "seller");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(2);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("category", "入力してください"),
                        tuple("category", "無効なカテゴリです")
                );
    }

    @Test
    public void categoryが無効なもののときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Foods", 100, "seller");
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
        ProductRequest productRequest = new ProductRequest("product", "Books", null, "seller");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("price", "入力してください")
                );
    }

    @Test
    public void priceが0以下のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 0, "seller");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("price", "0より大きい値である必要があります")
                );
    }

    @Test
    public void priceが1000000を超えるときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 1000001, "seller");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("price", "1000000以下である必要があります")
                );
    }

    @Test
    public void sellerがnullのときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 100, null);
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("seller", "無効な販売者です")
                );
    }

    @Test
    public void sellerが空文字のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 100, "");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("seller", "無効な販売者です")
                );
    }

    @Test
    public void sellerが半角スペースのときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 100, " ");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("seller", "無効な販売者です")
                );
    }

    @Test
    public void sellerが2文字未満のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 100, "s");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("seller", "無効な販売者です")
                );
    }

    @Test
    public void sellerが20文字を超えるときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 100, "s".repeat(21));
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("seller", "無効な販売者です")
                );
    }

    @Test
    public void sellerが1文字のサロゲートペアの時バリデーションエラーとならないこと() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 100, "𠮷");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).isEmpty();
    }

    @Test
    public void sellerが1文字の絵文字の時バリデーションエラーとならないこと() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 100, "😊");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).isEmpty();
    }

    @Test
    public void sellerが19文字の通常文字と1文字のサロゲートペアの漢字のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 100, "s".repeat(19) + "𠮷");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("seller", "無効な販売者です")
                );
    }

    @Test
    public void sellerが19文字の通常文字と1文字の絵文字のときにバリデーションエラーとなること() {
        ProductRequest productRequest = new ProductRequest("product", "Books", 100, "s".repeat(19) + "😊");
        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(productRequest);
        assertThat(violations).hasSize(1);
        assertThat(violations)
                .extracting(violation -> violation.getPropertyPath().toString(), ConstraintViolation::getMessage)
                .containsExactlyInAnyOrder(
                        tuple("seller", "無効な販売者です")
                );
    }
}
