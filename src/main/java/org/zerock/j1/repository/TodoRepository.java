package org.zerock.j1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.j1.domain.Todo;

                                                        //데이터 리절트타입과 pk타입을 넣어줘라.
public interface TodoRepository extends JpaRepository<Todo , Long>{
    
    

}
