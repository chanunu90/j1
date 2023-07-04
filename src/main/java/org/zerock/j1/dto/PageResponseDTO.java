package org.zerock.j1.dto;

import java.util.List;

import lombok.Data;

@Data
public class PageResponseDTO<E> {
    
    List<E> dtoList;

}
