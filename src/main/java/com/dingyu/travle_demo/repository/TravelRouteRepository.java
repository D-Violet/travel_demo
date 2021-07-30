package com.dingyu.travle_demo.repository;

import com.dingyu.travle_demo.model.TravelRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TravelRouteRepository extends JpaRepository<TravelRoute, String>, JpaSpecificationExecutor<TravelRoute> {
}
