# **음식 주문 관리 시스템 - DB 테이블 분리하기**

<aside>

**지난주에 진행한 음식 주문 관리 시스템을 발전시키기 위해** 

**이번 2주차 과제에서는 데이터베이스 테이블을 분리하고,** 

**연관관계를 매핑하는 작업을 진행해주세요. 😊**

</aside>

![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/cc828b43-d99b-4ac1-bf52-62d7808d44cb/88b8eaf0-e0c8-4940-8d15-c8fefd0537d5/image.png)

---

<aside>

### **📌 시스템 확장 목표**

</aside>

1. **테이블 분리**: 기존에 "주문" 테이블에 모든 정보를 포함했던 것을 개선하여, **각 역할별로 테이블을 분리**해 주세요.(필요에 따라 추가로 분리하여도 괜찮습니다!)
    - **음식 테이블**
    - **주문 테이블**
    - **사용자 테이블**
2. **연관관계 매핑**: JPA를 활용하여 각 테이블 간의 **연관관계를 매핑**하고, **매핑된 결과를 다이어그램(ERD)**으로 그려주세요. 설명을 함께 작성해주세요(관계 종류, 컬럼 등)
    - 다이어그램 전용 무료 툴은 **1. ERD-Diagram 2. draw.io 3. MysqlWorkbench를** 추천드려요.
        
        ---
        
        ![image.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/cc828b43-d99b-4ac1-bf52-62d7808d44cb/ab56ae10-9ae2-4b02-ba9b-41ef9afc49a6/image.png)
        
        https://dbdiagram.io/d → 이 사이트 활용
        
        ```java
        Table foods {
          id integer [primary key]
          name varchar
          price decimal
        }
        
        Table orders {
          id integer [primary key]
          user_id integer [ref: > users.id]
          food_id integer [ref: > foods.id]
          quantity integer
          status varchar
          menu_name varchar
          menu_price decimal
        }
        
        Table users {
          id integer [primary key]
          name varchar
          phone_number varchar [note: "Unique phone number"]
        }
        ```
        
        ---
        
    1. 그 외에 툴도 연관관계와 테이블 컬럼을 알아 볼 수 있다면 괜찮습니다.
3. **API 확장**
    - **사용자 생성 API 추가 -** **새로운 RestController를 생성**하여 ****사용자를 추가할 수 있는 기능을 만들어주세요.
4. **Transaction 활용**
    - 서비스 코드 내에서 DB에 여러 차례 접근하는 메서드들에는 반드시 **transaction을 활용**해주세요.

---

<aside>

### **📌 개발 시 지켜야할 것**

</aside>

1. 깃허브 레포지토리 내에 이슈를 만들고 커밋과 PR에서 **이슈를 트래킹** 해주세요
2. RESTAPI를 사용하지 않은 분들은 코드리뷰를 참고하여 **json 포맷을 반환하도록** 수정해주세요.
3. **DTO의 경우 response인지, request인지 구분**할 수 있도록 네이밍을 고민해주세요.

---

과제를 진행하시면서 어려운 점이 있으면 언제든지 질문해 주세요! 😊 **각자의 깃허브 레포지토리에 진행한 내용을 PR로 남겨주시고, 완료되면 저를 리뷰어로 지정**해주신 후 연락 남겨주세요. 모두 화이팅입니다! 🚀
