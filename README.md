Android ContentProvider Generator (acpg)
========================================

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Android%20ContentProvider%20Generator-brightgreen.svg?style=plastic)](https://android-arsenal.com/details/1/111)

A tool to generate Android ContentProviders.
It takes a set of entity (a.k.a "table") definitions as the input, and generates:
- a `ContentProvider` class
- an `SQLiteOpenHelper` class
- one `Columns` class per entity
- one `Cursor` class per entity
- one `ContentValues` class per entity
- one `Selection` class per entity
- one `Model` interface per entity
- one `Bean` class per entity (optionally)


Usage
-----

There are two possible ways to generate the code:

1. as part of the build script (with a Gradle plugin)
2. as a one-time step (using a command line tool)

The Gradle plugin is perhaps the 'cleaner' way in the sense that the generated
code won't be part of the source (not checked into VCS). The configuration is declared inside
the Gradle script which allows to update it easily.

Alternatively, a one-time generation can be done (typically at the beginning of the project.)
The generated code is part of the source and checked into VCS: this allows you
to modify it if you need to.

You can decide which option is the best for your project :)


### Option 1: Gradle plugin

Add this to your app's `build.gradle`:
```groovy
buildscript {
    dependencies {
        classpath 'org.jraf:acpg-gradle-plugin:1.13.1'
    }
}

apply plugin: 'org.jraf.acpg.gradleplugin'

(...)

// This is where you declare a few parameters used to generate the code
acpg {
    // Where to find the entity files (see 'Entity files' below)
    // Optional - default value: 'etc/acpg' in the root project
    entitiesDir file('etc/acpg-entities')

    // Java package in which all the code will be generated
    providerJavaPackage 'com.example.app.provider'

    // ContentProvider authority
    // "${applicationId}" will be substituted by BuildConfig.APPLICATION_ID in the generated code
    authority '${applicationId}.provider'

    // Name of the provider class
    providerClassName 'ExampleProvider'

    // Name of the db file
    databaseFileName 'example.db'

    // Version of the db
    databaseVersion 1

    // Name of the SQLiteOpenHelper class
    // Optional - default value: providerClassName + "SQLiteOpenHelper"
    sqliteOpenHelperClassName 'ExampleSQLiteOpenHelper'

    // Name of a subclass of BaseSQLiteOpenHelperCallbacks
    // Optional - this allows you to get called when the db is opened/created/upgraded
    sqliteOpenHelperCallbacksClassName 'ExampleSQLiteOpenHelperCallbacks'

    // Whether to enable foreign keys support (see 'Advanced usage' below)
    // Optional - default value: false
    enableForeignKeys true

    // Whether @Nullable/@NonNull annotations will be used in the generated code
    // Optional - default value: false
    useAnnotations true

    // Whether support library classes are used or the Android SDK ones (e.g. CursorLoader)
    // Optional - default value: false
    useSupportLibrary true

    // Whether to generate a 'Beans' class for each entity
    // Optional - default value: true
    generateBeans true

    // Name of a boolean field in BuildConfig to enable/disable debug logging in the generated code
    // Optional - default value: "DEBUG"
    debugLogsFieldName 'LOG_DEBUG_PROVIDER'

    // Version of the tool syntax (must be 4)
    // The allows to break the build immediately if an incompatible version of the tool is used. Safety first!
    // Optional - default value: 4
    syntaxVersion 4
}
```


### Option 2: Command line tool

The configuration is the same, except you declare it in a file named `_config.json`
in the same folder as the entity files.

Here is an example:
```json
{
	"syntaxVersion": 4,
	"packageName": "com.example.app",
	"providerJavaPackage": "com.example.app.provider",
	"authority": "${applicationId}.provider",
	"providerClassName": "ExampleProvider",
	"databaseFileName": "example.db",
	"databaseVersion": 1,
	"sqliteOpenHelperClassName": "ExampleSQLiteOpenHelper",
	"sqliteOpenHelperCallbacksClassName": "ExampleSQLiteOpenHelperCallbacks",
	"enableForeignKeys": true,
	"useAnnotations": true,
	"useSupportLibrary": true,
	"generateBeans": true,
	"debugLogsFieldName": "LOG_DEBUG_PROVIDER"
}
```

