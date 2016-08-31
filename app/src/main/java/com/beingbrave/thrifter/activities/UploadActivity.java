package com.beingbrave.thrifter.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.beingbrave.thrifter.R;
import com.beingbrave.thrifter.ThrifterApplication;
import com.beingbrave.thrifter.models.Post;
import com.nononsenseapps.filepicker.FilePickerActivity;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UploadActivity extends AppCompatActivity {

    private static final String TAG = "UploadActivity";
    private static final int SELECT_PICTURE = 1;

    private String selectedImagePath;

    @BindView(R.id.toolbar) Toolbar toolbar;

    @BindView(R.id.upload_text_title) EditText editTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        // Bind views
        ButterKnife.bind(this);

        // Use support library action bar instead of default
        setSupportActionBar(toolbar);

    }

    public void onClick(View v) {
        if(selectedImagePath == null) {
            Log.e(TAG, "No image selected for upload");
            return;
        }

        ThrifterApplication.getApi().createPost(
                new Post(editTitle.getText().toString(),
                        Uri.fromFile(new File(selectedImagePath))));

//        Intent intent = new Intent(UploadActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
    }


    public void onImageClick(View v) {
        // This always works
        Intent i = new Intent(getApplicationContext(), FilePickerActivity.class);
        // This works if you defined the intent filter
        // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

        // Set these depending on your use case. These are the defaults.
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
        i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
        i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

        // Configure initial directory by specifying a String.
        // You could specify a String like "/storage/emulated/0/", but that can
        // dangerous. Always use Android's API calls to get paths to the SD-card or
        // internal memory.
        i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

        startActivityForResult(i, SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = selectedImageUri.getPath();
                Log.d(TAG, selectedImagePath);
            }
        }
    }

}
