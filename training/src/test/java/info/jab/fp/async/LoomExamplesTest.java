/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package info.jab.fp.async;

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
}