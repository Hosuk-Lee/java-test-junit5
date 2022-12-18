package org.example.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {


    @DisplayName("스터디 만들어")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeateTest(RepetitionInfo c) {
        System.out.println(
                "test\t" + c.getCurrentRepetition() + "/" + c.getTotalRepetitions()
        );
    }

    //
    @DisplayName("이건되네?")
    @ParameterizedTest(name= "{index} {displayName} message={0}")
    @ValueSource(strings = {"봄","여름","가을","겨울"})
    void parameterizedTest(String message){
        System.out.println(message);
    }

    @DisplayName("이건??")
    @ParameterizedTest(name= "{index} {displayName} message={0}")
    @ValueSource(ints = {10,20,30,40})
    void parameterizedTest2(int message){
        System.out.println(message);
    }

    @DisplayName("이건??")
    @ParameterizedTest(name= "{index} {displayName} message={0}")
    @ValueSource(ints = {10,20,30,40})
    void parameterizedTest3(@ConvertWith(StudyConverter.class) Study study){
        System.out.println(study);
    }
    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("이건??")
    @ParameterizedTest(name= "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
//    void parameterizedTest4(Integer limit, String name) {
//    void parameterizedTest4(ArgumentsAccessor argumentsAccessor) {
    void parameterizedTest4(@AggregateWith(StudyAggregator.class) Study study ) {
        // 1.
        // Study study = new Study(limit, name);

        // 2.
//        Study study = new Study(
//                argumentsAccessor.getInteger(0)
//                ,argumentsAccessor.getString(1));
//
        System.out.println(study);
    }
    static class StudyAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            Study study = new Study(accessor.getInteger(0),accessor.getString(1));
            return study;
        }
    }

// 인자값 변환


    @DisplayName("스터디 만들기")
//    @Test
//    @Tag("fast") // Local 에서 실행하고 싶다.
    @FastTest
    void create_new_study() {

        Study study = new Study();

        /* 원래는 실패하면 바로 끝나지만, 아래처럼 묶으면 모든 상태를 검사할 수 있다.*/
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.STARTED, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 합니다.")
        );
    }

    @DisplayName("스터디 부시기")
//    @Test
//    @Tag("slow") // CI 환경에서 실행 하고 싶다.
    @SlowTest
    void create_new_study2(){
        Study study = new Study();
        assertNotNull(study);
        assertEquals(StudyStatus.DRAFT, study.getStatus(),
                ()->"바보멍청이.."
        );
    }

    @Test
    @DisplayName("스터디 쪼개기")
    void create_new_study3(){
        Study study = new Study();
        /* Exception 일치하면 정상 오류 */
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = illegalArgumentException.getMessage();
        assertEquals("0보다 커야 합니다.", message);
    }

    @Test
    @DisplayName("스터디 Timeout")
    void create_new_study4(){
        // 함수가 끝날때 까지 기다려준다.
        assertTimeout(Duration.ofMillis(100), ()-> {
           new Study();
           Thread.sleep(300);
        });

        // 타임아웃 발생시 바로 취소
        // 주의
        // TODO ThreadLocal 예상치 못한 결과가 발생 할 수 있다.
        assertTimeoutPreemptively(Duration.ofMillis(100), ()-> {
            new Study();
            Thread.sleep(300);
        });
    }

    @Test
    @DisplayName("스터디 Assert J")
    void create_new_study5(){

        Study actual = new Study(10);
        // assertJ SpringBoot Starter 를 사용하면 디펜던시가 자동으로 붙는다.
        //assertThat
    }

    @Test
    @DisplayName("스터디 테스트 조건 넣기")
    void create_new_study6(){
        String test_env = System.getenv("TEST_ENV");
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));


    }

}