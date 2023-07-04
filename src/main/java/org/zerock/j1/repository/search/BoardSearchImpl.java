package org.zerock.j1.repository.search;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j1.domain.Board;
import org.zerock.j1.domain.QBoard;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch{

    public BoardSearchImpl(){
        super(Board.class);
    }


    //2.
    @Override
    public Page<Board> search1(String searchType, String keyword ,Pageable pageable){
    
        QBoard board = QBoard.board;
        
        JPQLQuery<Board> query = from(board);


        if(keyword != null && searchType != null){
            String[] searchSrr = searchType.split("");
            
            //우선연산자 괄호 같은 역할 이다. ()
            BooleanBuilder searchBuilder = new BooleanBuilder();

            for (String type : searchSrr) {
                
                switch(type){
                    case "t" -> searchBuilder.or(board.title.contains(keyword));
                    case "c" -> searchBuilder.or(board.content.contains(keyword));
                    case "w" -> searchBuilder.or(board.writer.contains(keyword));
                }

            } // for문끝
            query.where(searchBuilder);
            //차은우 테스트
            query.where(board.writer.contains(keyword));
            
        }
        


        // query.where(board.title.contains("1"));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Board>list = query.fetch(); // 페이징 쿼리를 만들어내는 부분

        long count = query.fetchCount();

        log.info(list);
        log.info("conut: " + count);
        

        return new PageImpl<>(list, pageable , count);
    
        
    }
    
}
