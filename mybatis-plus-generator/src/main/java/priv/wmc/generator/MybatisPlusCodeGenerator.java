package priv.wmc.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import priv.wmc.base.BaseEntity;

/**
 * Mybatis Plus code generator apply
 *
 * @author Wang Mincong
 * @date 2020-08-17 17:21:41
 */
@Slf4j
public class MybatisPlusCodeGenerator {

    public static final String PROJECT_PATH = System.getProperty("user.dir");

    /**
     * <p>
     * 读取控制台内容 → 去除空格 → 以英文逗号为分隔符切分返回字符串数组
     * </p>
     */
    public static String[] scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        log.info("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (ipt != null && ipt.length() != 0) {
                return ipt.trim().split(",");
            }
        }
        throw new MybatisPlusException("请输入正确的数据表名！");
    }

    private static GlobalConfig globalConfig() {
        return new GlobalConfig()
            .setOutputDir(PROJECT_PATH + "/mybatis-plus-generator/src/main/java")
            .setAuthor("Wang Mincong")
            .setOpen(false)
            .setFileOverride(false)
            .setServiceName("%sService")
            .setDateType(DateType.TIME_PACK)
            .setSwagger2(true);
    }

    private static DataSourceConfig dataSourceConfig() {
        return new DataSourceConfig()
            .setUrl("jdbc:mysql://localhost:8090/mybatis_plus?useUnicode=true&useSSL=false&characterEncoding=utf8")
            .setDriverName("com.mysql.cj.jdbc.Driver")
            .setUsername("root")
            .setPassword("123")
            .setDbType(DbType.MYSQL);
    }

    private static PackageConfig packageConfig() {
        return new PackageConfig()
            // 下面两个配置结合，实际生成的父报名就是 priv.wmc.generate
            .setParent("priv.wmc")
            .setModuleName("generate")
            // controller、service、entity、mapper的报名都是可以指定的
            .setEntity("pojo.entity");
    }

    private static StrategyConfig strategyConfig() {
        // 自动填充策略
        TableFill gmtCreate = new TableFill("gmt_create", FieldFill.INSERT);
        TableFill gmtModified = new TableFill("gmt_modified", FieldFill.UPDATE);

        String[] tableNames = scanner("请输入数据库的表名：（多个英文逗号分割）");

        return new StrategyConfig()
            .setNaming(NamingStrategy.underline_to_camel)
            .setColumnNaming(NamingStrategy.underline_to_camel)
            .setEntityBooleanColumnRemoveIsPrefix(true)

            .setEntityLombokModel(true)

            .setRestControllerStyle(true)

            .setSuperEntityClass(BaseEntity.class)
            .setSuperEntityColumns("id", "version", "gmt_create", "gmt_modified", "deleted")
            .setVersionFieldName("version")
            .setLogicDeleteFieldName("deleted")

            // 代码生成的依据：数据库表名
            .setInclude(tableNames)
            .setTableFillList(Arrays.asList(gmtCreate, gmtModified));
    }

    public static void main(String[] args) {
        new AutoGenerator()
            /* 全局配置 */
            .setGlobalConfig(globalConfig())
            /* 数据源配置 */
            .setDataSource(dataSourceConfig())
            /* 包配置 */
            .setPackageInfo(packageConfig())
            /* 策略配置 */
            .setStrategy(strategyConfig())
            /* 模板引擎 */
            .setTemplateEngine(new FreemarkerTemplateEngine())
            /* 配置模板 */
//            .setTemplate(templateConfig())
            /* 自定义配置 */
//            .setCfg(customConfig())
            .execute();
    }

    private static TemplateConfig templateConfig() {
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        return new TemplateConfig()
            .setEntity("templates/entity2.java")
            .setService("xxx")
            .setController("xxx");
    }

    private static InjectionConfig customConfig() {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        /// 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置 - 自定义配置会被优先输出
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return PROJECT_PATH + "/src/main/resources/mapper/" + packageConfig().getModuleName()
                    + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

//        IFileCreate iFileCreate = new IFileCreate() {
//            @Override
//            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
//                // 判断自定义文件夹是否需要创建
//                checkDir("调用默认方法创建的目录，自定义目录用");
//                if (fileType == FileType.MAPPER) {
//                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
//                    return !new File(filePath).exists();
//                }
//                // 允许生成模板文件
//                return true;
//            }
//        };
//        cfg.setFileCreate(iFileCreate);
        return cfg;
    }

}
