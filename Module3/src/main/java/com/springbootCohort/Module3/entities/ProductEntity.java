package com.springbootCohort.Module3.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table( // this annotation is to further customize the table in the DB as per our needs
        name = "Product",// we can define any custom name
        uniqueConstraints = {
                @UniqueConstraint(name = "sku_unique", columnNames = {"sku"}),   //  all the skus are unique now
                @UniqueConstraint(name = "title_price_unique", columnNames = {"title", "price"})    //  no two items whose title and price are same are allowed
        },
        indexes = {
                //  index on id is auto-generated, all other custom indexes can be added here
                @Index(name = "sku_index", columnList = "sku")
        }
)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //  we can custom-define the names of the columns, we can restrict that if a certain column can't be null and we can define the max length of the entry in that column
    //  more of the annotations can be seen in the @Column class
    @Column(name = "sku", nullable = false, length = 20)
    String sku;

    String title;

    BigDecimal price;

    Integer quantity;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;
}
