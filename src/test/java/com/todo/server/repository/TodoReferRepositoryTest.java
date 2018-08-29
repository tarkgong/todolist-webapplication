package com.todo.server.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

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
public class TodoReferRepositoryTest {

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
    public void TodoRefer_create() {
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

		Optional<TodoRefer> todoRefer
			= todoReferRepository.findById(TodoReferId
										.builder()
										.id(id2)
										.referId(id)
										.build());

        assertThat(todoRefer.get().getTodoReferId().getId(), is(id2));
        assertThat(todoRefer.get().getTodoReferId().getReferId(), is(id));
    }
    
    @Test
    @Transactional
    public void TodoRefer_deleteByTodoReferIdId() {
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

		todoReferRepository.deleteByTodoReferIdId(id2);
    	
		Optional<Todo> todo = todoRepository.findById(id2);

        assertThat(todo.get().getTodoRefer(), is(nullValue()));
    }
}
