package com.ulanzhasssanov.CrudAppWithJUnit.controller;

import com.ulanzhasssanov.CrudAppWithJUnit.model.Writer;
import com.ulanzhasssanov.CrudAppWithJUnit.repository.JdbcWriterRepositoryImpl;

import java.util.List;

public class WriterController {
    JdbcWriterRepositoryImpl jdbcWriterRepository = new JdbcWriterRepositoryImpl();

    public Writer saveWriter(Writer writer){
        Writer savedWriter = jdbcWriterRepository.save(writer);
        return savedWriter;
    }

    public List<Writer> getAllWriters(){
        List<Writer> writers = jdbcWriterRepository.getAll();
        return writers;
    }

    public Writer getWriterById(Integer id){
        Writer writer = jdbcWriterRepository.getById(id);
        return writer;
    }

    public Writer updateWriter(Writer writer){
        Writer updatedWriter = jdbcWriterRepository.update(writer);
        return updatedWriter;
    }

    public void deleteWriter(Integer id){
        jdbcWriterRepository.deleteById(id);
    }
}
