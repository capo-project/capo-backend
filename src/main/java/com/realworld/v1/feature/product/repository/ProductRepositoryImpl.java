package com.realworld.v1.feature.product.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.realworld.v1.feature.like.entity.QProductLikeJpaEntity;
import com.realworld.v1.feature.member.entity.QMemberJpaEntityV1;
import com.realworld.v1.feature.product.domain.Product;
import com.realworld.v1.feature.product.entity.ProductJpaEntity;
import com.realworld.v1.feature.product.entity.QProductJpaEntity;
import com.realworld.v1.global.category.GroupCategory;
import com.realworld.v1.global.code.ErrorCode;
import com.realworld.v1.global.config.exception.CustomProductExceptionHandlerV1;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final QProductJpaEntity product = QProductJpaEntity.productJpaEntity;
    private final QMemberJpaEntityV1 member = QMemberJpaEntityV1.memberJpaEntityV1;
    private final QProductLikeJpaEntity like = QProductLikeJpaEntity.productLikeJpaEntity;

    @Override
    public List<Product> getSearchProductList(Pageable pageable, String search, GroupCategory category, Long seq) {
        // 좋아요 수는 product에 물려있기 때문에 조인 x likeCount
        List<ProductJpaEntity> products = queryFactory
                .select(product)
                .from(product)
                .innerJoin(product.member, member)
                .where(
                        ltSeq(seq),
                        containTitle(search),
                        eqCategory(category)
                )
                .orderBy(productSort(pageable))
                .limit(pageable.getPageSize() + 1)
                .fetch();
        return products.stream().map(ProductJpaEntity::searchToDomain).toList();
    }

    @Override
    public Product getDetailsProduct(Long seq) {
        ProductJpaEntity details = queryFactory
                .select(product)
                .from(product)
                .innerJoin(product.member, member)
                .where(
                        product.productSeq.eq(seq)
                ).fetchOne();

        if (details == null) {
            throw new CustomProductExceptionHandlerV1(ErrorCode.NOT_EXISTS_PRODUCT);
        }

        return details.searchToDomain();
    }


    private BooleanExpression containTitle(String search) {
        if (search == null || search.isEmpty()) {
            return null;
        }
        return product.title.containsIgnoreCase(search);
    }

    private BooleanExpression eqCategory(GroupCategory category) {
        if (category == null || category.toString().isEmpty()) {
            return null;
        }

        return product.category.eq(GroupCategory.valueOf(category.toString()));
    }

    private BooleanExpression ltSeq(Long seq) {
        if (seq == null) {
            return null; // BooleanExpression 자리에 NULL 반환 시 자동으로 제거된다.
        }
        return product.productSeq.lt(seq);
    }

    private OrderSpecifier<?> productSort(Pageable pageable) {
        if (!pageable.getSort().isEmpty()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                return switch (order.getProperty()) {
                    case "title" -> new OrderSpecifier<>(direction, product.title);
                    case "modifiedAt" -> new OrderSpecifier<>(direction, product.modifiedAt);
                    case "createAt" -> new OrderSpecifier<>(direction, product.createAt);
                    case "views" -> new OrderSpecifier<>(direction, product.views);
                    case "likeCount" -> new OrderSpecifier<>(direction, product.likeCount);
                    default -> new OrderSpecifier<>(direction, product.productSeq);
                };
            }
        }
        return new OrderSpecifier<>(Order.DESC, product.productSeq);
    }
}
