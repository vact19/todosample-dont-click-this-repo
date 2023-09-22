package com.example.todoexample.domain.todo;

import com.example.todoexample.domain.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Todo extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private boolean done;

    public Todo(String content) {
        this.content = content;
        this.done = false;
    }

    public void update (String content, boolean done) {
        this.content = content;
        this.done = done;
    }
}















