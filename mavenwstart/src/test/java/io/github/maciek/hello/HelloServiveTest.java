package io.github.maciek.hello;

import io.github.maciek.lang.Lang;
import io.github.maciek.lang.LangRepository;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class HelloServiveTest {

    private static String WELOCME = "Hello";
    private static String FALLBACK_ID_WELOME = "Hola";

    //private HelloService SUT = new HelloService(); //System under test

    @Test
    public void test_nullName_prepareGreeting_returnGreetingWithFallBackName() throws Exception {
        //given
        var mockReposytory = alwaysReturnHelloRepository();
        var SUT = new HelloService(mockReposytory);
        // when
        var result = SUT.prepareGreeting(null, -1);

        //then
        assertEquals(WELOCME+ " " + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_name_prepareGreeting_returnGreetingWithName() throws Exception {
        //given
        var SUT = new HelloService();
        var name = "test";

        // when
        var result = SUT.prepareGreeting(name, -1);

        //then
        assertEquals(WELOCME + " "+ name + "!", result);
    }

    @Test
    public void test_nullLang_prepareGreeting_returnGreetingWithFallBackIdLang() throws Exception {
        //given
        var mockReposytory = fallbackLangIdReposytory();
        var SUT = new HelloService(mockReposytory);
        // when
        var result = SUT.prepareGreeting(null, null);

        //then
        assertEquals(FALLBACK_ID_WELOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }

    @Test
    public void test_nonExisitngLang_prepareGreeting_returnGreetingWithFallBackIdLang() throws Exception {
        //given
        var mockReposytory = new LangRepository()
        {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.empty();
            }
        };
        var SUT = new HelloService(mockReposytory);
        // when
        var result = SUT.prepareGreeting(null, -1);

        //then
        assertEquals(HelloService.FALLBACK_LANG.getWelcomeMsg() + " " + HelloService.FALLBACK_NAME + "!", result);
    }


    /*@Test
    public void test_textLang_prepareGreeting_returnGreetingWithFallBackIdLang() throws Exception {
        //given
        var mockReposytory = fallbackLangIdReposytory();
        var SUT = new HelloService(mockReposytory);
        // when
        var result = SUT.prepareGreeting(null, "abc");

        //then
        assertEquals(FALLBACK_ID_WELOME + " " + HelloService.FALLBACK_NAME + "!", result);
    }*/

    private LangRepository fallbackLangIdReposytory() {
        return new LangRepository(){
            @Override
            public Optional<Lang> findById(Integer id) {
                if(id.equals(HelloService.FALLBACK_LANG.getId())){
                    return Optional.of(new Lang(null, FALLBACK_ID_WELOME, null));
                }
                return Optional.empty();
            }
        };
    }

    private LangRepository alwaysReturnHelloRepository() {
        return new LangRepository() {
            @Override
            public Optional<Lang> findById(Integer id) {
                return Optional.of(new Lang(null, WELOCME, null));
            }
        };
    }

}
