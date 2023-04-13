package com.example.SpringJdbcThymeCustomSecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    JdbcTemplate template;

    private Logger logger= LoggerFactory.getLogger(UserService.class);

    public String insertOne(HaiUsers haiUser){
        String info=haiUser.getUsername()+" has inserted";
        template.update("insert into hai_users values(hai_users_seq.nextval,?,?,?,?)",new Object[]{haiUser.getUsername(),haiUser.getPassword(),haiUser.getRole(),haiUser.getStatus()});
        return info;
    }
    public String updateAttempt(HaiUsers haiUser){
        String info=haiUser.getUsername()+" attempt has updated";
        template.update("update hai_users set attempts=? where id=?",new Object[]{haiUser.getAttempts(),haiUser.getId()});
        return info;
    }
    public String updateStatus(HaiUsers haiUser){
        String info=haiUser.getUsername()+" status has updated";
        template.update("update hai_users set status=? where id=?",new Object[]{haiUser.getStatus(),haiUser.getId()});
        return info;
    }

    public HaiUsers findByUsername(String username){
        HaiUsers hai=template.queryForObject("select * from hai_users where username=?",new Object[]{username},new BeanPropertyRowMapper<>(HaiUsers.class));
        logger.info(hai.toString());
        return hai;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HaiUsers haiUsers=findByUsername(username);
        if(haiUsers==null)
            throw new UsernameNotFoundException("Invalid username");
        return haiUsers;
    }

    public void increaseChances(HaiUsers haiUsers){
        haiUsers.setAttempts(haiUsers.getAttempts()+1);
        logger.info("Chances are left "+haiUsers.getAttempts());
        updateAttempt(haiUsers);
    }

    public void locking(HaiUsers haiUsers){
        haiUsers.setStatus(0);
        logger.error("Locked");
        updateStatus(haiUsers);// update the status
    }
}
