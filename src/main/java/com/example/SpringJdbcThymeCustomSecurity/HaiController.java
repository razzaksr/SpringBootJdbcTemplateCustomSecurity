package com.example.SpringJdbcThymeCustomSecurity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hai")
public class HaiController {
    @Autowired
    private HaiServices services;
    private Logger logger= LoggerFactory.getLogger(HaiController.class);

    @PostMapping("/")
    public String addOne(@RequestBody Hai hai){
        logger.trace("Received Entity object to be inserted is "+hai.getName());
        return services.insertOne(hai);
    }

    @PutMapping("/")
    public String changeOne(@RequestBody Hai hai){
        logger.debug("Received Entity object to be updated is "+hai.getName());
        return services.updateOne(hai);
    }

    @DeleteMapping("/{id}")
    public String removeOne(@PathVariable("id") int id){
        logger.error("Received id to be removed is "+id);
        return services.deleteOne(id);
    }

    @GetMapping("/{id}")
    public Optional<Hai> readOne(@PathVariable("id") int id){
        logger.info("Received id to be read is "+id);
        return services.listOne(id);
    }

    @GetMapping("/")
    public List<Hai> readAll(){
        logger.info("About to fetch all data");
        return services.listAll();
    }
}
