package com.capstone.kanbantool.web;

import com.capstone.kanbantool.domain.User;
import com.capstone.kanbantool.payload.JWTLoginSuccessResponse;
import com.capstone.kanbantool.payload.LoginRequest;
import com.capstone.kanbantool.security.JWTTokenProvider;
import com.capstone.kanbantool.services.UserService;
import com.capstone.kanbantool.services.ValidationErrorServiceMap;
import com.capstone.kanbantool.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.capstone.kanbantool.security.SecurityConstant.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private ValidationErrorServiceMap validationErrorServiceMap;

    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult){

        ResponseEntity<?> errorMaps = validationErrorServiceMap.ValidationServiceMap(bindingResult);

        if(errorMaps != null){
            return errorMaps;
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = TOKEN_PREFIX + jwtTokenProvider.generatedToken(authentication);

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult){

        userValidator.validate(user, bindingResult);

        ResponseEntity<?> errorMaps = validationErrorServiceMap.ValidationServiceMap(bindingResult);

        if(errorMaps != null){
            return errorMaps;
        }

        User newUser  = userService.saveUser(user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

}
