package inhatc.spring.shop.controller;

import inhatc.spring.shop.dto.UserDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public UserDto hello(){
        UserDto userDto = new UserDto();
        userDto.setAge(20);
        userDto.setName("홍길동");

        return userDto;
    }
}
