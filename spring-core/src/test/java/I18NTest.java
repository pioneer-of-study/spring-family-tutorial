import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Locale;

public class I18NTest {

    @Test
    public void Test() {
        Locale defaultLocale = Locale.getDefault();

        System.out.println("country="+ defaultLocale.getCountry());
        System.out.println("language="+ defaultLocale.getLanguage());

        Object[] arg = new Object[] { "Yuang"};
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        String msg = ctx.getMessage("k1", arg, defaultLocale);
        System.out.println(msg);
        defaultLocale = Locale.US;
        msg = ctx.getMessage("k1", arg, defaultLocale);
        System.out.println(msg);
    }
}
