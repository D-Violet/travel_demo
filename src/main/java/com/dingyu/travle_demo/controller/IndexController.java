package com.dingyu.travle_demo.controller;

import com.dingyu.travle_demo.model.Attractions;
import com.dingyu.travle_demo.model.Hotel;
import com.dingyu.travle_demo.service.ReserveService;
import com.dingyu.travle_demo.service.RouteService;
import com.dingyu.travle_demo.service.StrategyService;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ReserveService reserveService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private StrategyService strategyService;

    public String index(Module module){
        List<Hotel> top10Hotel= reserveService.getTop10Hotel();
        List<Attractions> top10Attractions = reserveService.getTop10Attraction();
        routeService.findTop10Attraction();
        return null;
    }

}
