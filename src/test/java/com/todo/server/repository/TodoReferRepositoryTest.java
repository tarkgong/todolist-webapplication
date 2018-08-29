package com.todo.server.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

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
        //given
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
        List<TodoRefer> todoReferList = todoReferRepository.findAll();

        //then
        TodoRefer todoRefer = todoReferList.get(0);
        assertThat(todoRefer.getTodoReferId().getId(), is(id2));
        assertThat(todoRefer.getTodoReferId().getReferId(), is(id));
    }
    
    @Test
    @Transactional
    public void TodoRefer_deleteByTodoReferIdId() {
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

        List<TodoRefer> todoRefer1
    	= todoReferRepository.findAll();
        System.out.println(todoRefer1.size());

		todoReferRepository.deleteByTodoReferIdId(id2);
    	
        //when
        List<TodoRefer> todoRefer2
        	= todoReferRepository.findAll();
        System.out.println("---------------------------");

        System.out.println(todoRefer2.size());
        //then
        assertThat(todoRefer2.size(), is(0));
    }
}
