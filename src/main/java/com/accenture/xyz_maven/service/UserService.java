package com.accenture.xyz_maven.service;

import com.accenture.xyz_maven.buisnessBeans.FormUserBean;
import com.accenture.xyz_maven.buisnessBeans.UserBean;
import com.accenture.xyz_maven.response.Response;

public interface UserService 
{
    Response validateLogin(UserBean userBean) throws Exception;

    Response saveUser(FormUserBean fBean) throws Exception;

    Response updatePassword(FormUserBean fBean) throws Exception;
}
