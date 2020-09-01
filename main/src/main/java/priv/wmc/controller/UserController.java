package priv.wmc.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import priv.wmc.pojo.dto.UserResult;
import priv.wmc.service.UserService;

/**
 * @author Wang Mincong
 * @date 2020-08-16 16:33:53
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserResult getOne(@PathVariable Long id) {
        return userService.findById(id);
    }

}
