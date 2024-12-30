package com.realworld.feature.temporarily_product.entity;

import com.realworld.feature.temporarily_product.domain.TemporarilyProductFile;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "temporarily_product_file")
public class TemporarilyProductFileJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long seq;

    @Column(name = "user_id")
    private String userId;

    @ManyToOne
    @JoinColumn(name = "product_seq", referencedColumnName = "product_seq")
    private TemporarilyProductJpaEntity product;

    @Column(name = "file_id")
    private UUID id;

    @CreatedDate
    @Column(name = "create_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    public TemporarilyProductFile toDomain() {
        return TemporarilyProductFile.builder()
                .userId(this.userId)
                .id(this.id)
                .product(this.product.toDomain())
                .createAt(this.createAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

    public TemporarilyProductFile generationToDomain() {
        return TemporarilyProductFile.builder()
                .userId(this.userId)
                .id(this.id)
                .build();
    }

    public TemporarilyProductFile updateToDomain() {
        return TemporarilyProductFile.builder()
                .userId(this.userId)
                .id(this.id)
                .createAt(this.createAt)
                .modifiedAt(this.modifiedAt)
                .build();
    }

    public TemporarilyProductFile searchToDomain() {
        return TemporarilyProductFile.builder()
                .userId(this.userId)
                .id(this.id)
                .build();
    }
}
