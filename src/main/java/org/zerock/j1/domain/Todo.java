package org.zerock.j1.domain;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_todo2")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tno;


    //nullable 은  db의 notnull같은거고 length은 varchar(???)같은거다.
    @Column(length = 300 , nullable = false)
    private String title;



    public void changeTitle(String title ){
        this.title = title;
    }
}
