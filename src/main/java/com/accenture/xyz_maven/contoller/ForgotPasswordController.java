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
public class ForgotPasswordController 
{
    @Autowired
    private UserService userService;
    
    //sends response about updating a user's password to Frontend
    @PostMapping("/forgotPassword")
    public ResponseEntity<Response> forgotPassword(@RequestBody FormUserBean fBean) throws Exception
    {
        Response forgotPasswordResponse = new Response();
        if(UserValidations.passwordValidation(fBean.getPassword()) && UserValidations.passwordValidation(fBean.getReEnterPassword()))
        {
            forgotPasswordResponse = userService.updatePassword(fBean);
        }
        else
        {
            String invalidPasswordError = MessageUtil.getMessage("error.password.invalid");
            forgotPasswordResponse.setMessage(invalidPasswordError);
            forgotPasswordResponse.setStatus(false);
        }
        return ResponseEntity.ok(forgotPasswordResponse);
    }

    //Handles Exception
    @ExceptionHandler(value=Exception.class)
	public ResponseEntity<Response> handleAllExceptions(Exception exception)
    {
        Response exceptionResponse = new Response();
		exceptionResponse.setMessage(MessageUtil.getMessage("error.exception.forgotPassword"));
        exceptionResponse.setStatus(false);
        exceptionResponse.setIsException(true);
		return ResponseEntity.ok(exceptionResponse);
	}
}
