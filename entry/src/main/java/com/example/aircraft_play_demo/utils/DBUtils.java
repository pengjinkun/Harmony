package com.example.aircraft_play_demo.utils;

import ohos.app.Context;
import ohos.data.distributed.common.*;
import ohos.data.distributed.user.SingleKvStore;

import java.util.ArrayList;

public class DBUtils {
    //具体的实现数据库的初始化
    public static SingleKvStore initOrGetDB(Context context, String storeId){
        //要做的是事情，定义数据库，设计数据库的表里有什么字段，这里就举例子 自己根据自己的项目写
        FieldNode fanswer = new FieldNode("answer");//字段名
        fanswer.setNullable(false);//不能为空
        fanswer.setType(FieldValueType.STRING);//数据类型

        FieldNode fpaint = new FieldNode("paint");
        fpaint.setNullable(false);
        fanswer.setType(FieldValueType.JSON_ARRAY);

//        FieldNode fdmoney = new FieldNode("money");
//        fdmoney.setType(FieldValueType.DOUBLE);//数据类型
//
//        FieldNode fdpaymentMatters = new FieldNode("paymentMatters");
//        fdpaymentMatters.setType(FieldValueType.STRING);//数据类型
//
//        FieldNode fddateYear = new FieldNode("dateYear");
//        fddateYear.setType(FieldValueType.INTEGER);//数据类型
//
//        FieldNode fdinOrExp = new FieldNode("inOrExp");
//        fdinOrExp.setType(FieldValueType.BOOLEAN);//数据类型

        //把上面的字段，封装到Schema对象
        Schema schema = new Schema();
        //设置索引
        ArrayList<String> indexList = new ArrayList<>();
        indexList.add("$.id"); //schema默认有一个rootFieldNode
        schema.setIndexes(indexList);
        schema.getRootFieldNode().appendChild(fanswer);
//        schema.getRootFieldNode().appendChild(fdmoney);
//        schema.getRootFieldNode().appendChild(fdpaymentMatters);
//        schema.getRootFieldNode().appendChild(fddateYear);
//        schema.getRootFieldNode().appendChild(fdinOrExp);
        schema.setSchemaMode(SchemaMode.STRICT);//严格模式

        KvManagerConfig kvManagerConfig = new KvManagerConfig(context);
        KvManager kvManager = KvManagerFactory.getInstance().createKvManager(kvManagerConfig);
        //设置操作参数
        Options options = new Options();
        options.setCreateIfMissing(true)//是否默认创建
                .setEncrypt(false)//是否默认加密
                .setKvStoreType(KvStoreType.SINGLE_VERSION)//单版本类型
                .setSchema(schema);
        SingleKvStore singleKvStore = kvManager.getKvStore(options, storeId);
        return  singleKvStore;
    }
}
