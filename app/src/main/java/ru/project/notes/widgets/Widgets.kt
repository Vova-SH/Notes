package ru.project.notes.widgets

import androidx.compose.Composable
import androidx.ui.core.Modifier
import androidx.ui.foundation.*
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.VisualTransformation
import androidx.ui.layout.ColumnScope.weight
import androidx.ui.layout.Stack
import androidx.ui.text.SoftwareKeyboardController
import androidx.ui.text.TextLayoutResult
import androidx.ui.text.TextStyle

@Composable
fun TextFieldWithHint(
    hint: @Composable() () -> Unit,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    textColor: Color = Color.Unset,
    textStyle: TextStyle = currentTextStyle(),
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Unspecified,
    onFocusChange: (Boolean) -> Unit = {},
    onImeActionPerformed: (ImeAction) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onTextInputStarted: (SoftwareKeyboardController) -> Unit = {},
    cursorColor: Color = contentColor()
) {
    Stack(Modifier.weight(1f)) {
        TextField(value = value,
            modifier = modifier,
            onValueChange = onValueChange,
            textStyle = textStyle,
            keyboardType = keyboardType,
            imeAction = imeAction,
            onFocusChange = onFocusChange,
            textColor = textColor,
            onImeActionPerformed = onImeActionPerformed,
            visualTransformation = visualTransformation,
            onTextLayout = onTextLayout,
            cursorColor = cursorColor,
            onTextInputStarted = onTextInputStarted
        )
        if (value.text.isEmpty()) Box(modifier = modifier) { hint() }
    }
}