package cn.hdj.hdjblog.util;

import cn.hdj.hdjblog.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.springframework.context.annotation.Profile;

/**
 * @auther: h_dj
 * @date: 2019/4/18 17:34
 * @description: 代码生成器
 */
@Profile("dev")
public class CodeGenerator {


    public static void main(String[] args) {
        //代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        //生成的文件输出到哪
        String projectPath = System.getProperty("user.dir");
        String outPutDir = "/gen";
        gc.setOutputDir(projectPath + outPutDir);
        //默认主键生成策略为uuid
        // AUTO(0),
        //    NONE(1),
        //    INPUT(2),
        //    ID_WORKER(3),
        //    UUID(4),
        //    ID_WORKER_STR(5)
        gc.setIdType(IdType.AUTO);
        gc.setOpen(false);
        gc.setFileOverride(true);
        gc.setAuthor("hdj");
        gc.setBaseColumnList(true);
        gc.setBaseResultMap(true);
        gc.setEntityName("%sDO");
        gc.setMapperName("%sDao");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setControllerName("%sController");
        mpg.setGlobalConfig(gc);
        //gc.setIdType(IdType.UUID);
        //是否开启Swagger2
//        gc.setSwagger2(true);
        //gc.setXmlName("%sDaoMapper");


        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://127.0.0.1:3306/blog?useUnicode=true&characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT%2B8");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        mpg.setDataSource(dsc);


        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("cn.hdj.hdjblog");
        pc.setMapper("dao")
                .setService("service")
                .setController("controller")
                .setEntity("entity");
        mpg.setPackageInfo(pc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass(BaseEntity.class);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        //继承自定义的controller，service，impl，dao
//        strategy.setSuperControllerClass("cn.hdj.hdjblog.base.BaseController");
//        strategy.setSuperServiceClass("cn.hdj.hdjblog.base.BaseService");
//        strategy.setSuperServiceImplClass("cn.hdj.hdjblog.base.BaseServiceImpl");
//        strategy.setSuperMapperClass("cn.hdj.hdjblog.base.BaseDao");
        strategy.setInclude(
                "t_friend_link"
        );
        strategy.setSuperEntityColumns("id", "create_time", "update_time");
        strategy.setControllerMappingHyphenStyle(false);
        strategy.setTablePrefix("t_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }
}
