package com.todo.server.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.todo.common.exception.RestException;
import com.todo.server.dto.todo.TodoCreateRequestDto;
import com.todo.server.dto.todo.TodoUpdateRequestDto;
import com.todo.server.entity.todo.Todo;
import com.todo.server.repository.TodoReferRepository;
import com.todo.server.repository.TodoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TodoServiceTest {
    @Autowired
    TodoService todoService;
    
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
    public void Todo_create_titleOnly() {
    	TodoCreateRequestDto todoCreateRequestDto = TodoCreateRequestDto.builder().title("테스트 title Only").build();
    	
    	todoService.create(todoCreateRequestDto);
    	
        Todo todo = todoRepository.findAll().get(0);
        
        assertThat(todo.getTitle()).isEqualTo(todoCreateRequestDto.getTitle());
    }
    
    @Test
    public void Todo_create_titleAndReferCount1() {
    	TodoCreateRequestDto dtoTestValue1 = TodoCreateRequestDto.builder().title("테스트 title And Refer 1").build();
    	Long dtoTestValue1Id = todoService.create(dtoTestValue1);
    	
    	List<Long> idList = new ArrayList<Long>();
    	idList.add(dtoTestValue1Id);
    	
    	TodoCreateRequestDto dtoTestValue2 = TodoCreateRequestDto.builder().title("테스트 title And Refer 2").referIds(idList).build();
    	Long dtoTestValue2Id = todoService.create(dtoTestValue2);
    	
		Optional<Todo> getTodo = todoRepository.findById(dtoTestValue2Id);
        
        assertThat(getTodo.get().getTitle()).isEqualTo(dtoTestValue2.getTitle());
        assertThat(getTodo.get().getTodoRefer().get(0).getTodoReferId().getReferId())
        			.isEqualTo(dtoTestValue2.getReferIds().get(0));

    }
    
    @Test
    public void Todo_create_titleAndReferCount2() {
    	TodoCreateRequestDto dtoTestValue1 = TodoCreateRequestDto.builder().title("테스트 title And Refer 1").build();
    	Long dtoTestValue1Id = todoService.create(dtoTestValue1);
    	
    	TodoCreateRequestDto dtoTestValue2 = TodoCreateRequestDto.builder().title("테스트 title And Refer 2").build();
    	Long dtoTestValue2Id = todoService.create(dtoTestValue2);
    	
    	List<Long> idList = new ArrayList<Long>();
    	idList.add(dtoTestValue1Id);
    	idList.add(dtoTestValue2Id);

    	TodoCreateRequestDto dtoTestValue3 = TodoCreateRequestDto.builder().title("테스트 title And Refer 3").referIds(idList).build();
    	Long dtoTestValue3Id = todoService.create(dtoTestValue3);
    	
		Optional<Todo> getTodo = todoRepository.findById(dtoTestValue3Id);
        
        assertThat(getTodo.get().getTitle()).isEqualTo(dtoTestValue3.getTitle());
        assertThat(getTodo.get().getTodoRefer().size()).isEqualTo(dtoTestValue3.getReferIds().size());
    }
    
    @Test
    public void Todo_update_titleOnly() {
    	TodoCreateRequestDto dtoTestValue1 = TodoCreateRequestDto.builder().title("테스트 update title Only").build();
    	Long dtoTestValue1Id = todoService.create(dtoTestValue1);
    	
    	TodoUpdateRequestDto dtoUpdate = TodoUpdateRequestDto.builder().title("테스트 update title Only modify").build();
	
    	todoService.update(dtoTestValue1Id, dtoUpdate);

        Todo todo = todoRepository.findAll().get(0);
        
        assertThat(todo.getTitle()).isEqualTo(dtoUpdate.getTitle());
    }
    
    @Test
    public void Todo_update_titleAndReferCount1() {
    	TodoCreateRequestDto dtoTestValue1 = TodoCreateRequestDto.builder().title("테스트 update title And Refer 1").build();
    	Long dtoTestValue1Id = todoService.create(dtoTestValue1);
    	
    	TodoCreateRequestDto dtoTestValue2 = TodoCreateRequestDto.builder().title("테스트 update title And Refer 2").build();
    	Long dtoTestValue2Id = todoService.create(dtoTestValue2);
    	
    	List<Long> idList = new ArrayList<Long>();
    	idList.add(dtoTestValue1Id);
    	
    	TodoCreateRequestDto dtoTestValue3 = TodoCreateRequestDto.builder().title("테스트 update title And Refer 3").referIds(idList).build();
    	Long dtoTestValue3Id = todoService.create(dtoTestValue3);
    	
		Optional<Todo> getTodo1 = todoRepository.findById(dtoTestValue3Id);

    	List<Long> idListUpdate = new ArrayList<Long>();
    	idListUpdate.add(dtoTestValue2Id);
    	
    	TodoUpdateRequestDto dtoUpdate = TodoUpdateRequestDto.builder().title("테스트 update title And Refer 3 modify").referIds(idListUpdate).build();

    	todoService.update(dtoTestValue3Id, dtoUpdate);
    	
		Optional<Todo> getTodo2 = todoRepository.findById(dtoTestValue3Id);
		
        assertThat(getTodo2.get().getTitle()).isEqualTo(dtoUpdate.getTitle());
        assertThat(getTodo2.get().getTodoRefer().get(0).getTodoReferId().getReferId())
        			.isEqualTo(dtoUpdate.getReferIds().get(0));

    }
    
    @Test
    public void Todo_updateStatus_todoOne() {
    	TodoCreateRequestDto dtoTestValue1 = TodoCreateRequestDto.builder().title("테스트 update title Only").build();
    	Long dtoTestValue1Id = todoService.create(dtoTestValue1);
    	
    	todoService.updateStatus(dtoTestValue1Id);
    	
        Todo todo = todoRepository.findAll().get(0);
        
        assertThat(todo.getIsfinish(), is(true));
    }
 
    @Test(expected = RestException.class)
    public void Todo_updateStatus_todoNotAllFinish() {
    	TodoCreateRequestDto dtoTestValue1 = TodoCreateRequestDto.builder().title("테스트 title And Refer 1").build();
    	Long dtoTestValue1Id = todoService.create(dtoTestValue1);
    	
    	List<Long> idList = new ArrayList<Long>();
    	idList.add(dtoTestValue1Id);
    	
    	TodoCreateRequestDto dtoTestValue2 = TodoCreateRequestDto.builder().title("테스트 title And Refer 2").referIds(idList).build();
    	todoService.create(dtoTestValue2);
    	
    	todoService.updateStatus(dtoTestValue1Id);        
    }

    @Test
    public void Todo_updateStatus_todoAllFinish() {
    	TodoCreateRequestDto dtoTestValue1 = TodoCreateRequestDto.builder().title("테스트 title And Refer 1").build();
    	Long dtoTestValue1Id = todoService.create(dtoTestValue1);
    	
    	List<Long> idList = new ArrayList<Long>();
    	idList.add(dtoTestValue1Id);
    	
    	TodoCreateRequestDto dtoTestValue2 = TodoCreateRequestDto.builder().title("테스트 title And Refer 2").referIds(idList).build();
    	Long dtoTestValue2Id = todoService.create(dtoTestValue2);
    	
    	todoService.updateStatus(dtoTestValue2Id);
    	todoService.updateStatus(dtoTestValue1Id);
    	
        assertThat(todoRepository.findAll().get(0).getIsfinish(), is(true));
        assertThat(todoRepository.findAll().get(1).getIsfinish(), is(true));

    }
    
}
