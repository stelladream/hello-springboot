package kr.ac.hansung.hellospringboot.repository;

import kr.ac.hansung.hellospringboot.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * ProductRepository - 레이어드 아키텍처의 Repository(Data Access) 계층
 *
 * JpaRepository<Product, Long> 상속:
 *   - Spring Data JPA가 런타임에 구현체를 자동 생성 (프록시 패턴)
 *   - 기본 제공 메서드: findAll(), findById(), save(), deleteById(), count() 등
 *   - 제네릭 파라미터: <엔티티 타입, PK 타입>
 *
 * @Repository: Spring Bean 등록 + JPA 예외를 Spring의 DataAccessException으로 변환
 *
 * 쿼리 메서드(Query Method):
 *   - 메서드 이름 규칙만 따르면 Spring Data JPA가 JPQL을 자동 생성
 *   - findByName → SELECT p FROM Product p WHERE p.name = ?1
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // 상품명으로 검색 - 동일한 이름의 상품이 여러 개 있을 수 있으므로 List 반환
    List<Product> findByName(String name);
}
