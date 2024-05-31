package com.ulanzhasssanov.CrudAppWithJUnit.service;

import com.ulanzhasssanov.CrudAppWithJUnit.enums.Status;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Label;
import com.ulanzhasssanov.CrudAppWithJUnit.repository.LabelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LabelServiceTest {

    @Mock
    private LabelRepository labelRepository;

    @InjectMocks
    private LabelService labelService;

    private Label mockLabel;

    @BeforeEach
    void setUp(){
        mockLabel = new Label(1, "Test Label", Status.ACTIVE);
    }

    @Test
    void testGetLabelById() {
        when(labelRepository.getById(anyInt())).thenReturn(mockLabel);

        Label label = labelService.getLabelById(1);

        assertLabelEquals(mockLabel, label);
        verify(labelRepository, times(1)).getById(1);
    }

    @Test
    void testGetAllLabels() {
        Label label1 = new Label(1, "Label 1", Status.ACTIVE);
        Label label2 = new Label(2, "Label 2", Status.DELETED);
        List<Label> mockLabels = Arrays.asList(label1, label2);

        when(labelRepository.getAll()).thenReturn(mockLabels);

        List<Label> labels = labelService.getAllLabels();

        assertEquals(2, labels.size());
        assertLabelEquals(label1, labels.get(0));
        assertLabelEquals(label2, labels.get(1));
        verify(labelRepository, times(1)).getAll();
    }

    @Test
    void testSaveLabel() {
        when(labelRepository.save(any(Label.class))).thenReturn(mockLabel);

        Label result = labelService.saveLabel(mockLabel);

        assertLabelEquals(mockLabel, result);
        verify(labelRepository, times(1)).save(mockLabel);
    }

    @Test
    void testUpdateLabel() {
        when(labelRepository.update(any(Label.class))).thenReturn(mockLabel);

        Label result = labelService.updateLabel(mockLabel);

        assertLabelEquals(mockLabel, result);
        verify(labelRepository, times(1)).update(mockLabel);
    }

    @Test
    void testDeleteLabelById() {
        int labelId = 1;

        labelService.deleteLabelById(labelId);

        verify(labelRepository, times(1)).deleteById(labelId);
    }

    private void assertLabelEquals(Label expected, Label actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

}
