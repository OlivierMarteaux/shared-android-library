package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.oliviermarteaux.shared.composables.texts.TextTitleLarge
import com.oliviermarteaux.shared.composables.texts.TextTitleSmall

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedScaffold(
    modifier: Modifier = Modifier,
    title: String = "",
    onFabClick: (() -> Unit)? = null,
    onBackClick: (() -> Unit)? = null,
    onMenuItem1Click: (() -> Unit)? = null,
    onMenuItem2Click: (() -> Unit)? = null,
    menuItem1Title: String = "",
    menuItem2Title: String = "",
    content: @Composable (contentPadding: PaddingValues) -> Unit = {},
){
    var showMenu by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { TextTitleLarge(title) },
                navigationIcon = {
                    onBackClick?.let {
                        SharedIconButton(
                            icon = IconSource.VectorIcon(Icons.AutoMirrored.Filled.ArrowBack),
                        ) { onBackClick() }
                    }
                },
                actions = {
                    onMenuItem1Click?.let{
                        SharedIconButton(
                            icon = IconSource.VectorIcon(Icons.Default.MoreVert),
                        ) { showMenu = !showMenu }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                onClick = {
                                    onMenuItem1Click()
                                    showMenu = false
                                },
                                text = {
                                    TextTitleSmall(text = menuItem1Title)
                                }
                            )
                            onMenuItem2Click?.let {
                                DropdownMenuItem(
                                    onClick = {
                                        onMenuItem2Click()
                                        showMenu = false
                                    },
                                    text = {
                                        TextTitleSmall(text = menuItem2Title)
                                    }
                                )
                            }
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            onFabClick?.let {
                FloatingActionButton(
                    modifier = Modifier.padding(bottom = 20.dp, end = 20.dp),
                    elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(10.dp),
                    onClick = onFabClick
                ) {
                    SharedIcon(
                        icon = IconSource.VectorIcon(Icons.Filled.Add),
                    )
                }
            }
        },
    ) { contentPadding -> content(contentPadding) }
}