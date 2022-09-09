# MarketCury

## ☑️요약

### 9조 클론코딩

**클론 대상 사이트** : [마켓컬리](https://www.kurly.com/shop/main/index.php?utm_source=1055&utm_medium=2202&utm_campaign=home_hashtag&utm_term=&gclid=Cj0KCQiA09eQBhCxARIsAAYRiymFTo_B-h_Ud0gO_bvKSG36IaBralIpgBFdb2WKsfT25LH5s8RrK-4aAm26EALw_wcB#%EB%A7%88%EC%BC%93%EC%BB%AC%EB%A6%AC&utm_content=brand)

📸  **클론 페이지** : 로그인, 회원가입, 메인, 장바구니

🛠  **클론 기능** : 로그인, 회원가입

➤ [사이트 바로가기](http://marketkkurly.s3-website.ap-northeast-2.amazonaws.com/)
➤ [시연영상 바로가기](https://youtu.be/7TeThbOcbZ4/)

## 📅 프로젝트 기간 및 팀원 👨🏻‍💻 👩‍💻
**2022.04.15 ~ 2022.04.21**

👨‍👩‍👧 **Back-End** : 강민승, 김규수, 정동섭

👨‍👦 **Front-End** : 강민택, 이예지
## ⚔️ 기술 스택
**Back-End**
## API 명세서

[Notion]()
## 📜  페이지 설명
### 📎  로그인 페이지
<pre>
    - JWT활용 하여 로그인 필터부분 커스터마이징
    - 토큰 정보(payload)에 username, nickname 추가하여 전송
    - 정상 로그인 시 토큰 전송, 비정상 시 에러메시지 전송
</pre>
### 📎  회원가입 페이지
<pre>
    - 
    - 
    - 아이디 중복 확인
</pre>
### 📎  메인(신상품), 베스트, 알뜰쇼핑 페이지
<pre>
    -메인 페이지 및 검색 : 메인페이지는 검색어:"식자재"를 default로 구현
    -장바구니 추가 버튼 : 장바구니에 이미 있는 제품의 장바구니 추가 버튼을 누르면 장바구니의 수량 증가
    -메인 페이지, 상세 페이지, 장바구니 추가 모두 로그인 하지 않아도 접근 가능
</pre>
### 📎  장바구니 페이지
<pre>
    - 장바구니에 담은 상품을 조회 (로그인 유저 & 비 로그인 유저)
    - 장바구니 페이지 내에서 개수 +,- 수정 기능
    - 담을 제품 삭제 기능
</pre>
## ⛳️  역할 담당
<details>
    <summary>김승민</summary>
    <!-- summary 아래 한칸 공백 두고 내용 삽입 -->
        * [기능] 전체 상품 조회, 검색(NAVER API), 상세 상품 조회, 상품 주문하기
  </details>
<details>
    <summary>백승호</summary>
    <!-- summary 아래 한칸 공백 두고 내용 삽입 -->
        * [기능] 회원가입, 로그인, 로그인체크, 아이디 중복, 리뷰(작성, 조회, 수정, 삭제), 장바구니 상품추가(비로그인 -> 로그인 전환)
  </details>
<details>
    <summary>엄성훈</summary>
    <!-- summary 아래 한칸 공백 두고 내용 삽입 -->
        * [기능] 장바구니 상품추가, 장바구니 전체조회, 장바구니 물품 삭제
  </details>
  
# 💡Trouble Shooting
<details>
    <summary>마켓컬리 API를 끌어올 수 없어서 네이버 쇼핑 API 사용</summary>
    <!-- summary 아래 한칸 공백 두고 내용 삽입 -->
        * 네이버 쇼핑을 전부 저장하는 것은 비효율 적이라 Product 부분을 검색을 할 때마다 저장 하도록 함
  </details>
<br>
<details>
    <summary>페이지를 로드 할 때 처음에는 페이지를 순서대로 눌러야하는 오류 발생</summary>
    <!-- summary 아래 한칸 공백 두고 내용 삽입 -->
        * 이 부분은 Product 저장 방식의 문제라 변경하지 못함
  </details>
<br>
<details>
    <summary>장바구니에 추가 시 데이터 변동의 고민</summary>
    <!-- summary 아래 한칸 공백 두고 내용 삽입 -->
        * 장바구니에 추가 시 +,- 로 수량과 가격을 변경할 수 있는 부분이 필요
        > 변경되는 부분은 모두 프론트에서 처리하고 
        > 데이터의 최종값을 API를 통해서 DB에 저장
  </details>
<br>
<details>
    <summary> 장바구니 내역에 대한 고민 (비회원 -> 로그인 전환 시)</summary>
    <!-- summary 아래 한칸 공백 두고 내용 삽입 -->
        * 비회원으로 장바구니 주문 시 주문 내역이, 로그인 했을 때 그대로 저장되는 마켓컬리 기능에 대한 고민 
        > 비회원 주문시 프론트엔드의 local stroage를 활용하여 정보를 저장
        > 로그인 시 로그인 API호출과 함께 유저의 장바구니로 전송되도록 API를 추가하여 
        > local storage 정보를 넘겨 받아 DB저장
  </details>
<br>
<details>
    <summary> JPA 쿼리 메소드 문제</summary>
    <!-- summary 아래 한칸 공백 두고 내용 삽입 -->
        * Cart에서 ProductId를 이용하여 삭제할 때, Cart엔티티에서 설정한 이름이 아닌 것으로 요청을 하여서 Status 500에러가 발생함
        > findByUserIdAndId -> findByUserIdAndProductId로 수정
  </details>
<h3 align="center"><b>📂 Project Directory Structure 📁</b></h3>
<pre>
<code>
/com.sparta.Webmini2
  └──/controller
     ├── /CartController.java
     ├── /CommentController.java
     ├── /OrderController.java
     ├── /ProductController.java
     └── /UserController.java
  └──/dto
     └──/CartDto.java
        ├── /CartFromNonToLoginUserRequestDto.java
        ├── /CartRequestDto.java
        └── /CartResponseDto.java
     └──/CommentDto.java
        ├── /CommentRequestDto.java
        └── /CommentResponseDto.java
     └──/OrderRequestDto.java
        ├── /OrderRequestDto.java
        └── /OrderRequestFinalDto.java
     └──/productDto.java
        ├── /ProductDto
        ├── /ProductRequestDto
        └── /ProductResponseDto
    └──/userDto.java
        ├── /IsDuplicatedRequestDto.java
        ├── /IsDuplicatedResponseDto.java
        ├── /LoginCheckDto.java
        └── /SignupRequestDto.java
  └──/exception
     ├── /Exception.java
     ├── /ExceptionHandling.java
  └──/model
     ├── /Cart.java
     ├── /Comment.java
     ├── /Order.java
     ├── /OrderCart.java
     ├── /Product.java
     ├── /Timestamped.java
     └── /User.java
  └──/repository
     ├── /CartRepository.java
     ├── /CommentRepository.java
     ├── /OrderCartRepository.java
     ├── /OrderRepository.java
     ├── /ProductRepository.java
     └── /UserRepository.java
  └──/security
     └── /filter
         ├── /FormLoginFilter.java
         └── /JwtAuthFilter.java
     └── /jwt
         ├── /HeaderTokenExtractor.java
         ├── /JwtDecoder.java
         ├── /JwtPreProcessingToken.java
         └── /JwtTokenUtils.java
     └── /provider
         ├── /FormLoginSuccessHandler.java
         └── /JWTAutProvider.java
     ├── /FilterSkipMatcher.java
     ├── /FormLoginFailureHandler.java
     ├── /FormLoginSuccessHandler.java
     ├── /UserDetailsImpl.java
     ├── /UserDetailsServiceImpl.java
     └── /WebSecurityConfig.java
  └──/service
     ├── /CartService.java
     ├── /CommentService.java
     ├── /OrderService.java
     ├── /ProductServiceService.java
     └── /UserService.java
  └──/utils
     └── /NaverShopSearch.java
  ├──/MarketCurlyApplication.java
  └──/WebConfig.java
</code>
</pre>
