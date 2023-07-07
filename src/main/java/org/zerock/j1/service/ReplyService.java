package org.zerock.j1.service;

import org.springframework.transaction.annotation.Transactional;
import org.zerock.j1.dto.PageResponseDTO;
import org.zerock.j1.dto.ReplyDTO;
import org.zerock.j1.dto.ReplyPageRequestDTO;

@Transactional
public interface ReplyService {

  PageResponseDTO<ReplyDTO> list(ReplyPageRequestDTO requestDTO);

}
