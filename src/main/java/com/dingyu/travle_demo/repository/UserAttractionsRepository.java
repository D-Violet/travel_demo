package com.dingyu.travle_demo.repository;

import com.dingyu.travle_demo.model.Attractions;
import com.dingyu.travle_demo.model.User;
import com.dingyu.travle_demo.model.UserAttractions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAttractionsRepository extends JpaRepository<UserAttractions, String>, JpaSpecificationExecutor<UserAttractions> {

    List<UserAttractions> findUserAttractionsByUser(User user);

    UserAttractions findUserAttractionsByAttractionsAndUser(Attractions attractions, User user);
}
