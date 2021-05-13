package bean;

import lombok.Data;

@Data
public class User {
    private String id;
    private String username;

    public User() {
        System.out.println("User被创建了");
    }
}
