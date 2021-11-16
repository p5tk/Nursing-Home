package com.blissstock.nursinghomesupport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blissstock.nursinghomesupport.entity.ContactPerson;

@Repository
public interface ContactPersonRepository extends JpaRepository<ContactPerson,Long> {
	
}
