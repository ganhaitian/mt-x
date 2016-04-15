package com.firstsnow.dao;

import com.firstsnow.bean.UserData;
import com.firstsnow.bean.UserInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

/**
 * Created by lenovo on 2016/4/7.
 */
@Repository
public class DataDao extends JdbcTemplate{

    @Resource(name = "shardingDataSource")
    private DataSource dataSource;

    @PostConstruct
    public void initDataSource() {
        super.setDataSource(this.dataSource);
    }

    public void insertUserData(UserData userData){
        update("insert into user_data (player_id,data) values(?,?)",
                userData.getPlayerId(),userData.getData());
    }

    public static void main(String[] args){
        ApplicationContext beanContext = new ClassPathXmlApplicationContext("classpath:app-context.xml");

        DataDao dataDao = beanContext.getBean(DataDao.class);
        String content = null;
        try {
            content = FileUtils.readFileToString(new File("c://Users//lenovo//1.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0;i < 10000;i++){
            UserData userData = new UserData();
            userData.setPlayerId("001_" + i + "8");
            userData.setData(content);

            dataDao.insertUserData(userData);
        }
    }

}
