package com.appinventiv.chatcomposeapp.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.getstream.chat.android.compose.ui.channels.ChannelsScreen
import io.getstream.chat.android.compose.ui.theme.ChatTheme

class ChannelListActivity : ComponentActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatTheme {
                ChannelsScreen(
                    title = "Channel List",
                    isShowingSearch = true,
                    onItemClick = {

                    },
                    onBackPressed = { finish() }
                )
            }
        }
    }
}