About `packageName`: this must be the same as the value of the `package` attribute in your manifest.
Not to be confused with the `applicationId` (see https://developer.android.com/studio/build/application-id.html)

#### Get and run the tool

Download the `acpg-cli-1.13.1.jar` file here:
https://github.com/BoD/android-contentprovider-generator/releases/latest

`java -jar acpg-cli-1.13.1.jar -i <input folder> -o <output folder>`
- Input folder: where to find `_config.json` and your entity json files
- Output folder: where the resulting files will be generated


### Entity files

Create one file per entity, naming it `<entity_name>.json`.
Inside each file, declare your fields (a.k.a "columns") with a name and a type.
You can also optionally declare a default value, an index flag, a documentation and a nullable flag.

Currently the type can be:
- `String` (SQLite type: `TEXT`)
- `Integer` (`INTEGER`)
- `Long` (`INTEGER`)
- `Float` (`REAL`)
- `Double` (`REAL`)
- `Boolean` (`INTEGER`)
- `Date` (`INTEGER`)
- `byte[]` (`BLOB`)
- `enum` (`INTEGER`).

You can also optionally declare table constraints.

Here is a `person.json` file as an example:

```json
{
	"documentation": "A human being which is part of a team.",
	"fields": [
		{
			"documentation": "First name of this person. For instance, John.",
			"name": "first_name",
			"type": "String",
			"defaultValue": "John"
		},
		{
			"documentation": "Last name (a.k.a. Given name) of this person. For instance, Smith.",
			"name": "last_name",
			"type": "String",
			"nullable": true,
			"defaultValue": "Doe"
		},
		{
			"name": "age",
			"type": "Integer",
			"index": true
		},
		{
			"name": "gender",
			"type": "enum",
			"enumName": "Gender",
			"enumValues": [
				"MALE",
				"FEMALE",
				{"OTHER": "Value to use when neither male nor female"}
			],
			"nullable": false
		}
	],

	"constraints": [
		{
			"name": "unique_name",
			"definition": "UNIQUE (first_name, last_name) ON CONFLICT REPLACE"
		}
	],
	
	"defaultOrder": "first_name, last_name, age DESC"
}
```

Notes:
- An `_id` primary key field is automatically (implicitly) declared for all entities. It must not be declared in the json file.
- `nullable` is optional (true by default).
- if `documentation` is present the value will be copied in Javadoc blocks in the generated code.
- the `constraints` and `defaultOrder` sections are optional

A more comprehensive sample is available in the [sample-app/etc/acpg](sample-app/etc/acpg) folder.

You can have a look at the corresponding generated code in the [etc/sample-generated-code](etc/sample-generated-code) folder.

By convention, you should name your entities and fields in lower case with words separated by '_', like in the example above.

### The `header.txt` file (optional)

If a `header.txt` file is present, its contents will be inserted at the top of every generated file.


### Use the generated files

- When querying a table, use the corresponding `Selection` class as shown in this example:

```java
PersonSelection where = new PersonSelection();
where.firstName("John").or().age(42);
Cursor c = context.getContentResolver().query(where.uri(), projection,
        where.sel(), where.args(), null);
```

- When using the results of a query, wrap the resulting `Cursor` in the corresponding wrapper class.  You can then use
the generated getters directly as shown in this example:

```java
PersonCursor person = new PersonCursor(c);
String lastName = person.getLastName();
Long age = person.getAge();
```

- You can also conveniently combine these two facilities by using the `query` (or `delete`) method:

```java
PersonSelection where = new PersonSelection();
where.firstName("John").or().age(42).orderByFirstName();
PersonCursor person = where.query(context);
person.moveToNext();
String lastName = person.getLastName();
Long age = person.getAge();
```
or, use a `CursorLoader`:
```java
where.getCursorLoader(context);
```

- When updating or inserting into a table, use the corresponding `ContentValues` class as shown in this example:

```java
PersonContentValues values = new PersonContentValues();
values.putFirstName("John").putAge(42);
context.getContentResolver().update(values.uri(), values.values(), null, null);
```
or
```java
values.insert(context);
```


Advanced usage
--------------

### Foreign key / joins

There is limited support for foreign keys and joins.
Here is an example of the syntax:
```json
{
	"fields": [
		{
			"name": "main_team_id",
			"type": "Long",
			"nullable": false,
			"foreignKey": {
				"table": "team",
				"onDelete": "CASCADE"
			}
		},
		{
			"name": "first_name",
			"type": "String",
			"nullable": false
		},

		(...)
}
```
In this example, the field `main_team_id` is a foreign key referencing the primary key of the `team` table.
- The appropriate `FOREIGN KEY` SQL constraint is generated (if `enableForeignKeys` is set to `true` in `_config.json`).
- The `team` table will be automatically joined when querying the `person` table `[1]`.
- Getters for `team` columns are generated in the `PersonCursor` wrapper.
- Of course if `team` has foreign keys they will also be handled (and recursively).

`[1]` A table is automatically joined if at least one of its columns is included in the projection.
If the projection is `null` (i.e. all columns), all the tables are joined. Caution: you should be extra careful when using a `null` projection
with joins because you will get several columns named `_id` in the results!

#### Limitations
- Foreign keys always reference the `_id` column (the implicit primary key of all tables) and thus must always be of type `Long`  - by design.
- **Only one foreign key to a particular table is allowed per table.**  In the example above only one column in `person` can point to `team`.
- **Loops** (i.e. A has a foreign key to B and B has a foreign key to A) **aren't detected.**  The generator will infinitely loop if they exist.
- Cases such as "A has a FK to B, B has a FK to C, A has a FK to C" generate ambiguities in the queries, because C columns appear twice.  In the [sample app](etc/sample/app/src/org/jraf/androidcontentprovidergenerator/sample/app/SampleActivity.java) you can see an example of how to deal with this case, using prefixes and aliases (SQL's `AS` keyword).


Sample
------

A sample is available in the [sample-app](sample-app) folder, with the entities in [sample-app/etc/acpg](sample-app/etc/acpg).

You can have a look at the corresponding generated code in the [etc/sample-generated-code](etc/sample-generated-code) folder.

Here is the table shema of the sample:
![Table shema of the sample](etc/sample-schema.png?raw=true "The sample")


Building
--------

This is a Gradle project.

`./gradlew install` to 'install' the Gradle plugin to your local maven repo

`./gradlew shadowJar` to build the cli tool


Similar tools
-------------
Here is a list of other tools that try to tackle the same problem.

I did not have the chance to try them out.

- https://github.com/SimonVT/schematic
- https://github.com/TimotheeJeannin/ProviGen
- http://triple-t.github.io/simpleprovider/
- https://github.com/foxykeep/ContentProviderCodeGenerator
- https://code.google.com/p/mdsd-android-content-provider/
- https://github.com/hamsterksu/Android-AnnotatedSQL
- http://robotoworks.com/mechanoid/doc/db/api.html
- https://github.com/robUx4/android-contentprovider-generator (a fork of this project that generates more code)
- https://github.com/novoda/sqlite-analyzer (based on sql statements, not json)
- https://github.com/ckurtm/simple-sql-provider


Licence
-------

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.

__*Just to be absolutely clear, this license applies to this program itself,
not to the source it will generate!*__
