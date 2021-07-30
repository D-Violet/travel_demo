package com.dingyu.travle_demo.service;

import com.dingyu.travle_demo.core.ResultGenerator;
import com.dingyu.travle_demo.model.User;
import com.dingyu.travle_demo.repository.UserRepository;
import com.dingyu.travle_demo.util.CookieUitl;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;

@Service
public class UserCenterService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    public User getUser(HttpServletRequest request) throws ServletException {
        Cookie cookie= CookieUitl.get(request,"username");
        User user = null;
        if(cookie != null){
            user = userRepository.findUserByUsername(cookie.getValue());
        }else{
            throw  new ServletException("不存在该用户！");
        }
        return user;
    }
    public Result centerEdit(User user){
        User oldUser = userRepository.findById(user.getId()).orElseThrow(()->new ServiceException("用户ID错误"));
        oldUser.setName(user.getName());
        userRepository.save(oldUser);
        return (Result) ResultGenerator.genSuccessResult();
    }

    public Result centerEditPW(HttpServletRequest request, HttpServletResponse response,String id,String oldPassword,String newPassword){
        User oldUser = userRepository.findById(id).orElseThrow(()->new ServiceException("用户ID错误"));
        if(!oldUser.getPassword().equals(oldPassword)){
            return (Result)ResultGenerator.genFailResult("与原密码不匹配");
        }
        oldUser.setPassword(newPassword);
        userRepository.save(oldUser);
        //重新登录
        loginService.logout(request, response);
        return (Result)ResultGenerator.genSuccessResult();
    }
}
