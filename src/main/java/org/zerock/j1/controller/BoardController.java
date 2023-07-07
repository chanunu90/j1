package org.zerock.j1.controller;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.j1.dto.BoardDTO;
import org.zerock.j1.dto.BoardListRcntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.service.BoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/api/board")
@Log4j2
@RequiredArgsConstructor
@CrossOrigin
public class BoardController {
    
    private final BoardService boardService;

    @GetMapping("/list" )
    public PageResponseDTO<BoardListRcntDTO> list(@ParameterObject PageRequestDTO pageRequestDTO){

        log.info(pageRequestDTO);


        return boardService.listRcnt(pageRequestDTO);

    }



    @GetMapping("/{bno}")
    public BoardDTO get(@PathVariable("bno") Long bno){

        return boardService.getOne(bno);
    }



}
