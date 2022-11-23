package org.example.test;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {


    @Test
    @DisplayName("스터디 만들기")
    void create_new_study(){
        Study study = new Study();

        /* 원래는 실패하면 바로 끝나지만, 아래처럼 묶으면 모든 상태를 검사할 수 있다.*/
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.STARTED, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다."),
                () -> assertTrue(study.getLimit() >0, "스터디 최대 참석 가능 인원은 0보다 커야 합니다.")
        );
    }

    @Test
    @DisplayName("스터디 부시기")
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