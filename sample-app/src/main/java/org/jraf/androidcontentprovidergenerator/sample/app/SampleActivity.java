package org.jraf.androidcontentprovidergenerator.sample.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.content.ContentUris;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

import org.jraf.androidcontentprovidergenerator.sample.R;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanyColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanyContentValues;
import org.jraf.androidcontentprovidergenerator.sample.provider.company.CompanySelection;
import org.jraf.androidcontentprovidergenerator.sample.provider.manual.ManualContentValues;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.Gender;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonContentValues;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonCursor;
import org.jraf.androidcontentprovidergenerator.sample.provider.person.PersonSelection;
import org.jraf.androidcontentprovidergenerator.sample.provider.personteam.PersonTeamColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.personteam.PersonTeamContentValues;
import org.jraf.androidcontentprovidergenerator.sample.provider.personteam.PersonTeamCursor;
import org.jraf.androidcontentprovidergenerator.sample.provider.personteam.PersonTeamSelection;
import org.jraf.androidcontentprovidergenerator.sample.provider.product.ProductContentValues;
import org.jraf.androidcontentprovidergenerator.sample.provider.product.ProductCursor;
import org.jraf.androidcontentprovidergenerator.sample.provider.product.ProductSelection;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.SerialNumberColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.serialnumber.SerialNumberContentValues;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamColumns;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamContentValues;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamCursor;
import org.jraf.androidcontentprovidergenerator.sample.provider.team.TeamSelection;

public class SampleActivity extends AppCompatActivity {
    private static final String TAG = SampleActivity.class.getSimpleName();

