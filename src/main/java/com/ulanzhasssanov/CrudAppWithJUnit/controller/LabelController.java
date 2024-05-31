package com.ulanzhasssanov.CrudAppWithJUnit.controller;

import com.ulanzhasssanov.CrudAppWithJUnit.model.Label;
import com.ulanzhasssanov.CrudAppWithJUnit.repository.JdbcLabelRepositoryImpl;
import com.ulanzhasssanov.CrudAppWithJUnit.service.LabelService;

import java.util.List;

public class LabelController {
    private LabelService labelService = new LabelService(new JdbcLabelRepositoryImpl());

    public Label saveLabel(Label label){
        return labelService.saveLabel(label);
    }

    public List<Label> getAllLabels(){
        return labelService.getAllLabels();
    }

    public Label getLabelById(Integer id){
        return labelService.getLabelById(id);
    }

    public Label updateLabel(Label label){
        return labelService.updateLabel(label);
    }

    public void deleteLabel(Integer id){
        labelService.deleteLabelById(id);
    }
}
