package org.zerock.j1.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.zerock.j1.domain.Board;
import org.zerock.j1.domain.Reply;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.ReplyDTO;
import org.zerock.j1.dto.ReplyPageRequestDTO;
import org.zerock.j1.service.ReplyService;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
public class ReplyRepositoryTests {

    @Autowired
    public ReplyRepository replyRepository;

    @Autowired
    private ReplyService replyService;


    // 인설트 문은 나중에 빌드할때 한번더 실행되기 때문에 주석걸어준다.
    @Test
    public void insertOne() {

        Long bno = 100L;
        Board board = Board.builder().bno(bno).build();
        Reply reply = Reply.builder().replyText("Reply.....1").replyer("replyer00").board(board).build();
        replyRepository.save(reply);

    }

    @Test
    public void testInsertDummies() {

        Long[] bnoArr = { 91L, 97L, 94L, 84L, 87L };
        for (Long bno : bnoArr) {

            Board board = Board.builder().bno(bno).build();
            for (int i = 0; i < 50; i++) {
                Reply reply = Reply.builder().replyText("Reply....." + bno + "--" + i).replyer("replyer" + i)
                        .board(board).build();
                replyRepository.save(reply);
            }

        } // end for

    }

    @Test
    public void testListBoard() {
        Long bno = 91L;

        Pageable pageable = PageRequest.of(0, 10, org.springframework.data.domain.Sort.by("rno").ascending());

        Page<Reply> result = replyRepository.listBoard(bno, pageable);

        // log.info(result);

        result.get().forEach(r -> log.info(r));
    }

    @Test
    public void testCount() {

        Long bno = 91L;

        Long count = replyRepository.getCountBoard(bno);

        log.info(count);

    }

    @Test
    public void testListLast() {

        ReplyPageRequestDTO requestDTO = ReplyPageRequestDTO.builder().bno(84L).last(true).build();
        PageResponseDTO<ReplyDTO> a = replyService.list(requestDTO);

        log.info(a);

    }

}
