package com.example.orm.core;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ORMSession {
    private Connection connection;

    public ORMSession(Connection connection) {
        this.connection = connection;
    }

    //保存数据
    public void save(Object entity) throws Exception {
        String insertSQL = "";
        //1.从OrmConfig中获得保存有映射信息的集合
        List<Mapper> mapperList = ORMConfig.mapperList;

        //2.遍历集合，从集合中找到和entity参数对应的mapper对象
        for (Mapper mapper:mapperList
             ) {
            if (mapper.getClassName().equals(entity.getClass().getName())){
                String tableName = mapper.getTableName();
                String insertSQL1 = "insert into " + tableName + "(" ;
                String insertSQL2 = ") values ( " ;
                //3.得到当前对象所属类中的所有属性
                Field[] fields = entity.getClass().getDeclaredFields();
                for (Field field:fields
                     ) {
                    field.setAccessible(true);
                    //4.遍历过程中根据属性得到字段名
                    String columnName = mapper.getPropMapper().get(field.getName());
                    //5.遍历过程中根据属性得到它的值
                    String columnValue = field.get(entity).toString();
                    //6.拼接SQL语句
                    insertSQL1 += columnName+",";
                    insertSQL2 += "'" + columnValue + "',";
                }
                insertSQL = insertSQL1.substring(0,insertSQL1.length()-1)
                            + insertSQL2.substring(0,insertSQL2.length()-1) + ")";
                break;
            }
        }
        //把SQL语句打印到控制台
        System.out.println("miniORM-save" + insertSQL);
        //7.通过JDBC发送并执行SQL语句
        PreparedStatement statement = connection.prepareStatement(insertSQL);
        statement.executeUpdate();
        statement.close();
    }

    //根据主键删除 delete from 表名 where 主键 = 值
    public void delete(Object entity) throws Exception {
        String deleteSQL = "delete from ";
        //1.从OrmConfig中获得保存有映射信息的集合
        List<Mapper> mapperList = ORMConfig.mapperList;
        //2.遍历集合，从集合中找到和entity参数对应的mapper对象
        for (Mapper mapper:mapperList
        ) {
            if (mapper.getClassName().equals(entity.getClass().getName())) {
                //3.得到我们想要的mapper对象，并得到表名
                String tableName = mapper.getTableName();
                deleteSQL += tableName + " where ";
                //4.得到主键字段名和属性名
                Object[] idProp = mapper.getIdMapper().keySet().toArray();  //idProp[0]
                Object[] idColumn = mapper.getIdMapper().values().toArray();    //idColumn[0]
                //5.得到主键的值
                Field field = entity.getClass().getDeclaredField(idProp[0].toString());
                field.setAccessible(true);
                String idVal = field.get(entity).toString();
                //6.拼接SQL
                deleteSQL += idColumn[0].toString() + "=" + idVal;
                break;
            }
        }
        //把SQL语句打印到控制台
        System.out.println("miniORM-save" + deleteSQL);
        //7.通过JDBC发送并执行SQL语句
        PreparedStatement statement = connection.prepareStatement(deleteSQL);
        statement.executeUpdate();
        statement.close();
    }

    //根据主键进行查询  select * from 表名 where 主键字段 = 值
    public Object findOne(Class clz,Object id) throws Exception {
        String querySQL = "select * from ";
        //1.从ORMConfig中得到存有映射信息的集合
        List<Mapper> mapperList = ORMConfig.mapperList;
        //2.遍历集合拿到想要的mapper对象
        for (Mapper mapper:mapperList
             ) {
            if (mapper.getClassName().equals(clz.getName())){
                //3.获得表名
                String tableName = mapper.getTableName();
                //4.获得主键字段名
                Object[] idColumn = mapper.getIdMapper().values().toArray();
                //5.拼接sql
                querySQL += tableName + " where " + idColumn[0].toString() + "=" + id;
                break;
            }
        }
        //把SQL语句打印到控制台
        System.out.println("miniORM-save " + querySQL);
        //6.通过JDBC发送并执行SQL语句,拿到结果集
        PreparedStatement statement = connection.prepareStatement(querySQL);
        ResultSet rs = statement.executeQuery();
        //7.封装结果集，返回对象
        if (rs.next()){
            //查询到一行数据
            //8.创建一个对象，目前属性都是初始值
            Object obj = clz.newInstance();
            //9.遍历mapperList集合，找到想要的mapper对象
            for (Mapper mapper:mapperList
                 ) {
                if (mapper.getClassName().equals(clz.getName())){
                    //10.得到存有属性-字段的映射信息
                    Map<String, String> propMap = mapper.getPropMapper();
                    //11.遍历集合分别拿到属性名和字段名
                    Set<String> keySet = propMap.keySet();
                    for (String prop:keySet     //prop就是属性名
                         ) {
                        String column = propMap.get(prop);  //colum就是和属性对应的字段名
                        Field field = clz.getDeclaredField(prop);
                        field.setAccessible(true);
                        field.set(obj,rs.getObject(column));
                    }
                    break;
                }
            }
            //12.释放资源
            statement.close();
            rs.close();
            //13.返回查询出来的对象
            return obj;
        }else {
            //没有查到数据
            return null;
        }
    }

    //关闭链接，释放资源
    public void close() throws Exception {
        if (connection!=null){
            connection.close();
            connection = null;
        }
    }
}
