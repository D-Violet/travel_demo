package com.dingyu.travle_demo.service;

import com.dingyu.travle_demo.core.Result;
import com.dingyu.travle_demo.core.ResultGenerator;
import com.dingyu.travle_demo.core.ServiceException;
import com.dingyu.travle_demo.model.*;
import com.dingyu.travle_demo.repository.*;
import com.dingyu.travle_demo.util.CookieUitl;
import com.dingyu.travle_demo.util.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

@Service
public class ReserveService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private AttractionsRepository attractionsRepository;
    @Autowired
    private UserHotelRepository userHotelRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserAttractionsRepository userAttractionsRepository;

    public Page<Hotel> reserveHotelListUI(String searchName, Pageable pageable){
        //查询启用的酒店列表
        Page<Hotel> hotelPage = hotelRepository.findAll((root, criteriaQuery, criteriaBuilder) ->{
            List<Predicate> predicates = new ArrayList<>();
            predicates.add((Predicate) criteriaBuilder.equal(root.get("status"),0));
            //status状态,查询状态为0,启动的酒店
            //酒店name模糊查询
            if(!StringUtils.isEmpty(searchName)){
                predicates.add((Predicate) criteriaBuilder.like(root.get("name"),"%"+searchName));
            }
            criteriaQuery.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
            return null;
        } ,pageable);
       return hotelPage;
    }

    public Hotel findHotelById(String id){
        return hotelRepository.findById(id).orElseThrow(() -> new ServiceException("酒店Id错误"));
    }

    public Page<Attractions>reserveAttractionsListUI(String searchName,Pageable pageable){
        //查询启用的景点列表
        Page<Attractions>attractionsPage = attractionsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();
            predicates.add((Predicate) criteriaBuilder.equal(root.get("status"),0));
            //status状态,查询状态为0,启动的景点
            //status状态,查询状态为0,启动的景点
            if (!StringUtils.isEmpty(searchName)){
                predicates.add((Predicate) criteriaBuilder.like(root.get("name"),"%"+searchName+"%"));
            }
            criteriaQuery.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
            criteriaQuery.orderBy((criteriaBuilder.desc(root.get("createDate"))));
            return null;
        },pageable);
        return attractionsPage;
    }

    public Attractions findAttractionsById(String id){
        return attractionsRepository.findById(id).orElseThrow(()->new ServiceException("景点id错误"));
    }

    public List<UserHotel>getReserveHotelByUser(HttpServletRequest request){
        Cookie cookie = CookieUitl.get(request, "username");
        if(cookie == null){
            throw new ServiceException("未能获得正确的用户名");
        }
        User user = userRepository.findUserByUsername(cookie.getValue());
        return userHotelRepository.findUserHotelsByUser(user);
    }

    @Transactional
    public Result cancelReserve(HttpServletRequest request,String id){
        Cookie cookie = CookieUitl.get(request, "username");
        if (cookie == null){
            throw new ServiceException("用户没登录！");
        }
        Hotel hotel = findHotelById(id);
        User user = userRepository.findUserByUsername(cookie.getValue());
        UserHotel userHotel = userHotelRepository.findUserHotelByHotelAndUser(hotel, user);
        //存在值就是取消预约.不存在值就是预约
        if(userHotel !=null){
            userHotelRepository.delete(userHotel);
        }else{
            UserHotel newUserHotel1 = new UserHotel();
            newUserHotel1.setId(IdGenerator.id());
            newUserHotel1.setCreateDate(new Date());
            newUserHotel1.setUser(user);
            newUserHotel1.setHotel(hotel);
            userHotelRepository.saveAndFlush(newUserHotel1);
        }
        return ResultGenerator.genSuccessResult();
    }

    public Boolean isReserveHotel(HttpServletRequest request,String id){
        Cookie cookie = CookieUitl.get(request, "username");
        if (cookie != null) {
            User user = userRepository.findUserByUsername(cookie.getValue());
            Hotel hotel = findHotelById(id);
            UserHotel userHotel = userHotelRepository.findUserHotelByHotelAndUser(hotel, user);
            //每个酒店只能预约一次
            if (userHotel != null){
                return true;
            }
        }
        return false;
    }

    public List<UserAttractions> getReserveAttractionsByUser(HttpServletRequest request){
        Cookie cookie = CookieUitl.get(request, "username");
        if (cookie == null){
            throw new ServiceException("未能获得正确的用户名");
        }
        User user = userRepository.findUserByUsername(cookie.getValue());
        return userAttractionsRepository.findUserAttractionsByUser(user);
    }

    @Transactional(rallbackFor = Exception.class)
    public Result cancelAttractionReserve(HttpServletRequest request,String id){
        Cookie cookie = CookieUitl.get(request, "username");
        if (cookie == null){
            throw new ServiceException("用户没有登录");
        }
        Attractions attractions = findAttractionsById(id);
        User user = userRepository.findUserByUsername(cookie.getValue());
        UserAttractions userAttractions = userAttractionsRepository.findUserAttractionsByAttractionsAndUser(attractions, user);
        //存在值就是取消预约.不存在值就是预约
        if (userAttractions != null){
            userAttractionsRepository.delete(userAttractions);
        }else{
            UserAttractions userAttractions1 = new UserAttractions();
            userAttractions1.setId(IdGenerator.id());
            userAttractions1.setCreateDate(new Date());
            userAttractions1.setUser(user);
            userAttractions1.setAttractions(attractions);
            userAttractionsRepository.saveAndFlush(userAttractions);
        }
        return ResultGenerator.genSuccessResult();
    }

    public Boolean isReserveAttraction(HttpServletRequest request,String id){
        Cookie cookie = CookieUitl.get(request, "username");
        if (cookie != null){
            User user = userRepository.findUserByUsername(cookie.getValue());
            Attractions attractionsById = findAttractionsById(id);
            UserAttractions userAttractions = userAttractionsRepository.findUserAttractionsByAttractionsAndUser(attractionsById, user);
            //每个景点只能预约一次
            if (userAttractions !=null){
                return true;
            }
        }
        return  false;
    }

    public List<Hotel> getTop10Hotel() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        //查询启用的酒店列表
        Page<Hotel> page = hotelRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            //status状态,查询状态为0,启动的酒店
            predicates.add((Predicate) criteriaBuilder.equal(root.get("status"), 0));
            criteriaQuery.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
            return null;
        }, pageRequest);
        return page.getContent();
    }

    public List<Attractions> getTop10Attraction() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Attractions> page = attractionsRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add((Predicate) criteriaBuilder.equal(root.get("status"), 0));
            criteriaQuery.where(predicates.toArray(new javax.persistence.criteria.Predicate[]{}));
            criteriaQuery.orderBy(criteriaBuilder.desc(root.get("createDate")));
            return null;
        }, pageRequest);
        return page.getContent();
    }
}
