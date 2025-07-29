package com.drygin.popcornplan.features.auth.data.mapper

import com.drygin.popcornplan.features.auth.data.remote.dto.TokenResponseDto
import com.drygin.popcornplan.features.auth.domain.model.Token

/**
 * Created by Drygin Nikita on 29.07.2025.
 */
fun TokenResponseDto.toDomain() = Token(accessToken)

fun Token.toDto() = TokenResponseDto(accessToken)