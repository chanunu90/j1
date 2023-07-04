package org.zerock.j1.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.j1.domain.Board;

public interface BoardSearch {

    //1.인터페이스에 메소드를 추가한다.
    Page<Board> search1(String searchType, String keyword ,Pageable pageable);

    

    
}
