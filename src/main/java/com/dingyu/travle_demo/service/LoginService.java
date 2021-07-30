package com.dingyu.travle_demo.service;

import com.dingyu.travle_demo.core.Result;
import com.dingyu.travle_demo.core.ResultGenerator;
import com.dingyu.travle_demo.model.User;
import com.dingyu.travle_demo.repository.UserRepository;
import com.dingyu.travle_demo.util.CookieUitl;
import com.dingyu.travle_demo.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    public Result login(User user, HttpServletResponse response){
        User userByUsername=userRepository.findUserByUsername(user.getUsername());
        if(userByUsername==null){
            return ResultGenerator.genFailResult("用户名不能为空");
        }else{
            if(user.getPassword().equals(userByUsername.getPassword())){
                Cookie cookie =new Cookie("username",user.getUsername());
                cookie.setPath("/");
                cookie.setMaxAge(3600);
                response.addCookie(cookie);
                return  ResultGenerator.genSuccessResult();
            }else{
                return ResultGenerator.genFailResult("密码错误");
            }
        }
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = CookieUitl.get(request, "username");
        if(cookie != null){
            CookieUitl.set(response,"username",null,0);
        }
    }

    public Result register(User user){
        User userByUsername = userRepository.findUserByUsername(user.getUsername());
        if(userByUsername != null){
            return ResultGenerator.genFailResult("用户名存在");
        }
        user.setId(IdGenerator.id());
        userRepository.save(user);
        return ResultGenerator.genSuccessResult();
    }
}
