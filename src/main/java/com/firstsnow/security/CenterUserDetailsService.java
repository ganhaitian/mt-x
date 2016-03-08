package com.firstsnow.security;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户中心详细信息服务
 * Created by ganhaitian on 2016/3/7.
 */
public class CenterUserDetailsService extends JdbcDaoImpl implements UserDetailsService {


    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetails> users = loadUsersByUsername(username);

        if (users.size() == 0) {
            logger.debug("Query returned no results for user '" + username + "'");

            throw new UsernameNotFoundException(
                    messages.getMessage("JdbcDaoImpl.notFound", new Object[]{username}, "Username {0} not found"), username);
        }

        UserDetails user = users.get(0);

        List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");
        dbAuths.add(authority);

        return createUserDetails(username,user,dbAuths);
    }

    /**
     * Can be overridden to customize the creation of the final UserDetailsObject which is
     * returned by the <tt>loadUserByUsername</tt> method.
     *
     * @param username the name originally passed to loadUserByUsername
     * @param userFromUserQuery the object returned from the execution of the
     * @param combinedAuthorities the combined array of authorities from all the authority loading queries.
     * @return the final UserDetails which should be used in the system.
     */
    protected UserDetails createUserDetails(String username, UserDetails userFromUserQuery,
                                            List<GrantedAuthority> combinedAuthorities) {
        String returnUsername = userFromUserQuery.getUsername();

        /*if (!usernameBasedPrimaryKey) {
            returnUsername = username;
        }*/

        return new User(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),
                true, true, true, combinedAuthorities);
    }

    /**
     * Executes the SQL <tt>usersByUsernameQuery</tt> and returns a list of UserDetails objects.
     * There should normally only be one matching user.
     */
    protected List<UserDetails> loadUsersByUsername(String username) {
        return getJdbcTemplate().query("select id,token,enabled from user where id = ?",
                new String[]{username}, new RowMapper<UserDetails>() {
                    public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
                        String username = rs.getString(1);
                        String password = rs.getString(2);
                        boolean enabled = rs.getBoolean(3);
                        return new User(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
                    }

                });
    }


}
