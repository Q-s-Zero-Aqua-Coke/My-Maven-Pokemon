## 포켓몬 API를 활용한 랜덤 포켓몬 정보 조회 프로그램

### 소개

이 프로그램은 PokeAPI를 사용하여 랜덤한 포켓몬의 이미지와 한국어 이름을 조회합니다. Java와 Jackson 라이브러리를 사용하여 API 데이터를 처리합니다.

### 기능

* 랜덤한 포켓몬 ID 생성 (1세대 포켓몬 범위: 1~151)
* PokeAPI에서 포켓몬 정보 조회 (이미지 URL)
* PokeAPI에서 포켓몬 종 정보 조회 (한국어 이름)
* 조회된 포켓몬 이미지 URL 및 한국어 이름 출력
* API 요청 실패 또는 데이터 누락 시 에러 핸들링

### 의존성

* Java 11 이상
* Jackson Databind (JSON 처리)
* Java HttpClient (HTTP 요청)

### 코드 설명

* `Main.java`: 메인 실행 클래스
    * `main` 메소드: 랜덤 포켓몬 ID 생성, API 요청, 결과 출력
    * `fetchPokemon` 메소드: 포켓몬 이미지 API 요청 및 JSON 파싱
    * `fetchPokemonSpecies` 메소드: 포켓몬 이름 정보 API 요청 및 JSON 파싱
* `Pokemon.java`: 포켓몬 이미지 JSON 데이터 모델 클래스
* `PokemonSpecies.java`: 포켓몬 이름 정보 JSON 데이터 모델 클래스

### 에러 핸들링

* API 요청 실패 시 콘솔에 에러 메시지 출력
* JSON 파싱 실패 시 콘솔에 에러 메시지 출력
* 한국어 이름을 찾을 수 없는 경우 "이름을 찾을 수 없습니다." 메시지 출력
