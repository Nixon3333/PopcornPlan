package com.drygin.popcornplan.network.websocket

/**
 * Created by Drygin Nikita on 03.08.2025.
 */
class WebSocketStarter(
    client: WebSocketClient,
    private val eventHandler: SyncEventHandler
) {
    init {
        client.start { event ->
            eventHandler.handle(event)
        }
    }
}