package com.ulanzhasssanov.CrudAppWithJUnit.service;

import com.ulanzhasssanov.CrudAppWithJUnit.model.Writer;
import com.ulanzhasssanov.CrudAppWithJUnit.repository.WriterRepository;

import java.util.List;

public class WriterService {
    private final WriterRepository writerRepository;

    public WriterService(WriterRepository writerRepository) {
        this.writerRepository = writerRepository;
    }

    public Writer getWriterById(Integer id){
        return writerRepository.getById(id);
    }

    public List<Writer> getAllWriters(){
        return writerRepository.getAll();
    }

    public Writer saveWriter(Writer writer){
        return writerRepository.save(writer);
    }

    public Writer updateWriter(Writer writer){
        return writerRepository.update(writer);
    }

    public void deleteWriterById(Integer id){
        writerRepository.deleteById(id);
    }
}
