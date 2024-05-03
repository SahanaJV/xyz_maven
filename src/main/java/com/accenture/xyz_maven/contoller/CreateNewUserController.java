package com.accenture.xyz_maven.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.xyz_maven.buisnessBeans.FormUserBean;
import com.accenture.xyz_maven.response.Response;
import com.accenture.xyz_maven.service.UserService;
import com.accenture.xyz_maven.util.MessageUtil;
import com.accenture.xyz_maven.validations.UserValidations;


@RestController
@RequestMapping("/")
public class CreateNewUserController {

    @Autowired
    UserService userService;

    //sends response about adding the new user to Frontend
    @PostMapping("save")
    public ResponseEntity<Response> saveUser(@RequestBody FormUserBean fBean) throws Exception
    {
        Response newUserResponse = new Response();

        if(UserValidations.usernameValidation(fBean.getUsername()))
        {
            if(UserValidations.passwordValidation(fBean.getPassword()) && UserValidations.passwordValidation(fBean.getReEnterPassword()))
            {
                newUserResponse = userService.saveUser(fBean);
            }
            else
            {
                String invalidPasswordError = MessageUtil.getMessage("error.password.invalid");
                newUserResponse.setMessage(invalidPasswordError);
                newUserResponse.setStatus(false);  
            }
        }
        else
        {
            String invalidUsernameError = MessageUtil.getMessage("error.username.invalid");
            newUserResponse.setMessage(invalidUsernameError);
            newUserResponse.setStatus(false);
        }
        return ResponseEntity.ok(newUserResponse);
    }

    //Handles Exception
    @ExceptionHandler(value=Exception.class)
	public ResponseEntity<Response> handleAllExceptions(Exception exception)
    {
        Response exceptionResponse = new Response();
		exceptionResponse.setMessage(MessageUtil.getMessage("error.exception.register"));
        exceptionResponse.setStatus(false);
        exceptionResponse.setIsException(true);
		return ResponseEntity.ok(exceptionResponse);
	}
}
