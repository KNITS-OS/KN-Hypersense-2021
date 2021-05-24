package com.knits.smartfactory.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompanyUserMapperTest {

    private CompanyUserMapper companyUserMapper;

    @BeforeEach
    public void setUp() {
        companyUserMapper = new CompanyUserMapperImpl();
    }
}
