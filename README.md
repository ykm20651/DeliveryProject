# **음식 주문 관리 시스템 - 스프링 예외 처리** 적용하기

<aside>

**음식 주문 관리 시스템에서 발생할 수 있는 다양한 예외 상황에 대비하여, 이번 과제에서는 스프링의 예외 처리를 구현하는 작업을 진행해주세요.**

</aside>

![예외처리는 꼼꼼히 😧](attachment:998f65b4-99b2-4e85-8ac8-4dddcdf973e8:image.png)

예외처리는 꼼꼼히 😧

### **0. Spring 예외 처리 이론 학습하기**

---

- `스프링에서 예외 처리는 왜 필요할까?`
- `스프링 MVC의 기본 예외 처리 메커니즘`
- `주요 애노테이션`
    - `@ControllerAdvice`
    - `@ExceptionHandler`
    - `@ResponseStatus` 등
- `예외를 처리하여 클라이언트에게 응답으로 반환하는 방법`

### **1. Spring 예외 처리 적용 실습**

---

1. **프로젝트 내 예외 상황 점검**
    - 현재 예외를 발생하고 있는 지점을 파악하여 정리하기
    
    ---
    
2. **Global Exception Handler 구현**
    - `@RestControllerAdvice`와 `@ExceptionHandler`를 활용하여 전역 예외 처리 클래스를 작성하기
    - 에러 메시지, HTTP 상태 코드, 타임스탬프 등 **공통된 응답 형식**을 정의하여 클라이언트에 일관된 정보를 제공해주세요.
    - **참고 1. [프로젝트](https://github.com/Re-4aliens/backend)에서 사용중인 Exception Handler**
        
        ```java
        @RestControllerAdvice
        @Order(value = Integer.MIN_VALUE)
        public class ApiExceptionHandler {
        
            @ExceptionHandler(value = RestApiException.class)
            public ResponseEntity<Object> apiException(RestApiException apiException) {
                ErrorCode errorCode = apiException.getErrorCode();
                return ResponseEntity
                        .status(errorCode.getHttpStatus())
                        .body(errorCode.getDevelopCode());
            }
        }
        ```
        
3. **커스텀 예외 클래스 작성**
    - **참고 2. [프로젝트](https://github.com/Re-4aliens/backend)에서 사용중인 커스텀 예외**
        
        ```java
        public class RestApiException extends RuntimeException {
        
            private final ErrorCode errorCode;
        
            public RestApiException(final ErrorCode errorCode) {
                super(errorCode.getDevelopCode());
                this.errorCode = errorCode;
            }
        
            public ErrorCode getErrorCode() {
                return errorCode;
            }
        }
        ```
        
4. **에러코드 Enum 활용해보기**
    - 도메인 별 에러코드를 만들어 문서처럼 관리해볼 수 있어요.
    - 개발시 필수는 아니지만, 만들어두면 협업때 편하게 사용할 수 있습니다.
    - **참고 3. [프로젝트](https://github.com/Re-4aliens/backend)에서 사용중인 ErrorCode Enum**
        
        ```java
        public interface ErrorCode {
            String getDevelopCode();
            HttpStatus getHttpStatus();
            String getMessage();
        }
        ```
        
        ```java
        public enum MatchingError implements ErrorCode {
            NOT_FOUND_MATCHING_ROUND(HttpStatus.NOT_FOUND, "MA1", "매칭 회차를 찾을 수 없음"),
            NOT_VALID_MATCHING_RECEPTION_TIME(HttpStatus.BAD_REQUEST, "MA2", "매칭 접수 시간이 아님"),
            NOT_FOUND_MATCHING_APPLICATION_INFO(HttpStatus.NOT_FOUND, "MA3", "매칭 신청 정보 찾을 수 없음"),
            NOT_FOUND_PREFER_LANGUAGE(HttpStatus.NOT_FOUND, "MA4", "선호 언어를 찾을 수 없음"),
            INVALID_LANGUAGE_INPUT(HttpStatus.BAD_REQUEST, "MA5", "두 선호 언어가 같을 수 없음"),
            DUPLICATE_MATCHING_APPLICATION(HttpStatus.BAD_REQUEST, "MA6", "중복된 매칭 신청"),
            ;
        
            private final HttpStatus httpStatusCode;
            private final String developCode;
            private final String message;
        
            MatchingError(final HttpStatus httpStatusCode, final String developCode, final String message) {
                this.httpStatusCode = httpStatusCode;
                this.developCode = developCode;
                this.message = message;
            }
        
            @Override
            public HttpStatus getHttpStatus() {
                return httpStatusCode;
            }
        
            @Override
            public String getDevelopCode() {
                return developCode;
            }
        
            @Override
            public String getMessage() {
                return message;
            }
        }
        ```
        
5. **테스트 코드 작성**
    - 각 예외 상황에 대한 **테스트**를 작성하여, 글로벌 예외 핸들러가 올바르게 작동하는지 검증해주세요
    

### 2. 심화(선택)

---

- 컨트롤러가 아닌 인터셉터에서 예외(세션 관련)가 발생한다면 어떻게 사용자에게 응답으로 반환할 수 있을까?
    - 생각해보거나 실제로 구현해보기

<aside>

### **📌 개발 시 지켜야할 것**

</aside>

- 파고들고, 즐기면서 개발하기🙂
- 기억이 잘 안나는 부분은 이전 과제 복습하기

---

진행하시면서 어려운 점이 있으면 언제든지 질문해 주세요. 😊 **각자의 깃허브 레포지토리에 진행한 내용을 PR로 남겨주시고, 완료되면 저를 리뷰어로 지정**해주세요.
