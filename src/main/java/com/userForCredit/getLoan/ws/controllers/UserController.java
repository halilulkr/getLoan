package com.userForCredit.getLoan.ws.controllers;

import com.userForCredit.getLoan.business.abstracts.UserService;
import com.userForCredit.getLoan.business.requests.userRequests.CreateUserRequest;
import com.userForCredit.getLoan.business.requests.userRequests.UpdateUserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/users")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("add")
    public ResponseEntity<?> add(@RequestBody @Valid CreateUserRequest createUserRequest){
        return ResponseEntity.ok(this.userService.add(createUserRequest));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody @Valid UpdateUserRequest updateUserRequest){
        return ResponseEntity.ok(this.userService.update(updateUserRequest));
    }

    @DeleteMapping("delete/{userId}")
    public ResponseEntity<?> delete(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.delete(userId));
    }

    @GetMapping("getAll")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.userService.getAll());
    }

    @GetMapping("getById/{userId}")
    public ResponseEntity<?> getById(@PathVariable Integer userId){
        return ResponseEntity.ok(this.userService.getById(userId));
    }
}
