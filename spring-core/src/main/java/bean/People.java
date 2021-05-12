package bean;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class People {
    private String id;
    private String username;
}
