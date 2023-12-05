package com.froud.fraudservice.mapper;

import com.froud.fraudservice.entity.FraudUser;
import com.froud.fraudservice.entity.FraudUsersEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Random;

// Как работает MapperConfiguration ?
@Mapper(config = MapperConfiguration.class)
public interface FraudUserMapper {

    Random RANDOM = new Random();

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "phone", source = "phone")
    @Mapping(target = "id", expression = "java( RANDOM.nextLong() )")
    FraudUsersEntity mapToEntity(FraudUser fraudUser);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "fraudUser.name")
    @Mapping(target = "phone", source = "fraudUser.phone")
    void updateFraudUserEntity(@MappingTarget FraudUsersEntity fraudUsersEntity, FraudUser fraudUser);

    FraudUser mapToFraudUser(FraudUsersEntity fraudUsersEntity);

    FraudUsersEntity mapToFraudUserEntity(FraudUser fraudUser);
}
