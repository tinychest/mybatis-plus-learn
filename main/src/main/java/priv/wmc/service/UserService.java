package priv.wmc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import priv.wmc.mapper.UserMapper;
import priv.wmc.pojo.dto.UserResult;
import priv.wmc.pojo.entity.User;

/**
 * @author Wang Mincong
 * @date 2020-08-16 16:33:46
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserService {

    private final UserMapper userMapper;

    public UserResult findById(Long id) {
        User user = userMapper.selectList(null).get(0);
        return new UserResult(user);
    }

}
