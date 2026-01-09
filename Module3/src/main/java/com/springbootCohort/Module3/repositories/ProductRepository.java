package com.springbootCohort.Module3.repositories;

import com.springbootCohort.Module3.entities.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByTitle(String title);

    List<ProductEntity> findByCreatedAtAfter(LocalDateTime createdAt);

    @Query("select p from ProductEntity p where p.title=?1 and p.price=?2")
    Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);

    // or we can also write
    @Query("select p from ProductEntity p where p.title=:title or p.price=:price")
    Optional<ProductEntity> findByTitleOrPrice(String title, BigDecimal price);

    //  ----------------------------------------------------------------------------------

    List<ProductEntity> findByOrderByPrice();
    //  but there is a issue -> for every field, we need to create a new method (like for Price, Quantity, etc, etc)

    //  so we can use the following:
    List<ProductEntity> findBy(Sort sort);
}
