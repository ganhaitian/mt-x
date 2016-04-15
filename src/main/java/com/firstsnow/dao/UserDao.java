package com.firstsnow.dao;

import com.firstsnow.bean.UserInfo;
import org.apache.commons.io.FileUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;

/**
 * Created by ganhaitian on 2016/3/17.
 */
@Repository
public class UserDao extends JdbcTemplate{

    @Resource(name = "shardingDataSource")
    private DataSource dataSource;

    @PostConstruct
    public void initDataSource() {
        super.setDataSource(this.dataSource);
    }

    public UserInfo getUserByPlayerId(String playerId){
        return queryForObject("select * from user_info where player_id = ?",
                new Object[]{playerId},
                BeanPropertyRowMapper.newInstance(UserInfo.class));
    }

    public void insertUserInfo(UserInfo userInfo){
        update("insert into user_info (user_id,player_id,user_name,platform,player_info) values(?,?,?,?,?)",
                userInfo.getUserId(),userInfo.getPlayerId(),userInfo.getUserName(),userInfo.getPlatform(),userInfo.getPlayerInfo());
    }

    public static void main(String[] args){
        ApplicationContext beanContext = new ClassPathXmlApplicationContext("classpath:app-context.xml");

        UserDao userDao = beanContext.getBean(UserDao.class);
        UserInfo user = userDao.getUserByPlayerId("01_8888");
        String content = null;
        try {
            content = FileUtils.readFileToString(new File("c://Users//lenovo//PlayerInfo.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0;i < 10000;i++){
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(String.valueOf(i));
            userInfo.setPlayerId("001_" + i + "8");
            userInfo.setUserName("sdfdfd");
            userInfo.setPlatform("233");
            userInfo.setPlayerInfo(content);

            userDao.insertUserInfo(userInfo);
        }

    }

}
