package com.ulanzhasssanov.CrudAppWithJUnit.service;

import com.ulanzhasssanov.CrudAppWithJUnit.enums.Status;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Writer;
import com.ulanzhasssanov.CrudAppWithJUnit.model.Post;
import com.ulanzhasssanov.CrudAppWithJUnit.repository.WriterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WriterServiceTest {

    @Mock
    private WriterRepository writerRepository;

    @InjectMocks
    private WriterService writerService;

    private List<Post> posts;
    private Writer mockWriter;

    @BeforeEach
    void setUp(){
        posts = new ArrayList<>();
        mockWriter = new Writer(1, "TestName", "TestLastName", posts, Status.ACTIVE);
    }


    @Test
    void testGetWriterById() {
        when(writerRepository.getById(anyInt())).thenReturn(mockWriter);

        Writer writer = writerService.getWriterById(1);

        assertWriterEquals(mockWriter, writer);
        verify(writerRepository, times(1)).getById(1);
    }

    @Test
    void testGetAllWriters() {
        Writer writer1 = new Writer(1, "TestName1", "TestLastName1", posts, Status.ACTIVE);
        Writer writer2 = new Writer(2, "TestName2", "TestLastName2", posts, Status.ACTIVE);
        List<Writer> mockWriters = Arrays.asList(writer1, writer2);

        when(writerRepository.getAll()).thenReturn(mockWriters);

        List<Writer> writers = writerService.getAllWriters();

        assertEquals(2, writers.size());
        assertWriterEquals(writer1, writers.get(0));
        assertWriterEquals(writer2, writers.get(1));
        verify(writerRepository, times(1)).getAll();
    }

    @Test
    void testSaveWriter() {
        when(writerRepository.save(any(Writer.class))).thenReturn(mockWriter);

        Writer result = writerService.saveWriter(mockWriter);

        assertWriterEquals(mockWriter, result);
        verify(writerRepository, times(1)).save(mockWriter);
    }

    @Test
    void testUpdateWriter() {
        when(writerRepository.update(any(Writer.class))).thenReturn(mockWriter);

        Writer result = writerService.updateWriter(mockWriter);

        assertWriterEquals(mockWriter, result);
        verify(writerRepository, times(1)).update(mockWriter);
    }

    @Test
    void testDeleteWriterById() {
        int writerId = 1;

        writerService.deleteWriterById(writerId);

        verify(writerRepository, times(1)).deleteById(writerId);
    }

    private void assertWriterEquals(Writer expected, Writer actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        assertEquals(expected.getStatus(), actual.getStatus());
        assertEquals(expected.getPosts(), actual.getPosts());
    }

}
