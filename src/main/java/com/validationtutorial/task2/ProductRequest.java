package com.validationtutorial.task2;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ProductRequest {
    @NotBlank(message = "商品名{E0001}")
    @Size(min = 2, max = 20, message = "商品名{E0003}")
    private String productName;

    @NotBlank(message = "カテゴリ{E0001}")
    @ValidCategory
    private String category;

    @NotNull(message = "価格{E0002}")
    @Positive(message = "価格{E0004}")
    @Max(value = 1000000, message = "価格{E0005}")
    private Integer price;

    public ProductRequest(String productName, String category, Integer price) {
        this.productName = productName;
        this.category = category;
        this.price = price;
    }

    public ProductRequest() {
    }

    public String getProductName() {
        return productName;
    }

    public String getCategory() {
        return category;
    }

    public Integer getPrice() {
        return price;
    }
}
