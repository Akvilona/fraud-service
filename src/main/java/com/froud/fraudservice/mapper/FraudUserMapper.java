package com.froud.fraudservice.mapper;

import com.froud.fraudservice.entity.FraudUserEntity;
import com.froud.fraudservice.server.dto.FraudUser;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfiguration.class)
public interface FraudUserMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "name")
    @Mapping(target = "userEmail", source = "email")
    FraudUser toFraudUser(FraudUserEntity fraudUserEntity);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "firstName")
    @Mapping(target = "email", source = "userEmail")
    FraudUserEntity toFraudUserEntity(FraudUser fraudUser);
}
