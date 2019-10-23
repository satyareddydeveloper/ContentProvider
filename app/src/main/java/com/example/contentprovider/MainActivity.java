package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements LoaderManager.
        LoaderCallbacks<Cursor>{
    private ListView listView;

    private SimpleCursorAdapter adapter;

    private static final int LOADER_ID = 1976;

    // Request code for READ_CONTACTS.
    private static final int PERMISSIONS_REQUEST_WRITE_CONTACTS = 100;

    final String[] from =   new String[]{ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME};

    final int[] to = new int[] { R.id.name, R.id.address };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("sathi eXmaples");
        listView = (ListView) findViewById(R.id.list_view);
        listView.setEmptyView(findViewById(R.id.empty));

        adapter = new SimpleCursorAdapter(this,
                R.layout.activity_view_record, null, from, to, 0);

       listView.setAdapter(adapter);

       requestPermissions();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.add_record) {
            Intent add_mem = new Intent(this, AddContactActivity.class);
            startActivity(add_mem);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        /* Display all of the Contacts */
        String selection = ContactsContract.Data.MIMETYPE + " = ?";
        String[] selectionArgs = new String[]{ContactsContract.
                CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};

        return new CursorLoader(this, ContactsContract.Data.CONTENT_URI,
                new String[]{ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME},

                selection, selectionArgs,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC");

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Swap the new cursor in.  (The framework will take care of closing the
        // old cursor once we return.)
      //
            adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // This is called when the last Cursor provided to onLoadFinished()
        // above is about to be closed.  We need to make sure we are no
        // longer using it.
        adapter.swapCursor(null);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (requestCode == PERMISSIONS_REQUEST_WRITE_CONTACTS)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                // Permission is granted
                getSupportLoaderManager().initLoader(LOADER_ID, null, this);
            } else {
                Toast.makeText(this, "You must grant permission to display contacts", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void requestPermissions()
    {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED)
        {
            // No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this, new String[]{Manifest
                    .permission.WRITE_CONTACTS}, PERMISSIONS_REQUEST_WRITE_CONTACTS);
        }
        else
        {
            getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        }
    }


}
