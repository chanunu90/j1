package org.zerock.j1.repository;


import java.util.Optional;
import java.util.stream.LongStream;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.j1.domain.Todo;
import org.zerock.j1.dto.TodoDTO;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class TodoRepositoryTests {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private TodoRepository todoRepository;

    @Test // id가 없으면 무조건 insert다 라고 생각한다. 
    public void testInsert(){

        LongStream.rangeClosed(1, 100).forEach(i->{

            Todo todo = Todo.builder().title("Title" + i).build();

            Todo result = todoRepository.save(todo);

            log.info(result);

        });



    }

    @Test 
    public void testPageing(){

        Pageable pageable = PageRequest.of(0, 110, Sort.by("tno").descending());

        Page<Todo> result  = todoRepository.findAll(pageable);

        log.info(result);

    }

    @Test
    public void testRead(){
        Long tno = 100L;

        Optional<Todo> result = todoRepository.findById(tno);

        Todo entity = result.orElseThrow();

        log.info("Entity.........................");
        log.info(entity);


        // 왼쪽에 있는 엔티티를 => 해당 dto로 변경된다.
        TodoDTO dto = modelMapper.map(entity , TodoDTO.class);
        log.info(dto);
        
    }



}