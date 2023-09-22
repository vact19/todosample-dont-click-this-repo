package com.example.todoexample.domain.todo;

import com.example.todoexample.api.todo.controller.TodoController;
import com.example.todoexample.api.todo.dto.TodoSaveReqDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TodoService {
    private final TodoRepository todoRepository;


    @Transactional
    // 선언적 트랜잭션
    public long create(TodoSaveReqDto reqDto) {
        // set audocommit false
        // start transaction
        Todo todo = new Todo(reqDto.getContent());

        return todoRepository.save(todo).getId();
        // commit + end transaction
    }

    public List<Todo> findTodos() {
        // service니까, repository에서 todo 데이터를 모두 가져온다.
        return todoRepository.findAll();
    }

    /**
     * JPA 1차 저장소가 있는데
     * Repository를 거쳐 온 Entity 객체는 1차 저장소에 등록됨.
     *
     * 1차 저장소에 등록된 객체는 변경 감지 기능을 통해 변경사항이 있으면 update 쿼리를 날린다.
     *
     * 1차 저장소의 생명주기는 트랜잭션 생명주기랑 같음.
     * 결국 트랜잭션 내에서 find...로 객체를 가져오고, 값을 변경하면
     *
     * (처음 가져왔을 때의 데이터) : (트랜잭션 커밋 시점의 데이터) 비교해서 Update query를 날려줌.
     */
    @Transactional
    public void update(Long id, TodoController.UpdateTodoReqDto reqDto) {
        // set auto commit false
        // start transaction
        Optional<Todo> optionalTodo = todoRepository.findById(id);
//        if(optionalTodo.isEmpty()) {
//            throw new IllegalArgumentException("해당 id의 todo가 없습니다.");
//        }
//        Todo todo1 = optionalTodo.get();

        Todo todo = optionalTodo.orElseThrow(() -> new IllegalArgumentException("해당 id의 todo가 없습니다."));
        // todo는 Null이 아니다.

        todo.update(reqDto.getContent(), reqDto.isDone());
        // commit + end transaction
    }

    @Transactional
    public void delete(Long todoId) {
        // 일단 가져오고 찾을 수 있는지를 확인
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 todo가 없습니다."));

        // 그 다음에 삭제
        todoRepository.delete(todo);
//        todoRepository.deleteById(todoId);
        // 이거는 id로 데이터를 못찾으면 이상한 에러를 띄움.
        // id로 일단 데이터를 찾고, delete(entity)를 해야함.
    }
}
