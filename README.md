Android ContentProvider Generator
=================================

A tool to generate an Android ContentProvider.
It takes a set of entity (a.k.a "table") definitions as the input, and generates:
- a `ContentProvider` class
- a `SQLiteOpenHelper` class
- a `SQLiteOpenHelperCallbacks` class
- one `Columns` class per entity
- one `Cursor` class per entity
- one `ContentValues` class per entity
- one `Selection` class per entity
- one `Model` interface per entity


How to use
----------

### The `_config.json` file

This is where you declare a few parameters that will be used to generate the code.

These are self-explanatory so here is an example:
```json
{
	"syntaxVersion": 3,
	"projectPackageId": "com.example.app",
	"authority": "com.example.app.provider",
	"providerJavaPackage": "com.example.app.provider",
	"providerClassName": "ExampleProvider",
	"sqliteOpenHelperClassName": "ExampleSQLiteOpenHelper",
	"sqliteOpenHelperCallbacksClassName": "ExampleSQLiteOpenHelperCallbacks",
	"databaseFileName": "example.db",
	"databaseVersion": 1,
	"enableForeignKeys": true,
	"useAnnotations": true
}
```

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
	]
}
```

Notes:
- An `_id` primary key field is automatically (implicitly) declared for all entities. It must not be declared in the json file.
- `nullable` is optional (true by default).
- if `documentation` is present the value will be copied in Javadoc blocks in the generated code.

A more comprehensive sample is available in the [etc/sample](etc/sample) folder.

You can also have a look at the corresponding generated code in the [etc/sample/app](etc/sample/app/src/org/jraf/androidcontentprovidergenerator/sample/provider) folder.

By convention, you should name your entities and fields in lower case with words separated by '_', like in the example above.

### The `header.txt` file (optional)

If a `header.txt` file is present, its contents will be inserted at the top of every generated file.

### Get the tool

Download the jar from here:
https://github.com/BoD/android-contentprovider-generator/releases/latest

### Run the tool

`java -jar android_contentprovider_generator-1.9.3-bundle.jar -i <input folder> -o <output folder>`
- Input folder: where to find `_config.json` and your entity json files
- Output folder: where the resulting files will be generated

### Use the generated files

- When querying a table, use the corresponding `Selection` class as shown in this example:

```java
PersonSelection where = new PersonSelection();
where.firstName("John").or().age(42);
Cursor c = context.getContentResolver().query(PersonColumns.CONTENT_URI, projection,
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
PersonCursor person = where.query(getContentResolver());
person.moveToNext();
String lastName = person.getLastName();
Long age = person.getAge();
```
- When updating or inserting into a table, use the corresponding `ContentValues` class as shown in this example:

```java
PersonContentValues values = new PersonContentValues();
values.putFirstName("John").putAge(42);
context.getContentResolver().update(personUri, values.values(), null, null);
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
- The `team` table will be automatically joined when querying the `person` table (only if any `team` columns are included in the projection).
- Getters for `team` columns are generated in the `PersonCursor` wrapper.
- Of course if `team` has foreign keys they will also be handled (and recursively).

#### Limitations
- Foreign keys always reference the `_id` column (the implicit primary key of all tables) and thus must always be of type `Long`  - by design.
- **Only one foreign key to a particular table is allowed per table.**  In the example above only one column in `person` can point to `team`.
- **Loops** (i.e. A has a foreign key to B and B has a foreign key to A) **aren't detected.**  The generator will infinitely loop if they exist.
- Cases such as "A has a FK to B, B has a FK to C, A has a FK to C" generate ambiguities in the queries, because C columns appear twice.  In the [sample app](etc/sample/app/src/org/jraf/androidcontentprovidergenerator/sample/app/SampleActivity.java) you can see an example of how to deal with this case, using prefixes and aliases (SQL's `AS` keyword).


Sample
------

A sample is available in the [etc/sample](etc/sample) folder.

You can have a look at the corresponding generated code in the [etc/sample/app](etc/sample/app/src/org/jraf/androidcontentprovidergenerator/sample/provider) folder.

Here is the table shema of the sample:
![Table shema of the sample](etc/sample/sample-schema.png?raw=true "The sample")


Building
--------

You need maven to build this tool.

`mvn package`

This will produce `android_contentprovider_generator-1.9.3-bundle.jar` in the `target` folder.


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
