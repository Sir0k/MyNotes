 package com.example.mynotes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mynotes.Models.Notes;

import java.text.SimpleDateFormat;
import java.util.Date;

 public class NotesTakerActivity extends AppCompatActivity {

    EditText editText_title, editText_notes;
    ImageView imageView_save;
    Notes notes;
    boolean isOldNote = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_taker);

        editText_title = findViewById(R.id.editText_title);
        editText_notes = findViewById(R.id.editText_notes);

        imageView_save = findViewById(R.id.imageView_save);

        notes = new Notes();
        try {
            notes = (Notes) getIntent().getSerializableExtra("old_note");
            editText_title.setText(notes.getTitle());
            editText_notes.setText(notes.getNotes());
            isOldNote = true;

        }catch (Exception e){
            e.printStackTrace();
        }


        imageView_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                String description = editText_notes.getText().toString();

                if (description.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Добавьте текст заметки", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (title.isEmpty()) {
                    Toast.makeText(NotesTakerActivity.this, "Добавьте текст заголовка", Toast.LENGTH_SHORT).show();
                    return;
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(NotesTakerActivity.this);
                builder.setTitle("Сохранение заметки");
                builder.setMessage("Вы уверены, что хотите сохранить заметку?");
                builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy");
                        Date date = new Date();
                        String toastText = "Заметка успешно изменена";

                        if (!isOldNote) {
                            notes = new Notes();
                            toastText = "Заметка успешно добавлена";
                            Toast.makeText(NotesTakerActivity.this, toastText, Toast.LENGTH_SHORT).show();
                        }

                        notes.setTitle(title);
                        notes.setNotes(description);
                        notes.setDate(formatter.format(date));

                        Intent intent = new Intent();
                        intent.putExtra("notes", notes);
                        setResult(Activity.RESULT_OK, intent);
                        Toast.makeText(NotesTakerActivity.this, toastText, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();





            }

        });

    }
}