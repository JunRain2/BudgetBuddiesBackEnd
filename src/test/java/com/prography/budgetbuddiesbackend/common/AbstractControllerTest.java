package com.prography.budgetbuddiesbackend.common;

import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestExecutionListeners;

@IntegrationTest
@TestExecutionListeners(
    value = FlywayTestExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// 포트 충돌 없이 안정적으로 테스트를 수행하기 위해 랜덤으로 지정
public abstract class AbstractControllerTest {

    @LocalServerPort // 실제 할당된 포트 번호를 테스트 내에 주입
    protected int port;

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = port; // 동적으로 할당된 포트를 반영
        RestAssured.baseURI = "http://localhost";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails(); // 검증이 실패하면 자동으로 요청 및 응답 내용을 콘솔에 출력
    }
}
