package com.accenture.xyz_maven.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.xyz_maven.buisnessBeans.UserBean;
import com.accenture.xyz_maven.response.Response;
import com.accenture.xyz_maven.service.UserService;
import com.accenture.xyz_maven.util.MessageUtil;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;
    
    //Sends response about user login to Frontend
    @PostMapping("login")
    public ResponseEntity<Response> validateLogin(@RequestBody UserBean userBean) throws Exception
    {
        return ResponseEntity.ok(userService.validateLogin(userBean));
    }

    //Handles Exception
    @ExceptionHandler(value=Exception.class)
	public ResponseEntity<Response> handleAllExceptions(Exception exception)
    {
        Response exceptionResponse = new Response();
		exceptionResponse.setMessage(MessageUtil.getMessage("error.exception.login"));
        exceptionResponse.setStatus(false);
        exceptionResponse.setIsException(true);
		return ResponseEntity.ok(exceptionResponse);
	}

}