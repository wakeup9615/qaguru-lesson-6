package guru.qa;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static java.util.Arrays.asList;

public class ParamsTest {
    //параметризов тесты - если тест кейс отличается только входными данными,
    // а сам сценарий один и тот же, то пишем параметризованные тесты (ветвления логики не должно быть)
    @ValueSource(strings = {"JUnit 5", "TestNG"})
    @ParameterizedTest(name = "При поиске в яндексе по запросу {0} в результатах отображается текст {0}")
    void yaTestCommon(String testData) {
        Selenide.open("https://ya.ru");
        $("#text").setValue(testData);
        $("button[type='submit']").click();
        //$$ - находят все эл-ты с селектором li.serp-item
        $$("li.serp-item").find(text(testData)).shouldBe(visible);
    }

    //@CsvFileSource(resources = "/test_data/1.csv") //равносильно тому что ниже
    @CsvSource(value = { //, обрабатывается как csv file
            "JUnit 5, team’s statement on the war in Ukraine",
            "TestNG, testing"
    })
    @ParameterizedTest(name = "При поиске в яндексе по запросу {0} в результатах отображается текст {1}")
        //то что слева и справа от  запятой
    void yaTestComplex(String searchData, String expectedResult) {
        Selenide.open("https://ya.ru");
        $("#text").setValue(searchData);
        $("button[type='submit']").click();
        //$$ - находят все эл-ты с селектором li.serp-item
        $$("li.serp-item").find(text(expectedResult)).shouldBe(visible);
    }

    static Stream<Arguments> yaTestVeryComplexDataProvider() {
        return Stream.of(
                Arguments.of("JUnit 5", asList("JUnit 5", "framework")),
                Arguments.of("TestNG", asList("JUnit", "framework"))
        );
    }

    @MethodSource(value = "yaTestVeryComplexDataProvider") //если датапровайдер  ^ совпадает с именем теста, то аргументы в эту аннотацию писать необязательно
    @ParameterizedTest(name = "При поиске в яндексе по запросу {0} в результатах отображается текст {1}")
    void yaTestVeryComplex(String searchData, List<String> expectedResult) {
        Selenide.open("https://ya.ru");
        $("#text").setValue(searchData);
        $("button[type='submit']").click();
        $$("li.serp-item").shouldHave(CollectionCondition.texts(expectedResult));
    }

    @EnumSource(Sex.class)
    @ParameterizedTest
    void enumTest(Sex sex) {
        Selenide.open("https://ya.ru");
        $("#text").setValue(sex.desc);
        $("button[type='submit']").click();
        $$("li.serp-item").find(text(sex.desc)).shouldBe(visible);
    }

    //аннотация - некотрорая информаия которая хранится рядом с классом в котором она использует (некая метаинформауия)


    @Override
    public String toString() {
        return super.toString();
    }
}
