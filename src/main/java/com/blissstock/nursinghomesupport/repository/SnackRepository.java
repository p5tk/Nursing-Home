package com.blissstock.nursinghomesupport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.blissstock.nursinghomesupport.entity.Snack;

@Repository
public interface SnackRepository extends CrudRepository<Snack, Integer> {

}
