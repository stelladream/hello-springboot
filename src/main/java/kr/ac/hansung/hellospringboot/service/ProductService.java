package kr.ac.hansung.hellospringboot.service;

import kr.ac.hansung.hellospringboot.model.Product;
import kr.ac.hansung.hellospringboot.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * ProductService - 레이어드 아키텍처의 Service(Business Logic) 계층
 *
 * 역할:
 *   - 비즈니스 규칙 적용 (유효성 검증, 데이터 가공 등)
 *   - 트랜잭션 경계 설정
 *   - Controller와 Repository 사이의 중간 계층
 *
 * @Service: @Component의 특수화 → Spring Bean으로 등록되며 서비스 계층임을 명시
 *
 * 의존성 주입 방식: 생성자 주입(Constructor Injection)
 *   - 필드 주입(@Autowired)보다 권장: 불변성 보장, 테스트 용이성 향상
 *   - Lombok @RequiredArgsConstructor 를 사용하면 생성자를 자동 생성 가능하지만
 *     여기서는 명시적으로 작성하여 학습 목적에 맞춤
 */
@Service
public class ProductService {

    // final 선언: 생성자 주입 후 변경 불가 (불변성 보장)
    private final ProductRepository productRepository;

    // 생성자 주입: Spring이 ProductRepository Bean을 이 생성자를 통해 주입
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * 전체 상품 목록 조회
     * @Transactional(readOnly = true): 읽기 전용 트랜잭션 → 성능 최적화(Dirty Checking 비활성화)
     */
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * ID로 단일 상품 조회
     * Optional<Product> 반환: null 대신 Optional로 감싸 NPE 방지
     */
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    /**
     * 상품 등록
     * save(): 엔티티에 ID가 없으면 INSERT, 있으면 UPDATE(merge) 수행
     */
    @Transactional
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * 상품 수정
     * 존재하지 않는 ID이면 빈 Optional 반환 → Controller에서 404 처리
     */
    @Transactional
    public Optional<Product> updateProduct(Long id, Product productDetails) {
        return productRepository.findById(id).map(existingProduct -> {
            // 기존 엔티티의 필드를 업데이트 (영속성 컨텍스트 내에서 Dirty Checking으로 자동 UPDATE)
            existingProduct.setName(productDetails.getName());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setDescription(productDetails.getDescription());
            return productRepository.save(existingProduct);
        });
    }

    /**
     * 상품 삭제
     * 삭제 성공 여부를 boolean으로 반환 → Controller에서 204/404 분기 처리
     */
    @Transactional
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
