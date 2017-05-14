package me.loki2302.be;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;

    @GetMapping("/")
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    @PostMapping("/")
    public ResponseEntity createTodo(@RequestBody Todo todo) {
        todo = todoRepository.save(todo);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTodo(@PathVariable("id") long id) {
        todoRepository.delete(id);
        return ResponseEntity.noContent().build();
    }
}
