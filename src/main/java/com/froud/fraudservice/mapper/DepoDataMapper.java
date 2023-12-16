package com.froud.fraudservice.mapper;

import com.froud.fraudservice.entity.DepoDataResultInner;
import com.froud.fraudservice.repository.DebtProjection;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface DepoDataMapper {
    List<DepoDataResultInner> toDepoDataResultInnerList(List<DebtProjection> debtProjectionList);
}
