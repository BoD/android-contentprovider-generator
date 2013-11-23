Android ContentProvider Generator
=================================

A small tool to generate an Android ContentProvider.
It takes a set of entity (a.k.a "table") definitions as the input, and generates:
- a `ContentProvider` class
- a `SQLiteOpenHelper` class
- one `BaseColumns` class per entity 
- one `CursorWrapper` class per entity
- one `ContentValues` class per entity
- one `Selection` class per entity

How to use
----------

### The `_config.json` file

This is where you declare a few parameters that will be used to generate the code.

These are self-explanatory so here is an example:
```json
{
	"projectPackage": "com.example.myapp",
	"providerPackage": "com.example.myapp.provider",
	"providerClassName": "ExampleProvider",
	"sqliteHelperClassName": "ExampleSQLiteOpenHelper",
	"authority": "com.example.myapp.provider",
	"databaseName": "example.db",
}
```

### Entity files

Create one file per entity, naming it `<entity name>.json`.
Inside each file, declare your fields (a.k.a "columns") with a name and a type. Currently the type has to be `text`, `integer`, `double`, `date` or `blob`.
You can also declare table contraints.
Here is a `person.json` file as an example:

```json
{
	"fields": [
		{
			"name": "first_name",
			"type": "text"
		},
		{
			"name": "last_name",
			"type": "text"
		},
		{
			"name": "age",
			"type": "integer"
		}
	],
	
	"constraints": [
		{
			"name": "unique_name",
			"definition": "unique ( first_name, last_name ) on conflict replace"
		}
	]
}
```

There is a working example in the `etc` folder.

### The `header.txt` file (optional)

If a `header.txt` file is present, its contents will be inserted at the top of every generated java file.

### Run the app

`java -jar android-contentprovider-generator-1.2-bundle.jar -i <input folder> -o <output folder>`
- Input folder: where to find _config.json and your entity json files
- Output folder: where the resulting files will be generated

### Use the generated files

- When querying a table, use the corresponding `Selection` class as shown in this example:

```java
PersonSelection where = new PersonSelection();
where.firstName("John").or().age(42);
Cursor c = context.getContentResolver().query(PersonColumns.CONTENT_URI, projection, where.sel(), where.args(), null);
```
- When using the results of a query, wrap the resulting `Cursor` in the corresponding `CursorWrapper`.  You can then use
the generated getters directly as shown in this example:

```java
PersonCursorWrapper person = new PersonCursorWrapper(c);
String lastName = person.getLastName();
Long age = person.getAge();
```
- When updating or inserting into a table, use the corresponding `ContentValues` class as shown in this example:

```java
PersonContentValues values = new PersonContentValues();
values.putFirstName("John");
values.putAge(42l);
context.getContentResolver().update(personUri, values.getContentValues(), null, null);
```

Building
--------

You need maven to build this app.

`mvn package`

This will produce `android-contentprovider-generator-1.2-bundle.jar` in the `target` folder.


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

*Just to be absolutely clear, this license applies to this program itself,
not to the source it will generate!*
