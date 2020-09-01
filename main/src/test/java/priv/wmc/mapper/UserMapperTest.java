package priv.wmc.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Arrays;
import java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.wmc.pojo.entity.User;

@Slf4j
@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void selectById() {
        User user = userMapper.selectById(1L);

        Assertions.assertNotNull(user);

        log.info(user.toString());
    }

    @Test
    void insert() {
        User user = new User();
        user.setUsername("明明");
        int insert = userMapper.insert(user);

        Assertions.assertEquals(1, insert);

        log.info(String.valueOf(user.getId()));
    }

    @Test
    void update() {
        User user = new User();
        user.setId(1L);
        // 设置了什么字段的值，才会在要更新的字段里
        user.setUsername("明明2");

        int update = userMapper.updateById(user);

        Assertions.assertEquals(1, update);

        log.info(user.toString());
    }

    @Test
    void testOptimisticLock1() {
        // 查出 version 字段的值
        User user = userMapper.selectById(1L);

        Assertions.assertNotNull(user);

        // 修改用户信息：会额外自增更新 version 字段，并且更新的条件中还有上面version，且值是上边查询出来的version值
        user.setUsername("小切");
        userMapper.updateById(user);
    }

    @Test
    void testOptimisticLock2() {
        // 线程1执行查询更新
        User user = userMapper.selectById(1L);
        Assertions.assertNotNull(user);
        user.setUsername("小切1");

        // 线程2在线程1还未更新之前执行查询更新操作
        User user2 = userMapper.selectById(1L);
        Assertions.assertNotNull(user2);
        user2.setUsername("小切2");
        userMapper.updateById(user2);

        // 线程1想更新，但是发现版本号不对，无法更新（所以一般下面的操作需要自旋：具体操作不知道怎么搞，再查询，再更新？）
        userMapper.updateById(user);
    }

    @Test
    void testSelect() {
        userMapper.selectById(1L);
        // 批量查询
        userMapper.selectBatchIds(Arrays.asList(1L, 2L ,3L));
        // 条件查询
        userMapper.selectByMap(Collections.singletonMap("username", "小明"));
        // 分页查询
        userMapper.selectPage(new Page<>(1, 2), null);
    }

    @Test
    void testDelete() {
        userMapper.deleteById(1L);
        userMapper.deleteBatchIds(Arrays.asList(1L, 2L, 3L));
        userMapper.deleteByMap(Collections.singletonMap("name", "小明"));
    }

    /** 数据表实体添加删除标识、全局配置删除的字段名、未删除时的值、删除以后的值 */
    @Test
    void testSoftDelete() {
        userMapper.deleteById(1L);
        User user = userMapper.selectById(1L);
        Assertions.assertNull(user);
    }

}