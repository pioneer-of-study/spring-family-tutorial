import bean.People;
import bean.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Solution {

    public static void main(String[] args) {
        // 得到IOC容器对象
        //在classpath路径下加载xml配置文件，完成Spring容器的加载
        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("容器创建");
        //基于注解配置的Spring容器加载方式
        ApplicationContext ac2 = new AnnotationConfigApplicationContext(AppConfig.class);

        User user = (User) ac.getBean("user");
        User user1 = (User) ac.getBean("user");

        User user2 = (User) ac2.getBean("user");
        User user3 = (User) ac2.getBean("user");

        People people = (People) ac.getBean("people");

        System.out.println(user);
        //单例
        System.out.println(user == user1);
        //多例
        System.out.println(user2 == user3);
        System.out.println(people);
    }
}
