package com.ulanzhasssanov.CrudAppWithJUnit.controller;

import com.ulanzhasssanov.CrudAppWithJUnit.model.Label;
import com.ulanzhasssanov.CrudAppWithJUnit.repository.JdbcLabelRepositoryImpl;

import java.util.List;

public class LabelController {
    JdbcLabelRepositoryImpl jdbcLabelRepository = new JdbcLabelRepositoryImpl();

    public Label saveLabel(Label label){
        Label savedLabel = jdbcLabelRepository.save(label);
        return savedLabel;
    }

    public List<Label> getAllLabels(){
        List<Label> labels = jdbcLabelRepository.getAll();
        return labels;
    }

    public Label getLabelById(Integer id){
        Label label = jdbcLabelRepository.getById(id);
        return label;
    }

    public Label updateLabel(Label label){
        Label updatedLabel = jdbcLabelRepository.update(label);
        return updatedLabel;
    }

    public void deleteLabel(Integer id){
        jdbcLabelRepository.deleteById(id);
    }
}
