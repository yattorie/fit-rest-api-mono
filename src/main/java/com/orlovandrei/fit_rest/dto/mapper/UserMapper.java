package com.orlovandrei.fit_rest.dto.mapper;


import com.orlovandrei.fit_rest.dto.user.CreateUserRequest;
import com.orlovandrei.fit_rest.dto.user.UserResponse;
import com.orlovandrei.fit_rest.dto.user.UpdateUserRequest;
import com.orlovandrei.fit_rest.entity.user.Role;
import com.orlovandrei.fit_rest.entity.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "role", source = "role", qualifiedByName = "roleToString")
    UserResponse toDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    User toEntity(CreateUserRequest dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", source = "role", qualifiedByName = "stringToRole")
    User toEntity(UpdateUserRequest dto);

    UpdateUserRequest toUpdateDto(User entity);

    CreateUserRequest toCreateDto(User entity);

    @Named("roleToString")
    default String roleToString(Role role) {
        return role == null ? null : role.name();
    }

    @Named("stringToRole")
    default Role stringToRole(String role) {
        return role == null ? null : Role.valueOf(role);
    }
}
