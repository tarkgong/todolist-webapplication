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
        //given
    	todoRepository.save(Todo.builder()
    							.title("테스트 할일")
    							.isfinish(false)
    							.build());

        //when
        List<Todo> todoList = todoRepository.findAll();

        //then
        Todo todo = todoList.get(0);
        assertThat(todo.getTitle(), is("테스트 할일"));
    }

    @Test
    public void bateTime_create () {
        //given
        LocalDateTime now = LocalDateTime.now();
    	todoRepository.save(Todo.builder()
				.title("테스트 할일")
				.isfinish(false)
				.build());
    	
        //when
        List<Todo> todoList = todoRepository.findAll();

        //then
        Todo todo = todoList.get(0);
        assertThat(todo.getTitle(), is("테스트 할일"));
        assertTrue(todo.getCreatedDate().isAfter(now));
        assertTrue(todo.getModifiedDate().isAfter(now));
    }
    
    @Test
    public void Todo_findAllByIsfinish() {
        //given
    	todoRepository.save(Todo.builder()
    							.title("테스트 할일")
    							.isfinish(false)
    							.build());

    	todoRepository.save(Todo.builder()
				.title("테스트 할일2")
				.isfinish(false)
				.build());
    	
    	todoRepository.save(Todo.builder()
				.title("테스트 할일3")
				.isfinish(true)
				.build());
        //when
        List<Todo> todoList = todoRepository.findAllByIsfinish(false);

        //then
        assertThat(todoList.size(), is(2));
    }
    
    @Test
    public void Todo_findAllByTodoReferTodoReferIdReferIdAndIsfinish() {
        //given
    	Long id = todoRepository.save(Todo.builder()
    							.title("테스트 할일")
    							.isfinish(false)
    							.build()).getId();

    	Long id2 = todoRepository.save(Todo.builder()
				.title("테스트 할일2")
				.isfinish(false)
				.build()).getId();
    	
    	todoReferRepository.save(TodoRefer.builder()
				.todoReferId(TodoReferId
						.builder()
						.id(id2)
						.referId(id)
						.build())
				.build());
    	
        //when
        List<Todo> todoList = todoRepository.findAllByTodoReferTodoReferIdReferIdAndIsfinish(id, false);
        
        //then
        assertThat(todoList.get(0).getTitle(), is("테스트 할일2"));
        assertThat(todoList.size(), is(1));
    }

    @Test
    public void Todo_findTodoListForSelect() {
        //given
    	Long id = todoRepository.save(Todo.builder()
    							.title("테스트 할일")
    							.isfinish(false)
    							.build()).getId();

    	Long id2 = todoRepository.save(Todo.builder()
				.title("테스트 할일2")
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
				.title("테스트 할일3")
				.isfinish(false)
				.build()).getId();
    	
        //when
        List<Todo> todoList = todoRepository.findTodoListForSelect(id);
        
        //then
        assertThat(todoList.get(0).getId(), is(id3));
        assertThat(todoList.size(), is(1));
    }

}
