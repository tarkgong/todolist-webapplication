package com.todo.server.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.todo.server.entity.todo.Todo;
import com.todo.server.entity.todo.TodoRefer;
import com.todo.server.entity.todo.TodoReferId;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoRepositoryTest {

    @Autowired
    TodoRepository todoRepository;
 
    @Autowired
    TodoReferRepository todoReferRepository;
    
    @After
    public void cleanup() {
    	todoReferRepository.deleteAll();
    	todoRepository.deleteAll();
    }

    @Test
    public void Todo_create() {
    	todoRepository.save(Todo.builder()
    							.title("Todo Create")
    							.isfinish(false)
    							.build());

        List<Todo> todoList = todoRepository.findAll();

        Todo todo = todoList.get(0);
        assertThat(todo.getTitle(), is("Todo Create"));
    }

    @Test
    public void baseTime_create () {
        LocalDateTime now = LocalDateTime.now();
    	todoRepository.save(Todo.builder()
				.title("Todo Create")
				.isfinish(false)
				.build());
    	
        List<Todo> todoList = todoRepository.findAll();

        Todo todo = todoList.get(0);
        assertThat(todo.getTitle(), is("Todo Create"));
        assertTrue(todo.getCreatedDate().isAfter(now));
        assertTrue(todo.getModifiedDate().isAfter(now));
    }
    
    @Test
    public void Todo_findAllByIsfinish() {
    	todoRepository.save(Todo.builder()
    							.title("Todo Create")
    							.isfinish(false)
    							.build());

    	todoRepository.save(Todo.builder()
				.title("Todo Create2")
				.isfinish(false)
				.build());
    	
    	todoRepository.save(Todo.builder()
				.title("Todo Create3")
				.isfinish(true)
				.build());

    	List<Todo> todoList = todoRepository.findAllByIsfinish(false);

        assertThat(todoList.size(), is(2));
    }
    
    @Test
    public void Todo_findAllByTodoReferTodoReferIdReferIdAndIsfinish() {
    	Long id = todoRepository.save(Todo.builder()
    							.title("Todo Create")
    							.isfinish(false)
    							.build()).getId();

    	Long id2 = todoRepository.save(Todo.builder()
				.title("Todo Create2")
				.isfinish(false)
				.build()).getId();
    	
    	todoReferRepository.save(TodoRefer.builder()
				.todoReferId(TodoReferId
						.builder()
						.id(id2)
						.referId(id)
						.build())
				.build());
    	
        List<Todo> todoList = todoRepository.findAllByTodoReferTodoReferIdReferIdAndIsfinish(id, false);
        
        assertThat(todoList.get(0).getTitle(), is("Todo Create2"));
        assertThat(todoList.size(), is(1));
    }

    @Test
    public void Todo_findTodoListForSelect() {
    	Long id = todoRepository.save(Todo.builder()
    							.title("Todo Create")
    							.isfinish(false)
    							.build()).getId();

    	Long id2 = todoRepository.save(Todo.builder()
				.title("Todo Create2")
				.isfinish(false)
				.build()).getId();
    	
    	todoReferRepository.save(TodoRefer.builder()
				.todoReferId(TodoReferId
						.builder()
						.id(id2)
						.referId(id)
						.build())
				.build());
    	
    	Long id3 = todoRepository.save(Todo.builder()
				.title("Todo Create3")
				.isfinish(false)
				.build()).getId();
    	
        List<Todo> todoList = todoRepository.findTodoListForSelect(id);
        
        assertThat(todoList.get(0).getId(), is(id3));
        assertThat(todoList.size(), is(1));
    }

}
