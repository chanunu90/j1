package org.zerock.j1.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.j1.domain.Board;
import org.zerock.j1.dto.BoardListRcntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;

public interface BoardSearch {
  
  //v1
  Page<Board> search1( String searchType, String keyword, Pageable pageable);

  //v2
  Page<Object[]> searchWithRcnt(String searchType, String keyword, Pageable pageable);

  //v3
  PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO requestDTO);

  default Pageable makePageable(PageRequestDTO requestDTO){

    Pageable pageable = PageRequest.of( 
      requestDTO.getPage() -1,
      requestDTO.getSize(),
      Sort.by("bno").descending()  );

      return pageable;
  }

}
