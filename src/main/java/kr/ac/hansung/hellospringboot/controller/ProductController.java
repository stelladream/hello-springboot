package kr.ac.hansung.hellospringboot.controller;

import kr.ac.hansung.hellospringboot.model.Product;
import kr.ac.hansung.hellospringboot.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ProductController - 레이어드 아키텍처의 Presentation(Controller) 계층
 *
 * @RestController: @Controller + @ResponseBody 복합 어노테이션
 *   - 모든 메서드의 반환값을 HTTP 응답 본문(JSON)으로 직렬화
 *   - Jackson ObjectMapper가 자동으로 Java 객체 ↔ JSON 변환 처리
 *
 * @RequestMapping("/api/products"): 이 컨트롤러의 모든 엔드포인트에 기본 경로 적용
 *
 * ResponseEntity<T> 사용 이유:
 *   - HTTP 상태코드, 응답 헤더, 응답 본문을 명시적으로 제어 가능
 *   - 단순 객체 반환 시 항상 200 OK가 반환되지만,
 *     ResponseEntity를 사용하면 201 Created, 404 Not Found 등 의미에 맞는 상태코드 반환 가능
 *   - RESTful API 설계 원칙에 부합하는 응답 구성 가능
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    // 생성자 주입: 단일 생성자이므로 @Autowired 생략 가능 (Spring 4.3+)
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * GET /api/products - 전체 상품 목록 조회
     *
     * HTTP 200 OK: 목록이 비어있어도 성공 응답 (빈 배열 [] 반환)
     * @GetMapping: HTTP GET 메서드에 매핑
     */
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products); // 200 OK
    }

    /**
     * GET /api/products/{id} - 단일 상품 조회
     *
     * @PathVariable: URL 경로의 {id} 부분을 메서드 파라미터로 바인딩
     * HTTP 200 OK: 상품 존재 시
     * HTTP 404 Not Found: 상품 없을 시
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)                          // 200 OK + 상품 데이터
                .orElse(ResponseEntity.notFound().build());       // 404 Not Found
    }

    /**
     * POST /api/products - 새 상품 등록
     *
     * @RequestBody: HTTP 요청 본문(JSON)을 Product 객체로 역직렬화
     * HTTP 201 Created: 리소스 생성 성공 시 반환 (단순 200 OK보다 의미가 명확)
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct); // 201 Created
    }

    /**
     * PUT /api/products/{id} - 상품 전체 수정
     *
     * PUT은 리소스 전체를 교체하는 의미 (부분 수정은 PATCH 사용)
     * HTTP 200 OK: 수정 성공 시 수정된 상품 반환
     * HTTP 404 Not Found: 수정 대상 상품 없을 시
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,
                                                  @RequestBody Product productDetails) {
        return productService.updateProduct(id, productDetails)
                .map(ResponseEntity::ok)                          // 200 OK + 수정된 데이터
                .orElse(ResponseEntity.notFound().build());       // 404 Not Found
    }

    /**
     * DELETE /api/products/{id} - 상품 삭제
     *
     * HTTP 204 No Content: 삭제 성공 시 (응답 본문 없음 - RESTful 관례)
     * HTTP 404 Not Found: 삭제 대상 상품 없을 시
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();            // 204 No Content
        }
        return ResponseEntity.notFound().build();                  // 404 Not Found
    }
}
