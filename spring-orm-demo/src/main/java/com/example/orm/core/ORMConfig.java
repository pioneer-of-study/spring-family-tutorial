package com.example.orm.core;

import com.example.orm.utils.AnnotationUtil;
import com.example.orm.utils.Dom4jUtil;
import org.dom4j.Document;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用来解析并封装框架的核心配置文件中的数据
 */
public class ORMConfig {
    //classpath路径
    private static String classPath;
    //核心配置文件
    private static File cfgFile;
    //<property>标签中的数据
    private static Map<String,String> propConfig;
    //映射配置文件路径
    private static Set<String> mappingSet;
    //实体类
    private static Set<String> entitySet;
    //映射信息
    public static List<Mapper> mapperList;

    static {
        //得到classPath路径
        classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            //转码
            classPath = URLDecoder.decode(classPath, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //得到核心配置文件
        cfgFile = new File(classPath + "miniORM.cfg.xml");
        if (cfgFile.exists()){
            //解析核心配置文件中的数据
            Document document = Dom4jUtil.getXMLByFilePath(cfgFile.getPath());
            propConfig = Dom4jUtil.Elements2Map(document, "property", "name");
            mappingSet = Dom4jUtil.Elements2Set(document, "mapping", "resource");
            entitySet = Dom4jUtil.Elements2Set(document, "entity", "package");
        }else {
            cfgFile = null;
            System.out.println("未找到核心配置文件miniORM.cfg.xmls");
        }
    }

    //从propConfig获取数据并连接数据库
    private Connection getConnection() throws Exception{
        String url = propConfig.get("connection.url");
        String driverClass = propConfig.get("connection.driverClass");
        String username = propConfig.get("connection.username");
        String password = propConfig.get("connection.password");
        //jdbc封装
        Class.forName(driverClass);
        Connection connection = DriverManager.getConnection(url, username, password);
        connection.setAutoCommit(true);
        return connection;
    }

    private void getMapping() throws Exception{
        mapperList = new ArrayList<>();
        //1.解析xxx.mapper.xml文件拿到映射数据
        for (String xmlPath:mappingSet
             ) {
            Document document = Dom4jUtil.getXMLByFilePath(classPath + xmlPath);
            String className = Dom4jUtil.getPropValue(document, "class", "name");
            String tableName = Dom4jUtil.getPropValue(document, "class", "table");
            Map<String, String> id_id = Dom4jUtil.ElementsID2Map(document);
            Map<String, String> mapping = Dom4jUtil.Elements2Map(document);

            Mapper mapper = new Mapper();
            mapper.setTableName(tableName);
            mapper.setClassName(className);
            mapper.setIdMapper(id_id);
            mapper.setPropMapper(mapping);

            mapperList.add(mapper);
        }
        //2.解析实体类的注解拿到映射数据
        for (String packagePath:entitySet
             ) {
            Set<String> nameSet = AnnotationUtil.getClassNameByPackage(packagePath);
            for (String name:nameSet
                 ) {
                Class<?> clz = Class.forName(name);
                String className = AnnotationUtil.getClassName(clz);
                String tableName = AnnotationUtil.getTableName(clz);
                Map<String, String> idMapper = AnnotationUtil.getIdMapper(clz);
                Map<String, String> propMapping = AnnotationUtil.getPropMapping(clz);

                Mapper mapper = new Mapper();
                mapper.setTableName(tableName);
                mapper.setClassName(className);
                mapper.setIdMapper(idMapper);
                mapper.setPropMapper(propMapping);

                mapperList.add(mapper);
            }
        }
    }

    //创建ORMSession对象
    public ORMSession buildORMSession() throws Exception{
        //1.连接数据库
        Connection connection = getConnection();
        //2.得到映射数据
        getMapping();
        //3.创建ORMSession
        return new ORMSession(connection);
    }

}
