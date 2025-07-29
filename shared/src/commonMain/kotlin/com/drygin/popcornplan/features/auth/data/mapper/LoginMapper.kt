package com.drygin.popcornplan.features.auth.data.mapper

import com.drygin.popcornplan.features.auth.data.remote.dto.LoginRequestDto
import com.drygin.popcornplan.features.auth.domain.model.LoginRequest

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
fun LoginRequestDto.toDomain() = LoginRequest(userName)