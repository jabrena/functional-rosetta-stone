/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */

package info.jab.fp.sealed;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
 
public class EitherTest {

    @BeforeAll
    public static void setUpClass() {
    }

    @Test
    public void testFold() {
        Either<String, Integer> success = Either.right(42);
        Either<String, Integer> failure = Either.left("Error occurred");
    }

}