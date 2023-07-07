package org.zerock.j1.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.j1.dto.BoardDTO;
import org.zerock.j1.dto.BoardListRcntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;

@Transactional
public interface BoardService {
 
  PageResponseDTO<BoardListRcntDTO> listRcnt (PageRequestDTO pageRequestDTO);

  BoardDTO getOne(Long bno);
  
}
