package com.example.todoexample.api.todo.dto;


import com.example.todoexample.domain.todo.Todo;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TodoListRspDto {
    List<TodoDto> todos;

    public TodoListRspDto(List<Todo> todos) {
        this.todos = TodoDto.from(todos);
    }

    @Builder
    @Getter
    static class TodoDto {
        private Long id;
        private String content;
        private boolean done;
        String lastModifiedBy;
        LocalDateTime lastModifiedTime;

        public static TodoDto from(Todo todo) {
            return TodoDto.builder()
                    .id(todo.getId())
                    .content(todo.getContent())
                    .done(todo.isDone())
                    .lastModifiedBy(todo.getLastModifiedBy())
                    .lastModifiedTime(todo.getLastModifiedTime())
                    .build();
        }
        // todo의 List를 받아서, todoDTO의 list로 변환
        public static List<TodoDto> from(List<Todo> todos) {
            return todos.stream()
                    .map(TodoDto::from)
                    .collect(java.util.stream.Collectors.toList());
        }


    }
}





