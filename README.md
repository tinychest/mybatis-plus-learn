# Mybatis Plus Demo 流程

1. 创建一个最基本的Springboot项目（详见项目）

2. 配置数据库连接

3. 从 Mybatis Demo 项目中复制数据表结构初始化脚本

4. 复制基建：枚举接口、枚举、枚举处理器、工具类、数据表实体、结果集

5. 创建 Mapper接口、启动配置类上添加 `@MapperScan("priv.wmc.mapper")`

6. 创建测试类 - 查询全部

7. 为 Mybatis Plus 配置自定义枚举处理器的所在包

8. 添加日志文件（指定mapper包下的日志级别为debug）、为 Mybatis Plus 配置日志实现为 Slf4j

9. 主键生成策略：`@TableId`注解的使用

10. 自动填充：在 Mybatis Demo 项目中，创建时间使用的是数据库的特性，创建相应的数据时不理会创建时间，数据库会采用字段默认的策略生成的值，当然，肯定是不推荐这样做的；更新时间思路明确，但是没有实现，因为需要涉及到sql语句的修改。采用 Mybatis Plus 提供的拓展去实现（注解、处理器）

    - **gmtCreate、gmtModified**

    1. 为初始化脚本添加修改表字段的默认值的语句，并执行

    2. 为创建时间和更新时间添加 `@TableField` 修饰

    3. 创建自定义处理器

    - **version**（以下介绍来自官网）

      意图：当要更新一条记录的时候，希望这条记录没有被别人更新

      乐观锁实现方式：

      - 取出记录时，获取当前version
      - 更新时，带上这个version
      - 执行更新时， set version = newVersion where version = oldVersion
      - 如果version不对，就更新失败

      ```
      注意点：
      1、支持的数据类型只有:int,Integer,long,Long,Date,Timestamp,LocalDateTime
      
      2、整数类型下 newVersion = oldVersion + 1
      
      3、newVersion 会回写到 entity 中
      
      4、仅支持 updateById(id) 与 update(entity, wrapper) 方法
      
      5、在 update(entity, wrapper) 方法下, wrapper 不能复用!!!
      ```

    1. 为初始化脚本添加脚本：为表添加乐观锁版本号的字段 - 默认值为1，并执行

    2. 数据表实体添加相应的字段：`@Version String version;`
    3. 创建一个配置类，用于Mybatis的相关配置，注册一个用于乐观锁逻辑实现的组件。并把项目启动配置类上的`@MapperScan`注解也移过来
    4. 编写测试用例（通过实践了解乐观锁的机制）

    - **deleted**

    1. 为初始化脚本添加脚本：为表添加乐观锁版本号的字段 - 默认值为0，并执行

    2. 数据表实体添加相应的字段：`@TableLogic Boolean deleted;`

       ```
       注意点：
       查找: 追加where条件过滤掉已删除数据,且使用 wrapper.entity 生成的where条件会忽略该字段
       
       更新: 追加where条件防止更新到已删除数据,且使用 wrapper.entity 生成的where条件会忽略该字段
       ```

11. 分页查询

    1. 同乐观锁，也需要注册一个组件
    2. 编写测试用例

12. 性能分析插件

    1. 引入依赖

    ```
    <!-- https://mvnrepository.com/artifact/p6spy/p6spy -->
    <dependency>
      <groupId>p6spy</groupId>
      <artifactId>p6spy</artifactId>
      <version>3.9.1</version>
    </dependency>
    ```

    2. 主配置文件修改

    ```
    spring:
      datasource:
        driver-class-name: com.p6spy.engine.spy.P6SpyDriver
    #    driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:p6spy:mysql://localhost:8090/mybatis_plus?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    #    url: jdbc:mysql://localhost:8090/mybatis_plus?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT%2B8
    ```

    3. 从官方复制p6spy的配置文件spy.properties（修改打印日志的日志系统为slf4j）

    ```
    #3.2.1以上使用
    modulelist=com.baomidou.mybatisplus.extension.p6spy.MybatisPlusLogFactory,com.p6spy.engine.outage.P6OutageFactory
    #3.2.1以下使用或者不配置
    #modulelist=com.p6spy.engine.logging.P6LogFactory,com.p6spy.engine.outage.P6OutageFactory
    # 自定义日志打印
    logMessageFormat=com.baomidou.mybatisplus.extension.p6spy.P6SpyLogger
    #日志输出到控制台
    #appender=com.baomidou.mybatisplus.extension.p6spy.StdoutLogger
    # 使用日志系统记录 sql
    appender=com.p6spy.engine.spy.appender.Slf4JLogger
    # 设置 p6spy driver 代理
    deregisterdrivers=true
    # 取消JDBC URL前缀
    useprefix=true
    # 配置记录 Log 例外,可去掉的结果集有error,info,batch,debug,statement,commit,rollback,result,resultset.
    excludecategories=info,debug,result,commit,resultset
    # 日期格式
    dateformat=yyyy-MM-dd HH:mm:ss
    # 实际驱动可多个
    #driverlist=org.h2.Driver
    # 是否开启慢SQL记录
    outagedetection=true
    # 慢SQL记录标准 2 秒
    outagedetectioninterval=2
    ```

    注意点

    ```
    打印出sql为null,在excludecategories增加commit
    
    批量操作不打印sql,去除excludecategories中的batch
    
    批量操作打印重复的问题请使用MybatisPlusLogFactory (3.2.1新增）
    
    该插件有性能损耗，不建议生产环境使用。
    ```

13. 条件构造器（拼接复杂的查询条件）
14. 将数据表实体的公共字段抽取成一个抽象的基础父类

16. Mybatis提供的枚举解决方案，是这样的，为所有的通用枚举的序列化依据字段上添加`@EnumValue`，并且在主配置文件上指定这些通用枚举所在的包。不是很符合理想的效果，但是发现在Mybatis Demo中配置枚举序列化解决方案是可行的

17. 代码生成器

    生成的代码，手动配置了Swagger注解，所以需要引入Swagger相关依赖

    （调研了一下Springfox和之前Swagger Bootstrap UI）

18. 插件推荐

    MybatisX - 根据Mapper接口方法，自动生成，Mapper.xml中的sql

## 辣鸡玩意

- 经过自己测试得到的认知：Maven只要在一个模块中创建了子模块，就有种该模块作为父模块就不应该存放任何的代码和静态资源文件（配置文件），其中代码还会编译，静态资源文件Maven编译根本就不理会了，即使在pom指定了resource标签也没用

  （找到原因：在idea界面右键创建一个module，idea会为root工程添加一个`<packaging>pom</packaging>`，就是这个导致的）

- MP代码生成的生成路径居然也是在父项目所在目录下边；子模块的日志输出文件配置为 target 开头或者 ./target 开头，都是生成到父模块的target文件下

  （没办法，最终手动添加了一层路径）