package com.blissstock.nursinghomesupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blissstock.nursinghomesupport.entity.PersonalCare;

@Repository
public interface PersonalCareRepository extends JpaRepository<PersonalCare, Long> {

}
