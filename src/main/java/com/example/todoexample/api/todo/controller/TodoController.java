package com.example.todoexample.api.todo.controller;

import com.example.todoexample.api.common.RspTemplate;
import com.example.todoexample.api.todo.dto.TodoListRspDto;
import com.example.todoexample.api.todo.dto.TodoSaveReqDto;
import com.example.todoexample.domain.todo.Todo;
import com.example.todoexample.domain.todo.TodoService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
// conroller (사용자 요청 관련) -> service (트랜잭션 관리, 주요 로직 호출) -> repository (DB 접근)
public class TodoController {
    private final TodoService todoService;

    // 등록은
    // /todos POST
    @PostMapping("/todos")
    // 요청값은 핸들러 메서드의 파라미터를 통해서 가져온다.
    public ResponseEntity<RspTemplate<Void>> create(@RequestBody TodoSaveReqDto reqDto) {
        // json -> 자바 객체로 바꾸는 방법
//        {
//            "title": "할일1",
//            "content": "할일1 내용"
//        }
        // 요청값 service로 넘기기
        // service는 repository를 활용해 데이터를 DB에 저장하고, 반환할 것이다.
        long todoId = todoService.create(reqDto);

        RspTemplate<Void> rspTemplate = new RspTemplate<>(HttpStatus.CREATED
                , todoId + "번 할일 등록 성공");
        return ResponseEntity.status(HttpStatus.CREATED).body(rspTemplate);
    }

    // 조회는
    // /todos GET
    @GetMapping("/todos")
    public RspTemplate<TodoListRspDto> getTodos() {
        // 클라이언트의 요청값이 필요한지? -> 필요없음.
        // service를 통해서 todo의 목록 전체를 받아온 다음 넘겨주기.
        List<Todo> todos = todoService.findTodos();

        // Entity 객체 todos를 DTO로 변환하기.
        TodoListRspDto rspDto = new TodoListRspDto(todos);

        // 변환한 DTO의 리스트를 클라이언트로 반환하기.
        return new RspTemplate<>(HttpStatus.OK, "할일 목록 조회 성공", rspDto);
    }

    // 수정
    // /todos/{todoId}  PUT
    // /todos/2
    // 어떤 자원인지는 url에
    // 자원으로 뭘 할 건지는 Http 본문 (JSON)
    @PutMapping("/todos/{todoId}")
    public ResponseEntity<Void> updateTodo(
            @RequestBody UpdateTodoReqDto reqDto
            , @PathVariable Long todoId
    ) {
        // 사용자의 요청값이 필요한가??? -> 필요함.
        todoService.update(todoId, reqDto);

        return ResponseEntity.noContent().build();
        // HTTP 204. NO CONTENT
    }

    // Jackson 이라는 json to Java object 변환 라이브러리가 기본 생성자를 통해서 객체를 생성합니다,.
    // 리플렉션 API
    @NoArgsConstructor
    @Getter
    public static class UpdateTodoReqDto {
        private String content;
        private boolean done;
    }


    // 삭제
    // /todos/{todoId} DELETE
    @DeleteMapping("/todos/{todoId}")
    public ResponseEntity<Void> deleteTodo(
            @PathVariable Long todoId
    ) {
        todoService.delete(todoId);
        return ResponseEntity.noContent().build();
    }











}
