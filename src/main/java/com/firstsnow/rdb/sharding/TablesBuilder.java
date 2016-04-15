package com.firstsnow.rdb.sharding;

import java.util.ArrayList;

/**
 * 散表的名称集合builder
 * Created by ganhaitian on 2016/3/16.
 */
public class TablesBuilder extends ArrayList{

    public TablesBuilder(String tableName,int shardingNums){
        for(int i = 0;i < shardingNums;i ++){
            this.add(tableName + "_" + i);
        }
    }

}
