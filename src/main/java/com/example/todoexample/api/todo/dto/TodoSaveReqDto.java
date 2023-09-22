package com.example.todoexample.api.todo.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

// Jackson 이라는 json to Java object 변환 라이브러리가 기본 생성자를 통해서 객체를 생성합니다,.
// 리플렉션 API
@Getter
@NoArgsConstructor
public class TodoSaveReqDto {
    String content;
}
