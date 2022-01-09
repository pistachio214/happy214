package com.happy.lucky.system.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

public class CodeGenerator {
    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


    public static void main(String[] args) {
        // 实例化代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "\\happy-system";

        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("Roger Peng");
        gc.setOpen(false);
        gc.setFileOverride(true);
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("数据库地址");
        // dsc.setSchemaName("public");
        dsc.setDriverName("oracle.jdbc.OracleDriver");
        dsc.setUsername("用户名");
        dsc.setPassword("密码");
        mpg.setDataSource(dsc);
//
//        // 包配置（生成的entity、controller、service等包名）
//        PackageConfig pc = new PackageConfig();
//        // pc.setModuleName(scanner("模块名"));
//        pc.setParent("com.queenme.demo");
//        mpg.setPackageInfo(pc);
//
//        // 数据库表策略设置
//        StrategyConfig strategy = new StrategyConfig();
//        //数据库表映射到实体的命名
//        strategy.setNaming(NamingStrategy.underline_to_camel);
//        //数据库表字段映射到实体的命名
//        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
//        //strategy.setSuperEntityClass("你自己的父类实体,没有就不用设置!");
//        //【实体】是否为lombok模型（默认 false）
//        strategy.setEntityLombokModel(true);
//        //生成 @RestController 控制器
//        strategy.setRestControllerStyle(true);
//
//        // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
//        // 写于父类中的公共字段
//        // strategy.setSuperEntityColumns("id");
//        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
//        strategy.setControllerMappingHyphenStyle(true);
//        //strategy.setTablePrefix(pc.getModuleName() + "_");
//        mpg.setStrategy(strategy);
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
//        mpg.execute();
    }

}
