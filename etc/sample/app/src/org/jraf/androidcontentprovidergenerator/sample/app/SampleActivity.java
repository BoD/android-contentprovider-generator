package org.jraf.androidcontentprovidergenerator.sample.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.app.Activity;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import org.jraf.androidcontentprovidergenerator.sample.R;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanyColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanyContentValues;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanySelection;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.Gender;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonContentValues;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonCursor;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonSelection;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamContentValues;

public class SampleActivity extends Activity {
    private static final String TAG = SampleActivity.class.getSimpleName();

    private static final String[] FIRST_NAMES = { "James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph", "Thomas", };
    private static final String[] LAST_NAMES = { "SMITH", "JOHNSON", "WILLIAMS", "BROWN", "JONES", "MILLER", "DAVIS", "GARCIA", "RODRIGUEZ", "WILSON", };
    private static long YEAR_IN_MS = 365 * 24 * 60 * 60 * 1000 * 1000L;
    private static final Random sRandom = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.btnPopulate).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnClear).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnQueryPeople).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnQueryPeopleWithTeam).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnQueryPeopleWithTeamAndCompany).setOnClickListener(mOnClickListener);

    }

    private OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnPopulate:
                    populateBase();
                    break;

                case R.id.btnClear:
                    deleteBase();
                    break;

                case R.id.btnQueryPeople:
                    queryPeople();
                    break;

                case R.id.btnQueryPeopleWithTeam:
                    queryPeopleWithTeam();
                    break;

                case R.id.btnQueryPeopleWithTeamAndCompany:
                    queryPeopleWithTeamAndCompany();
                    break;
            }
        }
    };

    private void queryPeople() {
        PersonSelection personSelection = new PersonSelection();
        personSelection.firstName("James", "John").and().hasBlueEyes(true);
        String[] projection = { PersonColumns._ID, PersonColumns.FIRST_NAME, PersonColumns.LAST_NAME, PersonColumns.AGE };
        PersonCursor c = personSelection.query(getContentResolver(), projection);
        while (c.moveToNext()) {
            Log.d(TAG, c.getFirstName() + " " + c.getLastName() + " (age: " + c.getAge() + ")");
        }
        c.close();

        // Query one person
        personSelection = new PersonSelection();
        personSelection.id(2);
        c = personSelection.query(getContentResolver(), projection);
        while (c.moveToNext()) {
            Log.d(TAG, c.getId() + " - " + c.getFirstName() + " " + c.getLastName() + " (age: " + c.getAge() + ")");
        }
        c.close();

        // Another way to query one person
        Uri uri = ContentUris.withAppendedId(PersonColumns.CONTENT_URI, 2l);
        Cursor c2 = getContentResolver().query(uri, projection, null, null, null);
        c = new PersonCursor(c2);
        while (c.moveToNext()) {
            Log.d(TAG, c.getId() + " - " + c.getFirstName() + " " + c.getLastName() + " (age: " + c.getAge() + ")");
        }
        c.close();
    }

    private void queryPeopleWithTeam() {
        PersonSelection personSelection = new PersonSelection();
        personSelection.firstName("James", "John");
        String[] projection = { PersonColumns._ID, PersonColumns.FIRST_NAME, PersonColumns.LAST_NAME, PersonColumns.AGE, TeamColumns.NAME };
        PersonCursor c = personSelection.query(getContentResolver(), projection);
        while (c.moveToNext()) {
            Log.d(TAG, c.getFirstName() + " " + c.getLastName() + " (age: " + c.getAge() + ") - team: " + c.getTeamName());
        }
        c.close();
    }

    private void queryPeopleWithTeamAndCompany() {
        PersonSelection personSelection = new PersonSelection();
        personSelection.firstName("James", "John");
        String[] projection = { PersonColumns.FIRST_NAME, PersonColumns.LAST_NAME, PersonColumns.AGE, TeamColumns.NAME, CompanyColumns.NAME };
        PersonCursor c = personSelection.query(getContentResolver(), projection);
        while (c.moveToNext()) {
            Log.d(TAG, c.getFirstName() + " " + c.getLastName() + " (age: " + c.getAge() + ") - team: " + c.getTeamName() + " - company: " + c.getCompanyName());
        }
        c.close();
    }

    private void deleteBase() {
        CompanySelection companySelection = new CompanySelection();
        companySelection.delete(getContentResolver());
    }

    private void populateBase() {
        long google = insertCompany("Google", "1600 Amphitheatre Pkwy, Mountain View, CA 94043");
        long microsoft = insertCompany("Microsoft", "One Microsoft Way Redmond, WA 98052-7329 USA");
        long apple = insertCompany("Apple", "1 Infinite Loop, Cupertino, CA 95014");

        ArrayList<Long> teams = new ArrayList<>();
        teams.add(insertTeam(google, "Nunchuk Geckos"));
        teams.add(insertTeam(google, "Dancing Dingoes"));
        teams.add(insertTeam(google, "American Dragons"));
        teams.add(insertTeam(microsoft, "New York Lightning"));
        teams.add(insertTeam(microsoft, "Red Legends"));
        teams.add(insertTeam(microsoft, "Awesome Predators"));
        teams.add(insertTeam(apple, "Cyborg Chasers"));
        teams.add(insertTeam(apple, "Kamikaze Falcons"));

        for (int i = 0; i < 40; i++) {
            long mainTeamId = teams.get(sRandom.nextInt(teams.size()));
            long secondaryTeamId = teams.get(sRandom.nextInt(teams.size()));
            String firstName = getRandomFirstName();
            String lastName = getRandomLastName();
            int age = sRandom.nextInt(30) + 21;
            Date birthDate = new Date(System.currentTimeMillis() - age * YEAR_IN_MS - (long) (sRandom.nextFloat() * YEAR_IN_MS));
            boolean hasBlueEyes = sRandom.nextBoolean();
            Float height = sRandom.nextFloat() + 1f;
            Gender gender = Gender.values()[sRandom.nextInt(Gender.values().length)];
            insertPerson(mainTeamId, secondaryTeamId, firstName, lastName, age, birthDate, hasBlueEyes, height, gender);
        }

    }

    private long insertCompany(String name, String address) {
        CompanyContentValues values = new CompanyContentValues();
        values.putName(name);
        values.putAddress(address);
        Uri uri = values.insert(getContentResolver());
        return ContentUris.parseId(uri);
    }

    private long insertTeam(long companyId, String name) {
        TeamContentValues values = new TeamContentValues();
        values.putCompanyId(companyId);
        values.putName(name);
        Uri uri = values.insert(getContentResolver());
        return ContentUris.parseId(uri);
    }

    private long insertPerson(long mainTeamId, long secondaryTeamId, String firstName, String lastName, int age, Date birthDate, boolean hasBlueEyes,
            Float height, Gender gender) {
        PersonContentValues values = new PersonContentValues();
        values.putMainTeamId(mainTeamId);
        values.putFirstName(firstName);
        values.putLastName(lastName);
        values.putAge(age);
        values.putBirthDate(birthDate);
        values.putHasBlueEyes(hasBlueEyes);
        values.putHeight(height);
        values.putGender(gender);

        Uri uri = values.insert(getContentResolver());
        return ContentUris.parseId(uri);
    }

    private static String getRandomFirstName() {
        return FIRST_NAMES[sRandom.nextInt(FIRST_NAMES.length)];
    }

    private static String getRandomLastName() {
        return LAST_NAMES[sRandom.nextInt(LAST_NAMES.length)];
    }
}
