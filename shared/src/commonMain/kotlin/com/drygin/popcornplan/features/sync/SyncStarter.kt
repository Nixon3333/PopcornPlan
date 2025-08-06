package com.drygin.popcornplan.features.sync

import com.drygin.popcornplan.domain.util.ApiResult
import com.drygin.popcornplan.features.auth.domain.usecase.LoginUseCase
import com.drygin.popcornplan.features.sync.domain.usecase.SyncUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

/**
 * Created by Drygin Nikita on 06.08.2025.
 */
class SyncStarter(
    loginUseCase: LoginUseCase,
    syncUseCase: SyncUseCase
) {
    init {
        println("SyncStarter init")
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val success = loginUseCase("Nixon")
                if (!success) {
                    println("Ошибка авторизации")
                    return@launch
                }

                when (val result = syncUseCase()) {
                    is ApiResult.Success -> println("Синхронизация выполнена")
                    is ApiResult.Error -> println("Синхронизация не удалась: ${result.message}")
                }
            } catch (e: Exception) {
                println("Необработанная ошибка: ${e.message}")
            }
        }
    }
}