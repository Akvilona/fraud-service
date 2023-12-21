package com.froud.fraudservice.mapper;

import com.froud.fraudservice.entity.DepoDataResultInner;
import com.froud.fraudservice.repository.DebtProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(config = MapperConfiguration.class)
public interface DepoDataMapper {

    List<DepoDataResultInner> toDepoDataResultInnerList(List<DebtProjection> debtProjectionList);

    @Mapping(target = "depositorType", source = ".", qualifiedByName = "mapDepositorType")
    DepoDataResultInner mapDepoDataResultInner(DebtProjection debtProjection);

    @Named("mapDepositorType")
    default String mapDepositorType(final DebtProjection debtProjection) {
        return debtProjection.getDepositor_Type();
    }
}
