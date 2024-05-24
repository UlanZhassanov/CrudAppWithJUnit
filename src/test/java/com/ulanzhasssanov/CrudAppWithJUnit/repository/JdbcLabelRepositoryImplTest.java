package com.ulanzhasssanov.CrudAppWithJUnit.repository;

import com.ulanzhasssanov.CrudAppWithJUnit.enums.Status;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Label;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JdbcLabelRepositoryImplTest {

    JdbcLabelRepositoryImpl jdbcLabelRepository = new JdbcLabelRepositoryImpl();
    Label label = new Label();

    @BeforeEach
    public void setUp(){
        label.setName("Name");
        label.setStatus(Status.ACTIVE);
    }

    @Test
    public void saveTest() {
        Label savedLabel = jdbcLabelRepository.save(label);
        assertEquals(label.getName(), savedLabel.getName());
        assertEquals(label.getStatus(), savedLabel.getStatus());


    }

    @AfterEach
    public void tearDown(){

    }
}
