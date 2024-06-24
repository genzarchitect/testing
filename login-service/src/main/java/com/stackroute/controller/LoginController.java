package com.stackroute.controller;


import com.stackroute.Exceptions.EmailNotFound;
import com.stackroute.Exceptions.IncorrectPasswordException;
import com.stackroute.Exceptions.UserExistsException;
import com.stackroute.Exceptions.UserNotFoundException;
import com.stackroute.service.LoginService;
import com.stackroute.service.TokenGeneratorService;
import com.stackroute.model.User;
import com.stackroute.model.UserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.Map;


//@CrossOrigin("*")
@RestController
    @RequestMapping("/arena")
    public class LoginController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private TokenGeneratorService tokenGeneratorService;
    public ResponseEntity<?> responseEntity;
//    private static final Logger log = LoggerFactory.getLogger(KafkaConsumer.class);


    @GetMapping("/welcome")
    public String welcome() {
        return "hello";
    }


//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody User user) throws UserExistsException {
//        try {
//            User users = loginService.registerUser(user);
//            responseEntity = new ResponseEntity<>(users, HttpStatus.CREATED);
//        } catch (UserExistsException u) {
//            responseEntity = new ResponseEntity<>("User Already Exists.", HttpStatus.CONFLICT);
//        }
//        return responseEntity;
//    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody UserCredential credential) throws UserNotFoundException, IncorrectPasswordException {
        try {
            User user = loginService.authenticateUser(credential.getUseremail(), credential.getPassword());
            Map<String, String> token = tokenGeneratorService.generateToken(credential);
            responseEntity = new ResponseEntity<>(token, HttpStatus.OK);
        } catch (UserNotFoundException e) {
            responseEntity = new ResponseEntity<>("Not User By this mail", HttpStatus.NOT_FOUND);
        } catch (IncorrectPasswordException i) {
            responseEntity = new ResponseEntity<>("Incorrect Password", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @PutMapping("/forget/{email}")
    public ResponseEntity<?> ForgotPassword(@PathVariable("email") String email, @RequestBody User user) {
        try {
            User userr = loginService.forgotPassword(email, user);
            responseEntity = new ResponseEntity<>(userr, HttpStatus.ACCEPTED);
        } catch (EmailNotFound e) {
            responseEntity = new ResponseEntity<>("Email not selected", HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
    @GetMapping("/user/{email}")
    public ResponseEntity<?> getuser(@PathVariable String email){
        try {
            User userr = loginService.getUser(email);
            responseEntity = new ResponseEntity<>(userr, HttpStatus.ACCEPTED);
        } catch (EmailNotFound e) {
            responseEntity = new ResponseEntity<>("Email not selected", HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }

//    @KafkaListener(topics = "topic1", groupId = "groupone")
//    public void listen(String message) throws JsonProcessingException {
//        ObjectMapper user=new ObjectMapper();
//        User user2 =user.readValue(message,User.class);
//        log.info("Message received:{}", message);

}







