package felp.projects.todosimple.controllers;

import felp.projects.todosimple.models.Task;
import felp.projects.todosimple.services.TaskService;
import felp.projects.todosimple.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/task")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Task> findById(@PathVariable Integer id) {
        Task obj = this.taskService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    @Validated
    public ResponseEntity<Void> create(@Valid @RequestBody Task obj) {
        this.taskService.create(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().
                path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    @Validated
    public ResponseEntity<Void> update(@PathVariable Integer id, @Valid @RequestBody Task obj) {
        obj.setId(id);
        this.taskService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @Validated
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        this.taskService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Task>> findAllByUserId(@PathVariable Integer userId){
        userService.findById(userId);
        List<Task> objs = this.taskService.findAllByUserId(userId);
        return ResponseEntity.ok().body(objs);
    }
}
