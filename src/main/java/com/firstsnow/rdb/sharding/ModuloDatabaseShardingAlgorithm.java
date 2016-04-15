package com.firstsnow.rdb.sharding;

import com.dangdang.ddframe.rdb.sharding.api.ShardingValue;
import com.dangdang.ddframe.rdb.sharding.api.strategy.database.SingleKeyDatabaseShardingAlgorithm;

import java.util.Collection;

/**
 * Created by lenovo on 2016/4/6.
 */
public class ModuloDatabaseShardingAlgorithm implements SingleKeyDatabaseShardingAlgorithm {

    public String doEqualSharding(Collection availableTargetNames, ShardingValue shardingValue) {
        if(availableTargetNames.size() > 0)
            return availableTargetNames.iterator().next().toString();
        return null;
    }

    public Collection<String> doInSharding(Collection availableTargetNames, ShardingValue shardingValue) {
        return availableTargetNames;
    }

    public Collection<String> doBetweenSharding(Collection availableTargetNames, ShardingValue shardingValue) {
        return null;
    }
}
