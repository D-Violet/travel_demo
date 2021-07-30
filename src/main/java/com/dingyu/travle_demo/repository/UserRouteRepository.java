package com.dingyu.travle_demo.repository;

import com.dingyu.travle_demo.model.TravelRoute;
import com.dingyu.travle_demo.model.User;
import com.dingyu.travle_demo.model.UserRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRouteRepository extends JpaRepository<UserRoute, String>, JpaSpecificationExecutor<UserRoute> {

    List<UserRoute> findUserRouteByUser(User user);

    UserRoute findUserRouteByTravelRouteAndUser(TravelRoute travelRoute, User user);
}
