package com.drygin.popcornplan.features.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.data.remote.SyncWebSocketClient
import com.drygin.popcornplan.features.auth.domain.usecase.LoginUseCase
import com.drygin.popcornplan.features.sync.domain.usecase.SyncUseCase
import com.drygin.popcornplan.reatures.sync.SyncEventHandler
import kotlinx.coroutines.launch

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
class WebSocketViewModel(
    private val syncWebSocketClient: SyncWebSocketClient,
    private val syncEventHandler: SyncEventHandler,
    loginUseCase: LoginUseCase,
    syncUseCase: SyncUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            syncWebSocketClient.connect { event ->
                syncEventHandler.handle(event)
            }
        }
        viewModelScope.launch {
            if (loginUseCase("Nixon")) {
                syncUseCase.invoke()
            }
        }
    }
}