# 로또 - TDD

---
## 1단계 - 학습 테스트 실습
### String 클래스에 대한 학습 테스트
#### 요구사항 1
* "1,2"을 ,로 split 했을 때 1과 2로 잘 분리되는지 확인하는 학습 테스트를 구현한다.
* "1"을 ,로 split 했을 때 1만을 포함하는 배열이 반환되는지에 대한 학습 테스트를 구현한다.
##### 힌트
* 배열로 반환하는 값의 경우 assertj의 contains()를 활용해 반환 값이 맞는지 검증한다.
* 배열로 반환하는 값의 경우 assertj의 containsExactly()를 활용해 반환 값이 맞는지 검증한다.
#### 요구사항 2
* "(1,2)" 값이 주어졌을 때 String의 substring() 메소드를 활용해 ()을 제거하고 "1,2"를 반환하도록 구현한다.
#### 요구사항 3
* "abc" 값이 주어졌을 때 String의 charAt() 메소드를 활용해 특정 위치의 문자를 가져오는 학습 테스트를 구현한다.
* String의 charAt() 메소드를 활용해 특정 위치의 문자를 가져올 때 위치 값을 벗어나면 StringIndexOutOfBoundsException이 발생하는 부분에 대한 학습 테스트를 구현한다.
* JUnit의 @DisplayName을 활용해 테스트 메소드의 의도를 드러낸다.
##### 힌트
* [AssertJ Exception Assertions](https://joel-costigliola.github.io/assertj/assertj-core-features-highlight.html#exception-assertion) 문서 참고
```java
import static org.assertj.core.api.Assertions.*;

assertThatThrownBy(() -> {
// ...
}).isInstanceOf(IndexOutOfBoundsException.class)
.hasMessageContaining("Index: 2, Size: 2");
```
```java
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

assertThatExceptionOfType(IndexOutOfBoundsException.class)
.isThrownBy(() -> {
// ...
}).withMessageMatching("Index: \\d+, Size: \\d+");
```
* 자주 발생하는 Exception의 경우 Exception별 메서드 제공하고 있음
  * assertThatIllegalArgumentException()
  * assertThatIllegalStateException()
  * assertThatIOException()
  * assertThatNullPointerException()

### Set Collection에 대한 학습 테스트
* 다음과 같은 Set 데이터가 주어졌을 때 요구사항을 만족해야 한다.
```java
public class SetTest {
  private Set<Integer> numbers;

  @BeforeEach
  void setUp() {
    numbers = new HashSet<>();
    numbers.add(1);
    numbers.add(1);
    numbers.add(2);
    numbers.add(3);
  }

  // Test Case 구현
}
```
#### 요구사항 1
* Set의 size() 메소드를 활용해 Set의 크기를 확인하는 학습테스트를 구현한다.
#### 요구사항 2
* Set의 contains() 메소드를 활용해 1, 2, 3의 값이 존재하는지를 확인하는 학습테스트를 구현하려한다.
* 구현하고 보니 다음과 같이 중복 코드가 계속해서 발생한다.
* JUnit의 ParameterizedTest를 활용해 중복 코드를 제거해 본다.
```java
@Test
void contains() {
    assertThat(numbers.contains(1)).isTrue();
    assertThat(numbers.contains(2)).isTrue();
    assertThat(numbers.contains(3)).isTrue();
}
```
##### 힌트
* [Guide to JUnit 5 Parameterized Tests](https://www.baeldung.com/parameterized-tests-junit-5)
```java
@ParameterizedTest
@ValueSource(strings = {"", "  "})
void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input) {
    assertTrue(Strings.isBlank(input));
}
```
#### 요구사항 3
* 요구사항 2는 contains 메소드 결과 값이 true인 경우만 테스트 가능하다. 입력 값에 따라 결과 값이 다른 경우에 대한 테스트도 가능하도록 구현한다.
* 예를 들어 1, 2, 3 값은 contains 메소드 실행결과 true, 4, 5 값을 넣으면 false 가 반환되는 테스트를 하나의 Test Case로 구현한다.
##### 힌트
* [Guide to JUnit 5 Parameterized Tests](https://www.baeldung.com/parameterized-tests-junit-5) 문서에서 @CsvSource를 활용한다.
```java
@ParameterizedTest
@CsvSource(value = {"test:test", "tEst:test", "Java:java"}, delimiter = ':')
void toLowerCase_ShouldGenerateTheExpectedLowercaseValue(String input, String expected) {
    String actualValue = input.toLowerCase();
    assertEquals(expected, actualValue);
}
```
#### assertj 활용
[Introduction to AssertJ](https://www.baeldung.com/introduction-to-assertj) 문서 참고해 assertj의 다양한 활용법 익힌다.

---
## 2단계 - 문자열 덧셈 계산기
### 문자열 덧셈 계산기를 통한 TDD/리팩토링 실습
#### 기능 요구사항
* 쉼표(,) 또는 콜론(:)을 구분자로 가지는 문자열을 전달하는 경우 구분자를 기준으로 분리한 각 숫자의 합을 반환 (예: “” => 0, "1,2" => 3, "1,2,3" => 6, “1,2:3” => 6)
* 앞의 기본 구분자(쉼표, 콜론)외에 커스텀 구분자를 지정할 수 있다. 커스텀 구분자는 문자열 앞부분의 “//”와 “\n” 사이에 위치하는 문자를 커스텀 구분자로 사용한다. 예를 들어 “//;\n1;2;3”과 같이 값을 입력할 경우 커스텀 구분자는 세미콜론(;)이며, 결과 값은 6이 반환되어야 한다.
* 문자열 계산기에 숫자 이외의 값 또는 음수를 전달하는 경우 RuntimeException 예외를 throw한다.
#### 프로그래밍 요구사항
* indent(들여쓰기) depth를 2단계에서 1단계로 줄여라.
* depth의 경우 if문을 사용하는 경우 1단계의 depth가 증가한다. if문 안에 while문을 사용한다면 depth가 2단계가 된다.
* 메소드의 크기가 최대 10라인을 넘지 않도록 구현한다.
* method가 한 가지 일만 하도록 최대한 작게 만들어라.
* else를 사용하지 마라.
#### 기능 요구사항 분리 및 힌트
1. 빈 문자열 또는 null 값을 입력할 경우 0을 반환해야 한다.(예 : “” => 0, null => 0)
```java
if (text == null) {}
if (text.isEmpty()) {}
```
2. 숫자 하나를 문자열로 입력할 경우 해당 숫자를 반환한다.(예 : “1”)
```java
int number = Integer.parseInt(text);
```
3. 숫자 두개를 컴마(,) 구분자로 입력할 경우 두 숫자의 합을 반환한다.(예 : “1,2”)
```java
String[] numbers = text.split(",");
// 앞 단계의 구분자가 없는 경우도 split()을 활용해 구현할 수 있는지 검토해 본다.
```
4. 구분자를 컴마(,) 이외에 콜론(:)을 사용할 수 있다. (예 : “1,2:3” => 6)
```java
String[] tokens= text.split(",|:");
```
5. “//”와 “\n” 문자 사이에 커스텀 구분자를 지정할 수 있다. (예 : “//;\n1;2;3” => 6)
```java
// java.util.regex 패키지의 Matcher, Pattern import
Matcher m = Pattern.compile("//(.)\n(.*)").matcher(text);
if (m.find()) {
    String customDelimiter = m.group(1);
    String[] tokens= m.group(2).split(customDelimiter);
    // 덧셈 구현
}
```
6. 음수를 전달할 경우 RuntimeException 예외가 발생해야 한다. (예 : “-1,2,3”)
* 구글에서 “junit4 expected exception”으로 검색해 해결책을 찾는다.
#### TestCase 소스 코드
```java
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class StringAddCalculatorTest {
    @Test
    public void splitAndSum_null_또는_빈문자() {
        int result = StringAddCalculator.splitAndSum(null);
        assertThat(result).isEqualTo(0);

        result = StringAddCalculator.splitAndSum("");
        assertThat(result).isEqualTo(0);
    }

     @Test
    public void splitAndSum_숫자하나() throws Exception {
        int result = StringAddCalculator.splitAndSum("1");
        assertThat(result).isEqualTo(1);
    }

    @Test
    public void splitAndSum_쉼표구분자() throws Exception {
        int result = StringAddCalculator.splitAndSum("1,2");
        assertThat(result).isEqualTo(3);
    }

    @Test
    public void splitAndSum_쉼표_또는_콜론_구분자() throws Exception {
        int result = StringAddCalculator.splitAndSum("1,2:3");
        assertThat(result).isEqualTo(6);
    }

    @Test
    public void splitAndSum_custom_구분자() throws Exception {
        int result = StringAddCalculator.splitAndSum("//;\n1;2;3");
        assertThat(result).isEqualTo(6);
    }

    @Test
    public void splitAndSum_negative() throws Exception {
        assertThatThrownBy(() -> StringAddCalculator.splitAndSum("-1,2,3"))
                .isInstanceOf(RuntimeException.class);
    }
}
```
* [AssertJ Exception Assertions](https://www.baeldung.com/assertj-exception-assertion)

---
## 3단계 - 로또(자동)

### 기능 요구사항
* 로또 구입 금액을 입력하면 구입 금액에 해당하는 로또를 발급해야 한다.
* 로또 1장의 가격은 1000원이다.
```
구입금액을 입력해 주세요.
14000
14개를 구매했습니다.
[8, 21, 23, 41, 42, 43]
[3, 5, 11, 16, 32, 38]
[7, 11, 16, 35, 36, 44]
[1, 8, 11, 31, 41, 42]
[13, 14, 16, 38, 42, 45]
[7, 11, 30, 40, 42, 43]
[2, 13, 22, 32, 38, 45]
[23, 25, 33, 36, 39, 41]
[1, 3, 5, 14, 22, 45]
[5, 9, 38, 41, 43, 44]
[2, 8, 9, 18, 19, 21]
[13, 14, 18, 21, 23, 35]
[17, 21, 29, 37, 42, 45]
[3, 8, 27, 30, 35, 44]

지난 주 당첨 번호를 입력해 주세요.
1, 2, 3, 4, 5, 6

당첨 통계
---------
3개 일치 (5000원)- 1개
4개 일치 (50000원)- 0개
5개 일치 (1500000원)- 0개
6개 일치 (2000000000원)- 0개
총 수익률은 0.35입니다.(기준이 1이기 때문에 결과적으로 손해라는 의미임)

### 프로그래밍 요구사항
* 모든 기능을 TDD로 구현해 단위 테스트가 존재해야 한다. 단, UI(System.out, System.in) 로직은 제외
  * 핵심 로직을 구현하는 코드와 UI를 담당하는 로직을 구분한다.
  * UI 로직을 InputView, ResultView와 같은 클래스를 추가해 분리한다.
* java enum을 적용해 프로그래밍을 구현한다.
* 규칙 8: 일급 콜렉션을 쓴다.
* indent(인덴트, 들여쓰기) depth를 2를 넘지 않도록 구현한다. 1까지만 허용한다.
  * 예를 들어 while문 안에 if문이 있으면 들여쓰기는 2이다.
  * 힌트: indent(인덴트, 들여쓰기) depth를 줄이는 좋은 방법은 함수(또는 메소드)를 분리하면 된다.
* 함수(또는 메소드)의 길이가 15라인을 넘어가지 않도록 구현한다.
  * 함수(또는 메소드)가 한 가지 일만 잘 하도록 구현한다.
* 모든 로직에 단위 테스트를 구현한다. 단, UI(System.out, System.in) 로직은 제외
  * 핵심 로직을 구현하는 코드와 UI를 담당하는 로직을 구분한다.
  * UI 로직을 InputView, ResultView와 같은 클래스를 추가해 분리한다.
* 자바 코드 컨벤션을 지키면서 프로그래밍한다.
  * 참고문서: https://google.github.io/styleguide/javaguide.html 또는 https://myeonguni.tistory.com/1596
* else 예약어를 쓰지 않는다.
  * 힌트: if 조건절에서 값을 return하는 방식으로 구현하면 else를 사용하지 않아도 된다.
  * else를 쓰지 말라고 하니 switch/case로 구현하는 경우가 있는데 switch/case도 허용하지 않는다.
```

#### 힌트
* 로또 자동 생성은 Collections.shuffle() 메소드 활용한다.
* Collections.sort() 메소드를 활용해 정렬 가능하다.
* ArrayList의 contains() 메소드를 활용하면 어떤 값이 존재하는지 유무를 판단할 수 있다.
* 일급 콜렉션을 쓴다.
  * 6개의 숫자 값을 가지는 java collection을 감싸는 객체를 추가해 구현해 본다.
* 하드 코딩을 하지 않기 위해 상수 값을 사용하면 많은 상수 값이 발생한다. 자바의 enum을 활용해 상수 값을 제거한다. 즉, enum을 활용해 일치하는 수를 로또 등수로 변경해 본다.
```java
public enum Rank {
    FIRST(6, 2_000_000_000),
    SECOND(5, 30_000_000),
    THIRD(5, 1_500_000),
    FOURTH(4, 50_000),
    FIFTH(3, 5_000),
    MISS(0, 0);

    private int countOfMatch;
    private int winningMoney;

    private Rank(int countOfMatch, int winningMoney) {
        this.countOfMatch = countOfMatch;
        this.winningMoney = winningMoney;
    }

    public int getCountOfMatch() {
        return countOfMatch;
    }

    public int getWinningMoney() {
        return winningMoney;
    }
		
    public static Rank valueOf(int countOfMatch, boolean matchBonus) {
        // TODO 일치하는 수를 로또 등수로 변경한다. enum 값 목록은 "Rank[] ranks = values();"와 같이 가져올 수 있다.
        return null;
    }
}
```

### 기능 목록 및 commit 로그 요구사항
* 기능을 구현하기 전에 README.md 파일에 구현할 기능 목록을 정리해 추가한다.
* git의 commit 단위위는 앞 단계에서 README.md 파일에 정리한 기능 목록 단위로 추가한다.
  * 참고문서: AngularJS Commit Message Conventions
#### AngularJS Commit Message Conventions 중
* commit message 종류를 다음과 같이 구분
```
feat (feature)
fix (bug fix)
docs (documentation)
style (formatting, missing semi colons, …)
refactor
test (when adding missing tests)
chore (maintain)
```

### 기능 목록
* [x] 자동 로또 번호 생성
  * [x] 1 ~ 45 사이의 숫자
  * [x] 중복되지 않음
  * [x] 6개 컬렉션
  * [x] 오름차순 정렬
* [x] 로또 한장 생성
  * [x] 중복되지 않는 숫자 6개
* [x] 구입 금액에 해당하는 로또 매수 구하기
  * [x] 로또 한장 가격은 1,000원
  * [x] 1,000원 미만은 예외 처리
  * [x] 잔돈은 무시
* [x] 로또 여러장 생성
* [x] 당첨 번호 생성
  * [x] 중복되지 않는 숫자 6개
* [x] 로또 한장의 순위 구하기
  * [x] 1등: 6개 일치
  * [x] 2등: 5개 일치
  * [x] 3등: 4개 일치
  * [x] 4등: 3개 일치
* [x] 로또 여러장의 순위 구하기
* [x] 순위별 당첨 장 수 구하기
* [x] 총 수익률 구하기(1 기준)
  * [x] 총 당첨 금액 / 구입 금액
* [x] 사용자 입력
  * [x] 구입 금액 입력
  * [x] 당첨 번호 입력
* [x] 결과 출력
  * [x] 구입한 로또 매수 출력
  * [x] 로또 티켓 번호 출력
  * [x] 당첨 통계 출력

### 도메인
* LottoNumber - 로또 번호
  * 1에서 45사이의 숫자 속성 원시 값을 포장한다.
  * 정적 팩토리 메서드로 인스턴스를 생성해두고 재사용한다.
  * 문자 형식의 숫자를 입력 받아서 로또 번호를 생성한다.
* LottoTicket - 로또 티켓 1장
  * 로또 번호 6개 속성을 가지는 일급 컬렉션이다.
  * 숫자 6개의 문자열로 로또 티켓을 생성한다.
  * 당첨 번호에 해당하는 순위를 반환한다.
* Purchase - 구입
  * 구입 금액 숫자 속성 원시 값을 포장한다.
    * 1장 단위 가격인 1,000원 이상인지 확인한다.
    * 잔돈은 무시한다.
  * 구입 금액에 해당하는 로또 매수를 반환한다.
* LottoNumbersStrategy - 로또 번호 생성 전략 인터페이스
  * 로또 번호를 생성해서 반환한다.
* AutoLottoNumbersStrategy - 자동 로또 번호 생성 전략 구현 클래스
  * 숫자의 범위와 개수를 입력 받아서 중복되지 않는 임의의 수를 오름차순으로 반환한다.
* Ranking - 순위
  * enum 타입으로 일치 개수, 당첨 금액 속성을 가진다.
  * 일치 개수에 해당하는 당첨 순위를 반환한다.
* LottoTickets - 로또 티켓 n장
  * 로또 티켓 n장 속성을 가지는 일급 컬렉션이다.
  * 로또 번호 생성 전략, 구입 로또 매수로 로또 티켓 n장을 생성한다.
  * 당첨 번호에 해당하는 로또 결과를 반환한다.
* LottoResult - 로또 결과
  * 로또 티켓들의 순위를 가지는 일급 컬렉션이다.
  * 순위 별 당첨 티켓 수를 집계해서 반환한다.
  * 총 당첨 금액을 계산해서 총 수익률(총 당첨 금액 / 구입 금액)을 계산해서 반환한다.