    private static final String[] FIRST_NAMES = {"James", "John", "Robert", "Michael", "William", "David", "Richard", "Charles", "Joseph", "Thomas",};
    private static final String[] LAST_NAMES = {"SMITH", "JOHNSON", "WILLIAMS", "BROWN", "JONES", "MILLER", "DAVIS", "GARCIA", "RODRIGUEZ", "WILSON",};
    private static final String[] COUNTRY_CODES = {"fr", "uk", "us", "de", "be",};
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
        findViewById(R.id.btnQueryTeamsWithCompany).setOnClickListener(mOnClickListener);
        findViewById(R.id.btnQueryProductsWithManual).setOnClickListener(mOnClickListener);
    }

    private final OnClickListener mOnClickListener = new OnClickListener() {
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

                case R.id.btnQueryTeamsWithCompany:
                    queryTeamsWithCompany();
                    break;

                case R.id.btnQueryProductsWithManual:
                    queryProductsWithManual();
                    break;
            }
        }
    };

    private void queryPeople() {
        // Query one person by some criteria
        PersonSelection personSelection = new PersonSelection();
        personSelection.firstName("James", "John").and().hasBlueEyes(true);
        String[] projection = {PersonColumns._ID, PersonColumns.FIRST_NAME, PersonColumns.LAST_NAME, PersonColumns.AGE};
        PersonCursor c = personSelection.query(this, projection);
        while (c.moveToNext()) {
            Log.d(TAG, c.getFirstName() + " " + c.getLastName() + " (age: " + c.getAge() + ")");
        }
        c.close();

        // Query one person by id
        personSelection = new PersonSelection();
        personSelection.id(2);
        c = personSelection.query(this, projection);
        while (c.moveToNext()) {
            Log.d(TAG, c.getId() + " - " + c.getFirstName() + " " + c.getLastName() + " (age: " + c.getAge() + ")");
        }
        c.close();

        // Another way to query one person
        Log.d(TAG, "---");
        Uri uri = ContentUris.withAppendedId(PersonColumns.CONTENT_URI, 2l);
        Cursor c2 = getContentResolver().query(uri, projection, null, null, null);
        c = new PersonCursor(c2);
        while (c.moveToNext()) {
            Log.d(TAG, c.getId() + " - " + c.getFirstName() + " " + c.getLastName() + " (age: " + c.getAge() + ")");
        }
        c.close();

        // Like / startsWitdh / contains / endsWith + order by
        Log.d(TAG, "---");
        personSelection = new PersonSelection();
        personSelection.lastNameEndsWith("SON").or().firstNameContains("ar", "ae").orderByLastName();
        c = personSelection.query(this, projection);
        while (c.moveToNext()) {
            Log.d(TAG, c.getId() + " - " + c.getFirstName() + " " + c.getLastName() + " (age: " + c.getAge() + ")");
        }
        c.close();
    }

    private void queryPeopleWithTeam() {
        PersonTeamSelection personTeamSelection = new PersonTeamSelection();
        personTeamSelection.personFirstName("James", "John");
        String[] projection = {PersonTeamColumns._ID, PersonColumns.FIRST_NAME, PersonColumns.LAST_NAME, PersonColumns.AGE, PersonColumns.COUNTRY_CODE,
                TeamColumns.NAME, TeamColumns.COUNTRY_CODE};
        PersonTeamCursor c = personTeamSelection.query(this, projection);
        while (c.moveToNext()) {
            Log.d(TAG, c.getPersonFirstName() + " " + c.getPersonLastName() + " (age: " + c.getPersonAge() + ", country code:" + c.getPersonCountryCode()
                    + " ) - team: " + c.getTeamName() + " (country code: " + c.getTeamCountryCode() + ")");
        }
        c.close();
    }

    private void queryPeopleWithTeamAndCompany() {
        // @formatter:off
        PersonTeamSelection personTeamSelection = new PersonTeamSelection();
        personTeamSelection.personFirstName("James", "John");
        String[] projection = {
                PersonTeamColumns._ID,
                PersonTeamColumns.PERSON_ID,
                PersonTeamColumns.TEAM_ID,
                PersonColumns.FIRST_NAME,
                PersonColumns.LAST_NAME,
                PersonColumns.AGE,
                PersonColumns.COUNTRY_CODE,
                TeamColumns.NAME,
                TeamColumns.COUNTRY_CODE,
                TeamColumns.PREFIX_SERIAL_NUMBER + "." + SerialNumberColumns.PART0 + " AS TEAM_SN_PART0", // In this case we need to manually prefix and alias
                TeamColumns.PREFIX_SERIAL_NUMBER + "." + SerialNumberColumns.PART1 + " AS TEAM_SN_PART1", // In this case we need to manually prefix and alias
                TeamColumns.COMPANY_ID,
                CompanyColumns.NAME,
                CompanyColumns.PREFIX_SERIAL_NUMBER + "." + SerialNumberColumns.PART0 + " AS COMPANY_SN_PART0",
                // In this case we need to manually prefix and alias
                CompanyColumns.PREFIX_SERIAL_NUMBER + "." + SerialNumberColumns.PART1 + " AS COMPANY_SN_PART1",
                // In this case we need to manually prefix and alias
        };
        PersonTeamCursor c = personTeamSelection.query(this, projection);
        while (c.moveToNext()) {
            Log.d(TAG,
                    c.getPersonFirstName() + " " + c.getPersonLastName() + " (age: " + c.getPersonAge() + ", country code:" + c.getPersonCountryCode() + ")"
                            + " - "
                            + "team: "
                            + c.getTeamId() + " " + c.getTeamName() + " (country code: " + c.getTeamCountryCode() + ", S/N: " +
                            c.getStringOrNull("TEAM_SN_PART0") + "/" + c.getStringOrNull("TEAM_SN_PART1") + ")"
                            + " - "
                            + "company: "
                            + c.getTeamCompanyId() + " " + c.getTeamCompanyName() + " (S/N: " + c.getStringOrNull("COMPANY_SN_PART0") + "/" +
                            c.getStringOrNull("COMPANY_SN_PART1") + ")");
        }
        c.close();
        // @formatter:on
    }

    private void queryTeamsWithCompany() {
        // All teams, with all columns (null projection)
        Log.d(TAG, "---");
        TeamSelection teamSelection = new TeamSelection();
        TeamCursor c = teamSelection.query(this);
        while (c.moveToNext()) {
            Log.d(TAG, c.getName());
        }
        c.close();

        // Teams with specific name (projection contains company columns)
        teamSelection = new TeamSelection();
        teamSelection.name("Red Legends").orderByCompanySerialNumberId(true);
        String[] projection = {TeamColumns._ID, TeamColumns.NAME, CompanyColumns.NAME,};
        c = teamSelection.query(this, projection);
        while (c.moveToNext()) {
            Log.d(TAG, c.getId() + " " + c.getName() + " - company: " + c.getCompanyName());
        }
        c.close();

        // Teams with specific id (null projection)
        teamSelection = new TeamSelection();
        teamSelection.id(0);
        c = teamSelection.query(this);
        while (c.moveToNext()) {
            Log.d(TAG, c.getId() + " " + c.getName() + " - company: " + c.getCompanyName());
        }
        c.close();
    }

    private void queryProductsWithManual() {
        // Demonstrate the getCursorLoader convenience method of Selection
        getSupportLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<Cursor>() {
            @Override
            public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
                ProductSelection productSelection = new ProductSelection();
                return productSelection.getCursorLoader(SampleActivity.this);
            }

            @Override
            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                ProductCursor c = (ProductCursor) cursor;
                while (c.moveToNext()) {
                    Log.d(TAG, c.getId() + " " + c.getName() + " - manual title: " + c.getManualTitle());
                }
                c.close();
            }

            @Override
            public void onLoaderReset(Loader<Cursor> loader) {}
        });
    }

    private void deleteBase() {
        // Delete person-teams first
        PersonTeamSelection personTeamSelection = new PersonTeamSelection();
        personTeamSelection.delete(this);

        // Now delete companies (which should also delete teams because of the "on delete cascade" constraint
        CompanySelection companySelection = new CompanySelection();
        companySelection.delete(this);

        // Now delete persons
        PersonSelection personSelection = new PersonSelection();
        personSelection.delete(this);
    }

    @SuppressWarnings("null")
    private void populateBase() {
        // Insert company serial numbers
        long googleSn = insertSerialNumber("C", "GOOG");
        long microsoftSn = insertSerialNumber("C", "MSFT");
        long appleSn = insertSerialNumber("C", "AAPL");

        // Insert companies
        long google = insertCompany("Google", "1600 Amphitheatre Pkwy, Mountain View, CA 94043", googleSn);
        long microsoft = insertCompany("Microsoft", "One Microsoft Way Redmond, WA 98052-7329 USA", microsoftSn);
        long apple = insertCompany("Apple", "1 Infinite Loop, Cupertino, CA 95014", appleSn);

        // Insert teams (and also team serial numbers)
        ArrayList<Long> teams = new ArrayList<>();
        teams.add(insertTeam(google, "Nunchuk Geckos", COUNTRY_CODES[sRandom.nextInt(COUNTRY_CODES.length)],
                insertSerialNumber("T", Integer.toHexString(sRandom.nextInt()))));
        teams.add(insertTeam(google, "Dancing Dingoes", COUNTRY_CODES[sRandom.nextInt(COUNTRY_CODES.length)],
                insertSerialNumber("T", Integer.toHexString(sRandom.nextInt()))));
        teams.add(insertTeam(google, "American Dragons", COUNTRY_CODES[sRandom.nextInt(COUNTRY_CODES.length)],
                insertSerialNumber("T", Integer.toHexString(sRandom.nextInt()))));
        teams.add(insertTeam(microsoft, "New York Lightning", COUNTRY_CODES[sRandom.nextInt(COUNTRY_CODES.length)],
                insertSerialNumber("T", Integer.toHexString(sRandom.nextInt()))));
        teams.add(insertTeam(microsoft, "Red Legends", COUNTRY_CODES[sRandom.nextInt(COUNTRY_CODES.length)],
                insertSerialNumber("T", Integer.toHexString(sRandom.nextInt()))));
        teams.add(insertTeam(microsoft, "Awesome Predators", COUNTRY_CODES[sRandom.nextInt(COUNTRY_CODES.length)],
                insertSerialNumber("T", Integer.toHexString(sRandom.nextInt()))));
        teams.add(insertTeam(apple, "Cyborg Chasers", COUNTRY_CODES[sRandom.nextInt(COUNTRY_CODES.length)],
                insertSerialNumber("T", Integer.toHexString(sRandom.nextInt()))));
        teams.add(insertTeam(apple, "Kamikaze Falcons", COUNTRY_CODES[sRandom.nextInt(COUNTRY_CODES.length)],
                insertSerialNumber("T", Integer.toHexString(sRandom.nextInt()))));

        // Insert persons
        ArrayList<Long> persons = new ArrayList<>();
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
            String countryCode = COUNTRY_CODES[sRandom.nextInt(COUNTRY_CODES.length)];
            persons.add(insertPerson(mainTeamId, secondaryTeamId, firstName, lastName, age, birthDate, hasBlueEyes, height, gender, countryCode));
        }

        // Insert persons-teams
        for (int i = 0; i < 150; i++) {
            try {
                insertPersonTeam(persons.get(sRandom.nextInt(persons.size())), teams.get(sRandom.nextInt(teams.size())));
            } catch (SQLException e) {
                // This can happen because of the unique constraint on person - some ids in persons can sometimes no longer exist
                Log.w(TAG, "populateBase Trying to join with a person id that does not exist: this can happen, and should be expected", e);
            }
        }

        // Insert a manual
        long manualId = insertManual("How to use product Foobar", "4242-888-11");

        // Insert 2 products
        insertProduct("Foobar", manualId);
        insertProduct("Hectomatic", null);
    }

    /**
     * Insert a product.
     *
     * @return the id of the created product.
     */
    private long insertProduct(@NonNull String name, @Nullable Long manualId) {
        ProductContentValues values = new ProductContentValues();
        values.putName(name);
        values.putManualId(manualId);

        Uri uri = values.insert(this);
        return ContentUris.parseId(uri);
    }

    /**
     * Insert a manual.
     *
     * @return the id of the created manual.
     */
    private long insertManual(@NonNull String title, @NonNull String isbn) {
        ManualContentValues values = new ManualContentValues();
        values.putTitle(title);
        values.putIsbn(isbn);

        Uri uri = values.insert(this);
        return ContentUris.parseId(uri);
    }

    /**
     * Insert a serial number.
     *
     * @return the id of the created serialnumber.
     */
    private long insertSerialNumber(@NonNull String part0, @NonNull String part1) {
        SerialNumberContentValues values = new SerialNumberContentValues();
        values.putPart0(part0);
        values.putPart1(part1);

        Uri uri = values.insert(this);
        return ContentUris.parseId(uri);
    }

    /**
     * Insert a company.
     *
     * @return the id of the created company.
     */
    private long insertCompany(@NonNull String name, String address, long serialNumberId) {
        CompanyContentValues values = new CompanyContentValues();
        values.putName(name);
        values.putAddress(address);
        values.putSerialNumberId(serialNumberId);

        Uri uri = values.insert(this);
        return ContentUris.parseId(uri);
    }

    /**
     * Insert a team.
     *
     * @return the id of the created team.
     */
    private long insertTeam(long companyId, @NonNull String name, @NonNull String countryCode, long serialNumberId) {
        TeamContentValues values = new TeamContentValues();
        values.putCompanyId(companyId);
        values.putName(name);
        values.putCountryCode(countryCode);
        values.putSerialNumberId(serialNumberId);

        Uri uri = values.insert(this);
        return ContentUris.parseId(uri);
    }

    /**
     * Insert a person.
     *
     * @return the id of the created person.
     */
    private long insertPerson(long mainTeamId, long secondaryTeamId, @NonNull String firstName, @NonNull String lastName, int age, Date birthDate,
                              boolean hasBlueEyes, Float height, @NonNull Gender gender, @NonNull String countryCode) {
        PersonContentValues values = new PersonContentValues();
        values.putFirstName(firstName);
        values.putLastName(lastName);
        values.putAge(age);
        values.putBirthDate(birthDate);
        values.putHasBlueEyes(hasBlueEyes);
        values.putHeight(height);
        values.putGender(gender);
        values.putCountryCode(countryCode);

        Uri uri = values.insert(this);
        return ContentUris.parseId(uri);
    }

    /**
     * Insert a person-team.
     *
     * @return the id of the created person-team.
     */
    private long insertPersonTeam(long personId, long teamId) {
        PersonTeamContentValues values = new PersonTeamContentValues();
        values.putPersonId(personId);
        values.putTeamId(teamId);

        Uri uri = values.insert(this);
        return ContentUris.parseId(uri);
    }

    @SuppressWarnings("null")
    private static
    @NonNull
    String getRandomFirstName() {
        return FIRST_NAMES[sRandom.nextInt(FIRST_NAMES.length)];
    }

    @SuppressWarnings("null")
    private static
    @NonNull
    String getRandomLastName() {
        return LAST_NAMES[sRandom.nextInt(LAST_NAMES.length)];
    }
}
