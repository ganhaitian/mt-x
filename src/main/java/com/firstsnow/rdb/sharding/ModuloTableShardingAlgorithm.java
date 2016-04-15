package com.firstsnow.rdb.sharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.table.SingleKeyTableShardingAlgorithm;
import com.google.common.collect.Lists;
import org.apache.commons.lang.NumberUtils;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 * 用模数分片算法
 * Created by ganhaitian on 2016/3/16.
 */
public class ModuloTableShardingAlgorithm implements SingleKeyTableShardingAlgorithm<String>{


    public String doEqualSharding(Collection<String> tableNames, ShardingValue<String> shardingValue) {
        int tableNums = tableNames.size();
        String value = shardingValue.getValue();
        if(value.indexOf('_') >= 0){
            String idPart = value.split("_")[1];

            if(NumberUtils.isNumber(idPart)){
                int resultIndex = Integer.parseInt(idPart) % tableNums;
                // 取id后两位
                for(String each : tableNames){
                    if(each.endsWith(resultIndex + ""))
                        return each;
                }
            }
        }

        throw new IllegalArgumentException();
    }

    public Collection<String> doInSharding(Collection<String> tableNames, ShardingValue<String> shardingValue) {
        return Lists.newArrayList(doEqualSharding(tableNames,shardingValue));
    }

    public Collection<String> doBetweenSharding(Collection<String> tableNames, ShardingValue<String> shardingValue) {
        Collection<String> result = new LinkedHashSet<String>(tableNames.size());
        /*Range<String> range = (Range<String>) shardingValue.getValueRange();
        for (Integer i = range.lowerEndpoint(); i <= range.upperEndpoint(); i++) {
            for (String each : tableNames) {
                if (each.endsWith(i % 2 + "")) {
                    result.add(each);
                }
            }
        }*/
        return result;
    }
}
