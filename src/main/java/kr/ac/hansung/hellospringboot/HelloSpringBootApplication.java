package kr.ac.hansung.hellospringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 애플리케이션 진입점
 *
 * @SpringBootApplication 은 세 가지 어노테이션의 복합체:
 *
 *   1. @SpringBootConfiguration
 *      - @Configuration 의 특수화 버전
 *      - 이 클래스가 Spring IoC 컨테이너에 Bean 정의를 제공하는 설정 클래스임을 선언
 *
 *   2. @EnableAutoConfiguration
 *      - 클래스패스에 존재하는 JAR, 빈 정의, 프로퍼티 설정을 기반으로
 *        Spring Boot가 필요한 Bean을 자동으로 등록(Auto Configuration) 하도록 활성화
 *      - 예: spring-boot-starter-data-jpa 가 있으면 DataSource, EntityManagerFactory,
 *            TransactionManager Bean이 자동 생성됨
 *
 *   3. @ComponentScan
 *      - 현재 패키지(kr.ac.hansung.hellospringboot) 및 하위 패키지를 스캔하여
 *        @Component, @Service, @Repository, @Controller 어노테이션이 붙은 클래스를
 *        Spring Bean으로 자동 등록
 */
@SpringBootApplication
public class HelloSpringBootApplication {

    public static void main(String[] args) {
        // SpringApplication.run(): IoC 컨테이너(ApplicationContext) 초기화 후 내장 Tomcat 시작
        SpringApplication.run(HelloSpringBootApplication.class, args);
    }
}
