package ru.project.notes.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.*
import androidx.fragment.app.Fragment
import androidx.ui.core.Modifier
import androidx.ui.core.setContent
import androidx.ui.foundation.*
import androidx.ui.foundation.shape.corner.CircleShape
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.Column
import androidx.ui.layout.fillMaxSize
import androidx.ui.layout.fillMaxWidth
import androidx.ui.layout.padding
import androidx.ui.material.*
import androidx.ui.material.icons.Icons
import androidx.ui.material.icons.filled.Add
import androidx.ui.material.icons.filled.Favorite
import androidx.ui.material.icons.filled.Info
import androidx.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.project.notes.NoteModel
import ru.project.notes.R
import ru.project.notes.fragments.add.EditNoteFragment

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()

    private val themeColors = lightColorPalette(
        background = Color(0xfffafafa)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fabShape = CircleShape
        val fragmentView = FrameLayout(inflater.context)
        (fragmentView as ViewGroup).setContent(Recomposer.current()) {
            MaterialTheme(colors = themeColors) {
                val showDialog = state { false }
                val typography = MaterialTheme.typography
                Scaffold(
                    bottomAppBar = { fabConfiguration ->
                        BottomAppBar(fabConfiguration = fabConfiguration, cutoutShape = fabShape) {
                            IconButton(onClick = {showDialog.value = true}) {
                                Icon(Icons.Filled.Info)
                            }
                        }
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            icon = { Icon(Icons.Filled.Add) },
                            shape = fabShape,
                            onClick = {
                                requireFragmentManager()
                                    .beginTransaction()
                                    .replace(
                                        R.id.root,
                                        EditNoteFragment.newInstance(NoteModel(0, "", ""))
                                    )
                                    .commit()
                            }
                        )
                    },
                    floatingActionButtonPosition = Scaffold.FabPosition.CenterDocked
                ) {
                    ListItems(it, viewModel.notes, typography)
                    if(showDialog.value) DialogInfo(showDialog)
                }
            }
        }
        return fragmentView
    }

    @Composable
    private fun DialogInfo(state: MutableState<Boolean>) {
        AlertDialog(
            onCloseRequest = {
            },
            title = {Text(resources.getString(R.string.info_title))},
            text =  {Text(resources.getString(R.string.info_text))},
            confirmButton = {
                Button(text = { Text("OK")}, onClick = {state.value = false})
            }
        )
    }

    @Composable
    private fun ListItems(
        modifier: Modifier,
        notesData: Flow<List<NoteModel>>,
        typography: Typography
    ) {
        val noteList by notesData.collectAsState()
        if (noteList == null) {
            Box(gravity = ContentGravity.Center, modifier = modifier.fillMaxSize()) {
                Text("Notes loading...")
            }
        } else if (noteList!!.isEmpty()) {
            Box(gravity = ContentGravity.Center, modifier = modifier.fillMaxSize()) {
                Text("No notes yet :)")
            }
        } else {
            AdapterList(modifier = modifier, data = noteList!!) {
                Item(it, typography)
            }
        }
    }

    @Composable
    private fun Item(note: NoteModel, typography: Typography) {
        Card(
            modifier = Modifier.padding(8.dp, 4.dp).fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier.clickable(
                    onLongClick = {
                        viewModel.removeNote(note)
                    },
                    onClick = {
                        requireFragmentManager()
                            .beginTransaction()
                            .replace(R.id.root, EditNoteFragment.newInstance(note))
                            .commit()
                    }
                ),
                padding = 16.dp
            ) {
                Column {
                    Text(note.title, style = typography.h5)
                    Text(note.text, style = typography.body2, maxLines = 1, softWrap = true)
                }
            }
        }
    }
}
