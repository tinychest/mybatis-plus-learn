package priv.wmc.generate.service.impl;

import priv.wmc.generate.pojo.entity.User;
import priv.wmc.generate.mapper.UserMapper;
import priv.wmc.generate.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Wang Mincong
 * @since 2020-08-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
