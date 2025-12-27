package com.oliviermarteaux.shared.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.CustomAccessibilityAction
import androidx.compose.ui.semantics.customActions
import androidx.compose.ui.semantics.hideFromAccessibility
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedSearchBar(
    modifier: Modifier = Modifier,
    //_ text field
    textFieldValue: TextFieldValue,
    onQueryChange: (TextFieldValue) -> Unit,
    onSearch: () -> Unit,
    textFieldModifier: Modifier = Modifier,
    searchLabel: String = "",
    //_ icon
    iconModifier: Modifier = Modifier,
    icon: IconSource/* = IconSource.VectorIcon(Icons.Default.Clear)*/,
    onIconClick: () -> Unit/* = {}*/,
    iconSemantics: String/* = ""*/,
){
    Box(
        contentAlignment = Alignment.CenterStart
    ) {
        Surface(
            modifier = Modifier
                .height(48.dp)
                .fillMaxWidth()
                .clip(CircleShape),
            color = SearchBarDefaults.colors().containerColor
        ){}
        Row(
            modifier = modifier
                .clip(shape = CircleShape)
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            //_ Text field
            SharedTextFieldValue(
                value = textFieldValue,
                onValueChange = onQueryChange,
                modifier = textFieldModifier
                    .semantics {
                        customActions = listOf(
                            CustomAccessibilityAction(
                                label = iconSemantics,
                                action = {
                                    onIconClick()
                                    true
                                }
                            )
                        )
                    }
                    .weight(1f),
                placeholder = searchLabel,
                singleLine = true,
                imeAction = ImeAction.Search,
                keyboardActions = KeyboardActions(
                    onSearch = { onSearch() }
                ),
            )

            //_ Trailing icon (add cdButtonSemantics to make it talkback-accessible)
            SharedIconButton(
                icon = icon,
                onClick = onIconClick,
                modifier = iconModifier.semantics { hideFromAccessibility() }//.cdButtonSemantics("Clear the search bar"),
            )
        }
    }
}