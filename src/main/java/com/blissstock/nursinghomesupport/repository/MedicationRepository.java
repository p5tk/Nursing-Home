package com.blissstock.nursinghomesupport.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.blissstock.nursinghomesupport.entity.Medication;

@Repository
public interface MedicationRepository extends CrudRepository<Medication, Integer> {

}
