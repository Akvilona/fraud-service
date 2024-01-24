package com.froud.fraudservice.mapper;

import com.froud.fraudservice.entity.FraudUserEntity;
import com.froud.fraudservice.server.dto.FraudUser;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfiguration.class)
public interface FraudUserMapper {

    FraudUser toFraudUser(FraudUserEntity fraudUserEntity);

    FraudUserEntity toFraudUserEntity(FraudUser fraudUser);
}
