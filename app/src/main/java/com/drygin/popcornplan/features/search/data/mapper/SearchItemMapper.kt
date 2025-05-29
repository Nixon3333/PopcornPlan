package com.drygin.popcornplan.features.search.data.mapper

import com.drygin.popcornplan.common.data.mapper.toDomain
import com.drygin.popcornplan.features.search.data.model.SearchItemDto
import com.drygin.popcornplan.features.search.domain.model.SearchItem

/**
 * Created by Drygin Nikita on 28.05.2025.
 */
fun SearchItemDto.toDomain() = SearchItem(type, score, movie.toDomain())