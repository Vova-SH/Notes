package ru.project.notes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.project.notes.fragments.main.MainFragment


class RootActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.root_activity)
        supportFragmentManager.beginTransaction().replace(R.id.root, MainFragment()).commit()
    }
}
