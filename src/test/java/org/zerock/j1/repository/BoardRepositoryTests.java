package org.zerock.j1.repository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.test.annotation.Commit;
import org.zerock.j1.domain.Board;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class BoardRepositoryTests {
    
    @Autowired
    private BoardRepository boardRepository;


    @Test
    public void testInsert(){


        for(int i=0 ; i<100 ; i++){

            Board board = Board.builder().title("title" + i).content("content" + i).writer("user" + (i%10)).build();

             boardRepository.save(board);

        }





    }

    @Test
    public void testRead(){


        Optional<Board> result =  boardRepository.findById(1l);

        log.info("-------------------------------------------------------------------------------");
        Board board =  result.orElseThrow();
        log.info(board.getTitle());

    }

    @Test
    public void testUpdate(){

        Optional<Board> result =  boardRepository.findById(1l);

        log.info("-----------------------------수정은 조회한 다음----------------------------------");
        Board board =  result.orElseThrow();

        board.changeTitle("Update Title");

        boardRepository.save(board);
        

    }

    @Test
    public void testQuery1(){

        List<Board> board = boardRepository.findByTitleContaining("1");
        log.info(board.size());
        log.info(board);

    }

      // JPQL 관련 테스트
    @Test
    public void testQuery1_1(){

        java.util.List<Board> list = boardRepository.listTitle("1");

        log.info("----------------------");
        log.info(list.size());
        log.info(list);
    }

    // JPQL 관련 테스트
    @Test
    public void testQuery1_2(){

        java.util.List<Object[]> list = boardRepository.listTitle2("1");

        log.info("----------------------");
        log.info(list.size());
        
        list.forEach(arr -> log.info(Arrays.toString(arr)));
    }

    // JPQL 관련 테스트
    @Test
    public void testQuery1_3(){


        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Object[]> list = boardRepository.listTitle2("1" , pageable);

        log.info("----------------------");
        log.info(list);


    }


    @Test
    public void testQuery2(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Board> obj =  boardRepository.findByContentContaining("1", pageable);
        log.info(obj);



    }
        
//     @Test
//     public void testQuery2() {

//     Pageable pageable = PageRequest.of(
//       0,10, 
//       Sort.by("bno").descending() );

//     Page<Board> result = boardRepository.findByContetnContaining("1", pageable);

//     log.info("-----------------");
//     log.info(result);

//             // List<Board> board = boardRepository.findByTitleContaining("1");
//         // log.info(board.size());
//         // log.info(board);

//   }


    @Transactional
    @Test
    @Commit
    public void testModify(){

        Long bno = 100L;
        String title = "Modify Title 100";

        int count = boardRepository.modifyTitle(title, bno);
        log.info("-----------------" + count);
        log.info(count);

    }


    @Test
    public void testNative(){
        List<Object[]> result = boardRepository.listNative();
        result.forEach(arr -> log.info(Arrays.toString(arr)));
    }


    @Test
    public void testSearch1(){

        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());

        Page<Board> result =  boardRepository.search1( "tcw" , "1"  , pageable);

        log.info(result.getTotalElements());

        result.get().forEach(b->log.info(b));

    }




}