package Demo.controller;

import Demo.customAnnotation.PermissionAnnotation;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/demo")
public class DemoController {

    @GetMapping("/echo")
    public List<String> echo() {
        // 查询列表
        List<String> result = new ArrayList<>();
        result.add("hanf4");
        result.add("xuy7");
        result.add("xiaoh6");
        System.out.println("hanf4*****");
        return result;
    }

    @PostMapping("/addUser")
    @PermissionAnnotation
    public JSONObject  addUser(@RequestBody JSONObject request){
        return JSON.parseObject("{\"message\":\"SUCCESS\",\"code\":200}");
    }
}
