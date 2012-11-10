Android ContentProvider Generator
=================================

A small tool to generate an Android ContentProvider.
It takes a set of entity (a.k.a "table") definitions as the input, and generates:
- a `ContentProvider` class
- a `SQLiteOpenHelper` class
- one `BaseColumns` class per entity 

How to use
----------

### The `_config.json` file

This is where you declare a few parameters that will be used to generate the code.

These are self-explanatory so here is an example:

	{
		"package": "com.example.myapp.provider",
		"providerClassName": "ExampleProvider",
		"sqliteHelperClassName": "ExampleSQLiteOpenHelper",
		"authority": "com.example.myapp.provider",
		"databaseName": "example.db",
	}

### Entity files

Create one file per entity, naming it `<entity name>.json`.
Inside each file, declare your columns with a name and a type. Currently the type has to be `text` or `integer`.
Here is a `person.json` file as an example:

	[
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
	]

There is a working example in the `etc` folder.

### Run the app

`java -jar android_contentprovider_generator-1.00-bundle.jar -i <input folder> -o <output folder>`
- Input folder: where to find _config.json and your entity json files
- Output folder: where the resulting files will be generated

Building
--------

You need maven to build this app.

`mvn assembly:single`

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
