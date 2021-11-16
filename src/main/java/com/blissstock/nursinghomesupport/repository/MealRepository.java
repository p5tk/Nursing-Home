package com.blissstock.nursinghomesupport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.blissstock.nursinghomesupport.entity.Meal;

@Repository
public interface MealRepository extends CrudRepository<Meal, Integer> {

}
