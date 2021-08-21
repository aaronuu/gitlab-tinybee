package top.koolhaas.tinybee.generator.run;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import top.koolhaas.tinybee.generator.generator.AutoGenerator;
import top.koolhaas.tinybee.generator.generator.InjectionConfig;
import top.koolhaas.tinybee.generator.generator.config.*;
import top.koolhaas.tinybee.generator.generator.config.builder.ConfigBuilder;
import top.koolhaas.tinybee.generator.generator.config.po.TableInfo;
import top.koolhaas.tinybee.generator.generator.config.rules.FileType;
import top.koolhaas.tinybee.generator.generator.config.rules.NamingStrategy;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: tinybee
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
public class CodeGenerator {

    private static String DB_URL = "jdbc:mysql://10.113.206.45:10001/code-analysis?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&useSSL=false&zeroDateTimeBehavior=convertToNull";
    private static String DB_USER = "root";
    private static String DB_PASS = "1234567890";
    private static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    /**
     * 要生成的表名
     */
    private static List<String> TABLE_NAMES = Lists.newArrayList("ca_holiday");

    /**
     * 生成代码注释中的作者，根据谁生成写谁
     */
    private static String AUTHOR = "hackerdom";

    /**
     * 产品名称
     */
    private static String APP_NAME = "tinybee";

    /**
     * 产品层目录
     */
    private static String BASE_PATH = "./src/main";

    /**
     * 包名
     */
    private static String PARENT_PACKAGE = "top.koolhaas." + APP_NAME;

    /**
     * 执行方法
     *
     * @param args
     */
    public static void main(String[] args) {
        TransferCodeConfig autoCreateCodeConfig = TransferCodeConfig.builder().entityLombokModel(true)
                .outPath4Transfer(BASE_PATH + "/java/top/koolhaas/" + APP_NAME + "/inbound/transfer/")
                .outPath4VO(BASE_PATH + "/java/top/koolhaas/" + APP_NAME + "/core/dto/")
                .package4Transfer(PARENT_PACKAGE + ".inbound.transfer")
                .package4VO("top.koolhaas." + APP_NAME + ".core.dto")
                .outPath4Controller(BASE_PATH + "/java/top/koolhaas/" + APP_NAME + "/inbound/ui/backend/controller/")
                .vuePath(BASE_PATH + "/vue/" + APP_NAME + "/")
                .appName(APP_NAME)
                .build();
        runGen(TABLE_NAMES, BASE_PATH, PARENT_PACKAGE, autoCreateCodeConfig);
    }

    public static void runGen(List<String> tableNames, String basePath, String parentPackage, TransferCodeConfig transferCodeConfig) {
        // 自定义文件命名，注意 %s 会自动填充表实体属性！
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setTransferCodeConfig(transferCodeConfig);
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig().setOutputDir(basePath + "/java/")
                .setFileOverride(true)
                .setActiveRecord(false)
                .setEnableCache(false)
                .setBaseResultMap(true)
                .setBaseColumnList(false)
                .setAuthor(AUTHOR)
                .setSwagger2(true)
                .setControllerName("%sController")
                .setServiceName("%sRepository")
                .setServiceImplName("%sRepositoryImpl")
                .setMapperName("%sMapper")
                .setXmlName("%sMapper")
                .setDomainName("%sDomain");
        autoGenerator.setGlobalConfig(globalConfig);
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig()
                .setDbType(DbType.MYSQL)
                .setDriverName(DRIVER_NAME)
                .setUrl(DB_URL)
                .setUsername(DB_USER)
                .setPassword(DB_PASS);
        autoGenerator.setDataSource(dataSourceConfig);
        // 策略配置
        // strategy.setTablePrefix(new String[] { "sys_" });// 此处可以修改为您的表前缀
        // 表名生成策略
        StrategyConfig strategy = new StrategyConfig().setInclude(tableNames.toArray(new String[1]))
                .setNaming(NamingStrategy.underline_to_camel)
                .setSuperServiceClass(null)
                .setSuperServiceImplClass(null)
                .setSuperMapperClass(null)
                .setEntityLombokModel(true);
        autoGenerator.setStrategy(strategy);
        // 包配置
        PackageConfig packageConfig = new PackageConfig().setParent(parentPackage)
                .setController("inbound.ui.backend.controller")
                .setService("infrastructure.repository")
                .setServiceImpl("infrastructure.repository.impl")
                .setMapper("infrastructure.mapper")
                .setEntity("domain")
                .setXml("xml")
                .setDomain("application.service");
        autoGenerator.setPackageInfo(packageConfig);

        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = Maps.newHashMap();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };
        injectionConfig.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                checkDir(filePath);
                return true;
            }
        });
        List<FileOutConfig> focList = new ArrayList<>();
        //        focList.add(new FileOutConfig("/templates/mapper.xml.ftl") {
        //            @Override
        //            public String outputFile(TableInfo tableInfo) {
        //                return basePath + "/resources/mapper/" + tableInfo.getEntityName() + "Mapper"
        //                        + StringPool.DOT_XML;
        //            }
        //        });
        focList.add(new FileOutConfig("/templates/formVO2.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return transferCodeConfig.getOutPath4VO() + tableInfo.getEntityName() + "VO"
                        + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/transfer2.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {

                return transferCodeConfig.getOutPath4Transfer() + tableInfo.getEntityName() + "Transfer"
                        + StringPool.DOT_JAVA;
            }
        });
        focList.add(new FileOutConfig("/templates/controller2.java.ftl") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return transferCodeConfig.getOutPath4Controller() + tableInfo.getEntityName() + "Controller"
                        + StringPool.DOT_JAVA;
            }

            @Override
            public boolean shouldGen(TableInfo tableInfo) {
                return StrUtil.isNotEmpty(tableInfo.getServiceAggName());
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        autoGenerator.setCfg(injectionConfig);
        TemplateConfig templateConfig = new TemplateConfig()
                .setEntityKt(null)
                .setController(null)
                .setXml(null);
        autoGenerator.setTemplate(templateConfig);
        autoGenerator.execute();
    }
}
