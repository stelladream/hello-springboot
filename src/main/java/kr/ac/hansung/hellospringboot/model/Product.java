package kr.ac.hansung.hellospringboot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Product 엔티티 - products 테이블과 매핑되는 JPA 도메인 객체
 *
 * @Entity  : Hibernate가 관리하는 영속성 객체임을 선언
 * @Table   : 매핑 테이블 이름 명시 (생략 시 클래스명 사용)
 *
 * Lombok 선택 이유:
 *   @Data 대신 @Getter + @Setter + @ToString 을 분리 사용.
 *   @Data 는 equals/hashCode 를 모든 필드 기반으로 생성하는데,
 *   JPA 엔티티에서는 id 기반 동등성이 필요하고 Hibernate 프록시와
 *   충돌할 수 있어 권장하지 않음.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(columnDefinition = "TEXT")
    private String description;
}
