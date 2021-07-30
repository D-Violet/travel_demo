package com.dingyu.travle_demo.repository;

import com.dingyu.travle_demo.model.Hotel;
import com.dingyu.travle_demo.model.User;
import com.dingyu.travle_demo.model.UserHotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserHotelRepository extends JpaRepository<UserHotel, String>, JpaSpecificationExecutor<UserHotel> {
    List<UserHotel> findUserHotelsByUser(User user);

    UserHotel findUserHotelByHotelAndUser(Hotel hotel, User user);

}
