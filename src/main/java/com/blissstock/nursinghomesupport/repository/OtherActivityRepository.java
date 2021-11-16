package com.blissstock.nursinghomesupport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.blissstock.nursinghomesupport.entity.OtherActivity;

@Repository
public interface OtherActivityRepository extends CrudRepository<OtherActivity, Integer> {

}
