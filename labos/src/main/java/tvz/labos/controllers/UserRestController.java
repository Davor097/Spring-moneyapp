package tvz.labos.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/user", produces = "application/json")
@CrossOrigin
public class UserRestController {/*
    private final JdbcUserRepository userRepository;

    public UserRestController(JdbcUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<User> findOne(@PathVariable String name) {
        User user = userRepository.findOne(name);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public User save(@RequestBody User user) {
        return userRepository.save(user);
    }

    @PutMapping("/{name}")
    public User update(@RequestBody User user) {
        return userRepository.update(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{name}")
    public void delete(@PathVariable String name) {
        userRepository.delete(name);
    }*/
}
