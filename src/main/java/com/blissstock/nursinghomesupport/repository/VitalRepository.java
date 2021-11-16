package com.blissstock.nursinghomesupport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.blissstock.nursinghomesupport.entity.Vital;

@Repository
public interface VitalRepository extends CrudRepository<Vital, Integer> {

}
