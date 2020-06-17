package ru.project.notes.fragments.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.Recomposer
import androidx.compose.state
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.ui.core.Modifier
import androidx.ui.core.focus.FocusModifier
import androidx.ui.core.focus.focusState
import androidx.ui.core.setContent
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.TextFieldValue
import androidx.ui.foundation.currentTextStyle
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.graphics.Color
import androidx.ui.input.ImeAction
import androidx.ui.input.KeyboardType
import androidx.ui.input.VisualTransformation
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Close
import androidx.ui.material.icons.filled.Done
import androidx.ui.text.TextRange
import androidx.ui.unit.dp
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.project.notes.NoteModel
import ru.project.notes.R
import ru.project.notes.fragments.main.MainFragment
import ru.project.notes.widgets.TextFieldWithHint

class EditNoteFragment : Fragment() {
    private val viewModel: EditNoteViewModel by viewModel {
        parametersOf(arguments!!.getSerializable(ARG_NOTE) as NoteModel)
    }

    private val themeColors = lightColorPalette(
        background = Color(0xfffafafa)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.saveCompleted.observe(viewLifecycleOwner, Observer {
            requireFragmentManager().beginTransaction().replace(
                R.id.root, MainFragment()
            ).commit()
        })

        val fabShape = CircleShape
        val fragmentView = FrameLayout(inflater.context)
        (fragmentView as ViewGroup).setContent(Recomposer.current()) {
            MaterialTheme(colors = themeColors) {
                val state = state {
                    Note(
                        TextFieldValue(text = viewModel.noteModel.title),
                        TextFieldValue(text = viewModel.noteModel.text)
                    )
                }
                Scaffold(
                    bottomAppBar = { fabConfiguration ->
                        BottomAppBar(fabConfiguration = fabConfiguration, cutoutShape = fabShape) {
                            IconButton(onClick = {
                                requireFragmentManager().beginTransaction().replace(
                                    R.id.root, MainFragment()
                                ).commit()
                            }) {
                                Icon(Icons.Filled.Close)
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            icon = { Icon(Icons.Filled.Done) },
                            shape = fabShape,
                            onClick = {
                                viewModel.addNote()
                            }
                        )
                    },
                    floatingActionButtonPosition = Scaffold.FabPosition.EndDocked
                ) {
                    Column(modifier = it) {
                        val focus = FocusModifier()
                        FilledTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = state.value.title,
                            onValueChange = { value ->
                                state.value = state.value.copy(title = value)
                                viewModel.noteModel = viewModel.noteModel.copy(title = value.text)
                            },
                            imeAction = ImeAction.Next,
                            onImeActionPerformed = {_,_ ->
                                focus.requestFocus()
                            },
                            label = { Text("Note title") },
                            backgroundColor = Color.White
                        )
                        TextFieldWithHint(
                            hint = {
                                Text(
                                    "Write your note",
                                    style = currentTextStyle().copy(color = Color.DarkGray)
                                )
                            },
                            modifier = Modifier.fillMaxSize().padding(8.dp).plus(focus),
                            value = state.value.text,
                            onValueChange = { value ->
                                state.value = state.value.copy(text = value)
                                viewModel.noteModel = viewModel.noteModel.copy(text = value.text)
                            },
                            keyboardType = KeyboardType.Text,
                            onImeActionPerformed = {
                                state.value = state.value.copy(
                                    text = state.value.text.copy(
                                        text = multilineTextFix(
                                            state.value.text.text,
                                            state.value.text.selection
                                        ),
                                        selection = multilineSelectionFix(state.value.text.selection)
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
        return fragmentView
    }


    private fun multilineTextFix(text: String, textRange: TextRange): String {
        return text.substring(0, textRange.start) + "\n" + text.substring(
            textRange.end - 1,
            text.length - 1
        )

    }

    private fun multilineSelectionFix(textRange: TextRange): TextRange =
        textRange.copy(start = textRange.start + 1, end = textRange.end + 1)


    data class Note(
        val title: TextFieldValue,
        val text: TextFieldValue
    )


    companion object {
        const val ARG_NOTE = "note"
        fun newInstance(noteModel: NoteModel): EditNoteFragment {
            val fragment = EditNoteFragment()
            val args = Bundle()
            args.putSerializable(ARG_NOTE, noteModel)
            fragment.arguments = args
            return fragment
        }
    }
}
