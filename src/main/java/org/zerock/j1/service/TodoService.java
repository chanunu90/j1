package org.zerock.j1.service;

import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.TodoDTO;

import jakarta.transaction.Transactional;

@Transactional
public interface TodoService {

    PageResponseDTO<TodoDTO> getList();

    TodoDTO register(TodoDTO dto);

    TodoDTO getOne(Long dto);

    void remove(Long tno);

    void modify(TodoDTO dto);
    
}
