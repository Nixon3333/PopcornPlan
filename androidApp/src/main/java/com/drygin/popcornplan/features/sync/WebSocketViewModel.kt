package com.drygin.popcornplan.features.sync

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drygin.popcornplan.features.auth.domain.usecase.LoginUseCase
import com.drygin.popcornplan.features.sync.domain.usecase.SyncUseCase
import com.drygin.popcornplan.network.websocket.SyncEventHandler
import com.drygin.popcornplan.network.websocket.WebSocketClient
import kotlinx.coroutines.launch

/**
 * Created by Drygin Nikita on 28.07.2025.
 */
class WebSocketViewModel(
    private val webSocketClient: WebSocketClient,
    private val syncEventHandler: SyncEventHandler,
    loginUseCase: LoginUseCase,
    syncUseCase: SyncUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            webSocketClient.connect { event ->
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