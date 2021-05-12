package controller;

import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.UserService;

@Controller
@RequestMapping("/test")
@Api(value = "SpringBoot测试接口")
public class UserTestController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping(value ="/jdbc")
    @ApiOperation(value="整合jdbc测试")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "username", value = "用户ID", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "password", value = "旧密码", required = true, dataType = "String")
    })
    public String test1(String username, String password){
        User u = new User();
        u.setUsername(username);
        u.setPassword(password);
        return userService.AddUser(u) + "";

    }

}
