package priv.wmc.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.wmc.pojo.entity.User;

@Slf4j
@SpringBootTest
class UserWrapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void test() {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();

        userQueryWrapper
            // 子查询（这条子查询的效果相当于，添加了一个查询条件，username 为 小明）
            .inSql("id", "select id from user where username = '小明'")
            // 不为空
            .isNull("username")
            .isNotNull("username")
            // 模糊查询
            .like("username", "小明")
            .likeLeft("username", "小明")
            .likeRight("username", "小明")
            .notLike("username", "小明")
            // 范围
            .between("age", 10, 12)
            .notBetween("age", 10, 12)
            // 大于或者等于
            .ge("age", 10)
            // 排序
            .orderByDesc("age");

        List<User> userList = userMapper.selectList(userQueryWrapper);

        // 返回值为单数
//        User user = userMapper.selectOne(userQueryWrapper);

        // 查询数量
//        Integer userCount = userMapper.selectCount(userQueryWrapper);

        // 返回值封装为Map
//        List<Map<String, Object>> maps = userMapper.selectMaps(userQueryWrapper);
//        Page<Map<String, Object>> mapPage = userMapper.selectMapsPage(new Page<>(1, 2), userQueryWrapper);

        Assertions.assertNotNull(userList);

        userList.forEach(user -> log.info(user.toString()));
    }

}