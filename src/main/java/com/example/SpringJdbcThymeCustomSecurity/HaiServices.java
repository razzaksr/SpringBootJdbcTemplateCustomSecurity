package com.example.SpringJdbcThymeCustomSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class HaiServices {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String insertOne(Hai hai){
        String info=hai.getName()+" has inserted";
        jdbcTemplate.update("insert into hai values(hai_seq.nextval,?,?)",new Object[]{hai.getName(),hai.getPrice()});
        return info;
    }

    public String updateOne(Hai hai){
        String info=hai.getId()+" has updated";
        jdbcTemplate.update("update hai set name=?, price=? where id=?",new Object[]{hai.getName(),hai.getPrice(),hai.getId()});
        return info;
    }

    public String deleteOne(int id){
        String info=listOne(id).get().getName()+" has deleted";
        jdbcTemplate.update("delete from hai where id=?",new Object[]{id});
        return info;
    }

    public Optional<Hai> listOne(int id){
        return Optional.of(jdbcTemplate.queryForObject("select * from hai where id=?",new Object[]{id},new BeanPropertyRowMapper<Hai>(Hai.class)));
    }

    public List<Hai> listAll(){
        return jdbcTemplate.query("select * from hai",new HaiMapper());
    }

    class HaiMapper implements RowMapper<Hai>{
        @Override
        public Hai mapRow(ResultSet rs, int rowNum) throws SQLException {
            Hai hai=new Hai();
            hai.setId(rs.getInt("id"));
            hai.setName(rs.getString("name"));
            hai.setPrice(rs.getInt("price"));
            return hai;
        }
    }
}
