package com.dingyu.travle_demo.repository;


import com.dingyu.travle_demo.model.TravelStrategy;
import com.dingyu.travle_demo.model.User;
import com.dingyu.travle_demo.model.UserStrategy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UserStrategyRepository extends JpaRepository<UserStrategy, String>, JpaSpecificationExecutor<UserStrategy> {

    List<UserStrategy> findUserStrategyByUser(User user);

    UserStrategy findUserStrategyByTravelStrategyAndUser(TravelStrategy travelStrategy, User user);
}
