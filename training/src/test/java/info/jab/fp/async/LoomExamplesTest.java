/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package info.jab.fp.async;

import java.util.concurrent.StructuredTaskScope.Subtask.State;

import static org.assertj.core.api.BDDAssertions.then;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import info.jab.utils.TestLoggerExtension;

 
@ExtendWith(TestLoggerExtension.class)
public class LoomExamplesTest {

    private static LoomExamples loomExamples;

    @BeforeAll
    public static void setUpClass() {
        loomExamples = new LoomExamples();
    }

    @Test
    public void usingVThread1() {
        loomExamples.usingVThread1();
    }


    @Test
    public void usingVThread2() {
        var result = loomExamples.usingVThread2();

        //Then
        then(result).isEqualTo("White");
    }

    @Disabled
    @Test
    public void usingVThread3() {
        var result = loomExamples.usingVThread3();

        //Then
        then(result).isEqualTo("White");
    }

    @Test
    public void usingVThread4() {
        var result = loomExamples.usingVThread4();

        //Then
        then(result).isEqualTo(3);
    }

    @Test
    public void usingVThread5() {
        var result = loomExamples.usingVThread5();

        //Then
        then(result).isEqualTo(State.SUCCESS);
    }

    @Test
    public void usingVThread6() {
        var result = loomExamples.usingVThread6();

        //Then
        then(result).isEqualTo(2);
    }

    @Test
    public void usingVThread7() {
        var result = loomExamples.usingVThread7();

        //Then
        then(result).isEqualTo(0);
    }

    @Test
    public void usingVThread8() {
        var result = loomExamples.usingVThread8();

        //Then
        then(result).isEqualTo(0);
    }
}