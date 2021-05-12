import bean.User;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

@Configurable
public class AppConfig {

//    @Bean(name = "people")
//    public People people(){
//        return new People();
//    }

    @Bean(name = "user")
    @Scope("prototype")
    public User user(){
        return new User();
    }

}
