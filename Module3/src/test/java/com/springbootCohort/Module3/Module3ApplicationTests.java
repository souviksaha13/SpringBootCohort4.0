package com.springbootCohort.Module3;

import com.springbootCohort.Module3.entities.ProductEntity;
import com.springbootCohort.Module3.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class Module3ApplicationTests {

    @Autowired
    ProductRepository productRepository;

	@Test
	void contextLoads() {
	}

    @Test
    void testRepository() {
        ProductEntity product = ProductEntity.builder()
                .sku("silkCadbury")
                .title("Cadbury Chocolates")
                .price(BigDecimal.valueOf(123.45))
                .quantity(12)
                .build();
        ProductEntity savedProduct = productRepository.save(product);
        System.out.println(savedProduct);
    }

    @Test
    void getAllProducts() {
        List<ProductEntity> allProducts = productRepository.findAll();
        for(ProductEntity product : allProducts) {
            System.out.println(product);
        }
    }

    //  Custom query methods by simply declaring method names
    @Test
    void customQueryByDeclaring() {
        //  1
        ProductEntity product = productRepository.findByTitle("Coco-Cola");
        System.out.println(product);

        //  2
        List<ProductEntity> entities = productRepository.findByCreatedAtAfter(
                LocalDateTime.of(2025, 1, 7, 0, 0, 0)
        );
        System.out.println(entities);

        //  3
        //  findByPriceGreaterThanAndQuantityLessThan -> finds items where price is greater than given value and quantity is less than given value

        // 4
        //  findByTitleLike("%Coco%") -> finds all titles which contains "Coco" in them
        //  we can also use findByTitleContaining("Coco") -> same as the above one

        //  can't remember all of them -> so we need to visit the documentation when and wherever required
    }
}
