package com.todo.server.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.todo.common.exception.RestException;
import com.todo.server.entity.todo.Todo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class TodoRestControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void Todo_FullTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // 1. create check start
    	String requestJson = "{\"title\":\"Todo Create\"}";
        HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
        String firstTodoId = restTemplate.postForObject("/todos/", entity, String.class);
        List<Todo> todo = Arrays.asList(this.restTemplate.getForObject("/todos/select", Todo[].class));
        
        assertThat(String.valueOf(todo.get(0).getId())).isEqualTo(firstTodoId);
        // 1. create check end

        // 2. update check start
        requestJson = "{\"title\":\"Todo Create Edit\"}";
        entity = new HttpEntity<String>(requestJson, headers);
        ResponseEntity<String> firstTodoEditId = restTemplate.exchange("/todos/" + firstTodoId, HttpMethod.PUT, entity, String.class);
        todo = Arrays.asList(this.restTemplate.getForObject("/todos/select", Todo[].class));
    
        assertThat(todo.get(0).getTitle()).isEqualTo("Todo Create Edit");
        // 2. update check end

        // 3. create2 check start
        requestJson = "{\"title\":\"Todo Create\",\"referIds\":[" + firstTodoId + "]}";
        entity = new HttpEntity<String>(requestJson, headers);
        String secondTodoId = restTemplate.postForObject("/todos/", entity, String.class);
        todo = Arrays.asList(this.restTemplate.getForObject("/todos/select", Todo[].class));

        assertThat(todo.size()).isEqualTo(2);
        // 3. create2 check end
        
        // 4. refer select list check start
        todo = Arrays.asList(this.restTemplate.getForObject("/todos/" + firstTodoId + "/select", Todo[].class));

        assertThat(todo.size()).isEqualTo(0);
        // 4. refer select list check end
        
        // 5. todo status change start
        ResponseEntity<String> statusUpdate = restTemplate.exchange("/todos/" + secondTodoId + "/status", HttpMethod.PUT, entity, String.class);
        
        assertThat(statusUpdate.getBody()).isEqualTo(String.valueOf(secondTodoId));
        // 5. todo status change fail end
        
    }
}
