package com.blissstock.nursinghomesupport.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blissstock.nursinghomesupport.entity.HelperInfo;
import com.blissstock.nursinghomesupport.repository.HelperRepository;

@Service
public class HelperDao {

    @Autowired
    private HelperRepository helperRepo;

    @Transactional
    public List<HelperInfo> fetchHelpers(String term) {
        return helperRepo.fetchHelpers(term);
    }
}
