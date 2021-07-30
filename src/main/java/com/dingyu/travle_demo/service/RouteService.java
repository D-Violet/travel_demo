package com.dingyu.travle_demo.service;

import com.dingyu.travle_demo.repository.TravelRouteRepository;
import com.dingyu.travle_demo.repository.UserRepository;
import com.dingyu.travle_demo.repository.UserRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class RouteService {
    @Autowired
    private TravelRouteRepository travelRouteRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRouteRepository userRouteRepository;

    public Page<>

    public void findTop10Attraction() {
    }
}
