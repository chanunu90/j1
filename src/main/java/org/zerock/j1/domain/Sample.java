package org.zerock.j1.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "tbl_sample")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Sample {
    

    // pk고유키를 주는이유는 고유하게 crud가 가능하게 하기위해서다.

    @Id
    private String keyCol;

    private String first;

    private String last;

    private String addr;

    private String zipCode;

    private String city;



    

}
