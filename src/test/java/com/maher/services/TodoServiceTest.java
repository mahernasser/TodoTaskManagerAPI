package com.maher.services;

import com.maher.enitites.Todo;
import com.maher.repositories.TodoRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TodoServiceTest {

    @MockBean
    private TodoRepo todoRepository;


    private TodoService todoService;


    @Before
    public void setUp() {
        todoService = new TodoService(todoRepository);
    }

    @Test
    public void WhenFindAll_ThenReturnList() {
        Todo todo = new Todo("1", "title", "description");
        Todo todo1 = new Todo("2", "title2", "description2");
        Todo todo2 = new Todo("3", "title3", "description3");
        List<Todo> todos = Arrays.asList(todo, todo1, todo2);
        Mockito.when(todoRepository.findAll()).thenReturn(todos);
        assertThat(todoService.getAllTasks()).hasSize(3).contains(todo2);
    }

    @Test
    public void WhenFindById_ThenReturnTodo() {
        Todo todo = new Todo("1", "title", "description");
        given(todoRepository.findById(anyString())).willReturn(Optional.of(todo));
        assertThat(todoService.findById("5")).isEqualTo(todo);
    }

    @Test
    public void WhenNotFountById_ThenThrowNotFoundException() {
        given(todoRepository.findById(anyString())).willReturn(Optional.empty());
        assertThatThrownBy(() -> todoService.findById("5"))
                .isInstanceOf(com.maher.exceptions.types.NotFoundException.class)
                .hasMessage("No Record associated with this id 5");
    }

    @Test
    public void whenClearAllTodos_thenSuccess() {
        todoService.clearAllTodos();
        verify(todoRepository, times(1)).deleteAll();
    }

}