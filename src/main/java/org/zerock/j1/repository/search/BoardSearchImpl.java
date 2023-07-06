package org.zerock.j1.repository.search;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j1.domain.Board;
import org.zerock.j1.domain.QBoard;
import org.zerock.j1.domain.QReply;
import org.zerock.j1.dto.BoardListRcntDTO;
import org.zerock.j1.dto.PageRequestDTO;
import org.zerock.j1.dto.PageResponseDTO;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
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


    @Override
    public Page<Object[]> searchWithRcnt(String searchType, String keyword, Pageable pageable) {

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));
        
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
            // query.where(board.writer.contains(keyword));
            
        }      
        query.groupBy(board);

        JPQLQuery<Tuple> tupleQuery = 
            query.select(board.bno , board.title , board.writer , reply.countDistinct());

        this.getQuerydsl().applyPagination(pageable, tupleQuery);

        log.info("!------------------------------------------");
        List<Tuple> tuples = tupleQuery.fetch();

        List<Object[]> arrList = tuples.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());


        log.info("2------------------------------------------");
        
        log.info(tuples);

        log.info("3------------------------------------------");
        

        Long count = tupleQuery.fetchCount();

        log.info("count : "+ count);

        return new PageImpl<>( arrList , pageable ,count);

    }


    // @Override
    // public PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO requestDTO) {

    //     Pageable Pageable = magePageable(requestDTO);

    //     QBoard board = QBoard.board;
    //     QReply reply = QReply.reply;

    //     JPQLQuery<Board> query = from(board);
    //     query.leftJoin(reply).on(reply.board.eq(board));

    //     String keyword = requestDTO.getKeyword();
    //     String searchType = requestDTO.getType();
        
        
    //     if(keyword != null && searchType != null){
    //         String[] searchSrr = searchType.split("");
            
    //         //우선연산자 괄호 같은 역할 이다. ()
    //         BooleanBuilder searchBuilder = new BooleanBuilder();

    //         for (String type : searchSrr) {
                
    //             switch(type){
    //                 case "t" -> searchBuilder.or(board.title.contains(keyword));
    //                 case "c" -> searchBuilder.or(board.content.contains(keyword));
    //                 case "w" -> searchBuilder.or(board.writer.contains(keyword));
    //             }

    //         } // for문끝
    //         query.where(searchBuilder);
            
    //     }      
    //     query.groupBy(board);

    //     //Projections이걸 이용해서 DTO를 바로 사용해서 넣을수있다. 써야할 디티오, 가저다쓸 컬럼들
    //     JPQLQuery<BoardListRcntDTO> listQuery = 
    //         query.select(Projections.bean(BoardListRcntDTO.class, board.bno , board.title , board.writer , reply.countDistinct().as("replyCount")));

    //     List<BoardListRcntDTO> list = listQuery.fetch();

    //     log.info("---------------------------------------------------------------------------------");
    //     log.info(list);

    //     return null;
    // }

    
    @Override
    public PageResponseDTO<BoardListRcntDTO> searchDTORcnt(PageRequestDTO requestDTO) {

        Pageable pageable = makePageable(requestDTO);

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;

        JPQLQuery<Board> query = from(board);
        query.leftJoin(reply).on(reply.board.eq(board));

        String keyword = requestDTO.getKeyword();
        String searchType = requestDTO.getType();
        
        if(keyword != null && searchType != null){

        //tc -> [t,c]
        String[] searchArr = searchType.split("");
        // ( )
        BooleanBuilder searchBuilder = new BooleanBuilder();

        for (String type : searchArr) {
            switch(type){
            case "t" -> searchBuilder.or(board.title.contains(keyword));
            case "c" -> searchBuilder.or(board.content.contains(keyword));
            case "w" -> searchBuilder.or(board.writer.contains(keyword));
            }
        }//end for
        query.where(searchBuilder);
        }

        this.getQuerydsl().applyPagination(pageable, query);
        query.groupBy(board);

        JPQLQuery<BoardListRcntDTO> listQuery = query.select(
        Projections.bean(BoardListRcntDTO.class, 
            board.bno,
            board.title,
            board.writer,
            reply.countDistinct().as("replyCount")
            )
        );

        List<BoardListRcntDTO> list = listQuery.fetch();    

        log.info("-----------------------");
        log.info(list);

        long totalCount = listQuery.fetchCount();

        return new PageResponseDTO<>(list, totalCount, requestDTO);

    }

    
    
}
