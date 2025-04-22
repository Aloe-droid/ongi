package com.aloe_droid.data.repository.mapper

import com.aloe_droid.data.datasource.dto.user.UserDTO
import com.aloe_droid.domain.entity.User

object UserMapper {
    fun UserDTO.toUser(): User = User(
        id = id,
        address = address
    )

    fun User.toUserDTO(): UserDTO = UserDTO(
        id = id,
        address = address
    )
}
