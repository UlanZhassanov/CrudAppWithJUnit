package com.ulanzhasssanov.CrudAppWithJUnit.service;

import com.ulanzhasssanov.CrudAppWithJUnit.model.Label;
import com.ulanzhasssanov.CrudAppWithJUnit.repository.LabelRepository;

import java.util.List;


public class LabelService {
    private final LabelRepository labelRepository;

    public LabelService(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }

    public Label getLabelById(Integer id){
        return labelRepository.getById(id);
    }

    public List<Label> getAllLabels(){
        return labelRepository.getAll();
    }

    public Label saveLabel(Label label){
        return labelRepository.save(label);
    }

    public Label updateLabel(Label label){
        return labelRepository.update(label);
    }

    public void deleteLabelById(Integer id){
        labelRepository.deleteById(id);
    }
}
