package com.accenture.xyz_maven.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.accenture.xyz_maven.buisnessBeans.FormUserBean;
import com.accenture.xyz_maven.buisnessBeans.UserBean;
import com.accenture.xyz_maven.dao.UserDao;
import com.accenture.xyz_maven.entity.UserEntity;
import com.accenture.xyz_maven.response.Response;
import com.accenture.xyz_maven.util.MessageUtil;

@Service
public class UserServiceImpl implements UserService 
{
    @Autowired
    private UserDao userDao;

    static List<String> usernames = new ArrayList<>();
    
    //Checks if the username and password is valid and sends the response to controller
    @Override
    public Response validateLogin(UserBean userBean) throws Exception 
    {
        System.out.println("Hello");
        BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder();
        Response loginResponse = new Response();
        UserEntity userEntity = new UserEntity();

        if(usernames.isEmpty())
        {
            createUsernamesList();
        }

        userEntity = userDao.findFirstByUsername(userBean.getUsername());

        if(userEntity != null)
        {
            if(bEncoder.matches(userBean.getPassword(), userEntity.getPassword()))
            {
                loginResponse.setMessage(MessageUtil.getMessage("login.success"));
                loginResponse.setStatus(true);
            }
            else
            {
                loginResponse.setMessage(MessageUtil.getMessage("password.incorrect"));
                loginResponse.setStatus(false);
            }
        }
        else
        {
            loginResponse.setMessage(MessageUtil.getMessage("user.not.found"));
            loginResponse.setStatus(false);
        }
        return loginResponse;
    }

    //Adds a new user to Database and sends the response to contoller
    @Override
    public Response saveUser(FormUserBean fBean) throws Exception 
    {
        BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder();
        Response newUserResponse = new Response();
        UserEntity userEntity = null;
        
        if(fBean.getPassword().equals(fBean.getReEnterPassword()))
        {
            if(uniqueUsername(fBean.getUsername()))
            {
                userEntity = new UserEntity();

                userEntity.setUsername(fBean.getUsername());
                String encodedPassword = bEncoder.encode(fBean.getPassword());
                userEntity.setPassword(encodedPassword);

                userDao.save(userEntity);

                usernames.add(userEntity.getUsername());
                newUserResponse.setMessage(MessageUtil.getMessage("user.success"));
                newUserResponse.setStatus(true);
            }
            else
            {
                newUserResponse.setMessage(MessageUtil.getMessage("user.not.unique"));
                newUserResponse.setStatus(false);
            }
        }
        else
        {
            newUserResponse.setMessage(MessageUtil.getMessage("password.mismatch"));
            newUserResponse.setStatus(false);
        }
        return newUserResponse;
    }
    
    //Updates the user password and sends the response to controller
    @Override
    public Response updatePassword(FormUserBean fBean) throws Exception 
    {
        BCryptPasswordEncoder bEncoder = new BCryptPasswordEncoder();
        Response forgotPasswordResponse = new Response();
        UserEntity userEntity = new UserEntity();

        if(usernames.isEmpty())
        {
            createUsernamesList();
        }
        
        if(fBean.getPassword().equals(fBean.getReEnterPassword()))
        {
            userEntity = userDao.findFirstByUsername(fBean.getUsername());
            if(userEntity!=null)
            {
                String encodedPassword = bEncoder.encode(fBean.getPassword());
                userEntity.setPassword(encodedPassword);

                userDao.save(userEntity);

                forgotPasswordResponse.setMessage(MessageUtil.getMessage("password.updated"));
                forgotPasswordResponse.setStatus(true);
            }
            else
            {
                forgotPasswordResponse.setMessage(MessageUtil.getMessage("user.not.found"));
                forgotPasswordResponse.setStatus(false);
            }
        }
        else
        {
            forgotPasswordResponse.setMessage(MessageUtil.getMessage("password.mismatch"));
            forgotPasswordResponse.setStatus(false);
        }      
        return forgotPasswordResponse;
    }

    //Checks if the username is unique and returns a boolean value
    private boolean uniqueUsername(String username) 
    {
        if(usernames.isEmpty())
        {
            createUsernamesList();
        }
        if(usernames.contains(username))
            return false;
        return true;    
    }

    //creates a list of usernames if the list is empty
    private void createUsernamesList() 
    {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities = userDao.findAll();
        for (UserEntity userEntity : userEntities) {
            usernames.add(userEntity.getUsername());
        }
    }

    
}
