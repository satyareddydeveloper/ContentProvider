package com.example.contentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddContactActivity extends AppCompatActivity {

    private static EditText firstName;
    private static EditText lastName;
    private static EditText street;
    private static EditText city;
    private static EditText state;
    private static EditText zip;
    private static boolean nameEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        firstName = (EditText) findViewById(R.id.editText_firstName);
        lastName = (EditText) findViewById(R.id.editText_lastname);
        street = (EditText) findViewById(R.id.editText_street);
        city = (EditText) findViewById(R.id.editText_city);
        state = (EditText) findViewById(R.id.editText_state);
        zip = (EditText) findViewById(R.id.editText_zipcode);

    }

    public void okButtonClicked(View view) {
        String displayFirstName = firstName.getText().toString();
        String displayLastName = lastName.getText().toString();
        String displayStreet = street.getText().toString();
        String displayCity = city.getText().toString();
        String displayState = state.getText().toString();
        String displayZip = zip.getText().toString();
        if (nameEmpty)
        {
            Toast.makeText(this, "A First or Last Name is required", Toast.LENGTH_SHORT).show();
        }
        else
        {
            String accountType = null;
            String accountName = null;

            // Create the contact
            ContentValues values = new ContentValues();
            values.put(ContactsContract.RawContacts.ACCOUNT_TYPE, accountType);
            values.put(ContactsContract.RawContacts.ACCOUNT_NAME, accountName);
            Uri rawContactUri = getContentResolver().insert(ContactsContract.
                    RawContacts.CONTENT_URI, values);
            long rawContactId = ContentUris.parseId(rawContactUri);

            values.clear();

            // Add the first and last names to the new contact
            values.put(ContactsContract.Data.RAW_CONTACT_ID, rawContactId);
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, displayFirstName);
            values.put(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME, displayLastName);
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

            // Add the postal information to the new contact
            values.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE);
            values.put(ContactsContract.CommonDataKinds.StructuredPostal.STREET, displayStreet);
            values.put(ContactsContract.CommonDataKinds.StructuredPostal.REGION, displayState);
            values.put(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE, displayZip);
            values.put(ContactsContract.CommonDataKinds.StructuredPostal.TYPE, ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME);
            values.put(ContactsContract.CommonDataKinds.StructuredPostal.CITY, displayCity);

          Uri isiner=  getContentResolver().insert(ContactsContract.Data.CONTENT_URI, values);

        }

    }



    public void cancelButtonClicked(View view) {
        finish();
    }
}
