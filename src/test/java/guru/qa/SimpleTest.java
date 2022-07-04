package guru.qa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Учебный тесты")
public class SimpleTest {

    @DisplayName("Проверяем, что 3 > 2") //для отображения имени (не использ в параметризованных тестах)
    @Disabled("Ticket-321") //отключает тест из запуска ИЛИ можно перед клаасом установить эту аннотация, тогда не будут запускатся все тесты класса
    @Test
    void simpleTest() {
        //проверки в junit
        Assertions.assertTrue(3 > 2);
    }

    @Test
    void simpleTest1() {
        //проверки в junit
        Assertions.assertTrue(3 < 2);
    }

    @Test
    void simpleTest2() {
        throw new RuntimeException("Просто другой эксепшн");
    }
}
