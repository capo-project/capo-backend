package com.realworld.feature.product.entity;

import com.realworld.feature.product.domain.ProductFile;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "product_file")
@EntityListeners(AuditingEntityListener.class)
public class ProductFileJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long seq;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "product_seq", referencedColumnName = "product_seq")
    private ProductJpaEntity product;

    @Column(name = "file_id")
    private UUID id;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public ProductFile toDomain() {
        return ProductFile.builder()
                .userId(this.userId)
                .id(this.id)
                .product(this.product.toDomain())
                .createAt(this.createAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

    public ProductFile generationToDomain() {
        return ProductFile.builder()
                .userId(this.userId)
                .id(this.id)
                .build();
    }

    public ProductFile updateToDomain() {
        return ProductFile.builder()
                .userId(this.userId)
                .id(this.id)
                .createAt(this.createAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

    public ProductFile searchToDomain() {
        return ProductFile.builder()
                .userId(this.userId)
                .id(this.id)
                .build();
    }

}
