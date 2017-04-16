Android ContentProvider Generator Changelog
===========================================

v1.13.1 (2017-04-16)
------
- `XyzSelection.addRaw()` now returns `this` to allow chaining.

v1.13.0 (2017-02-20)
------
- `XyzSelection.getCursorLoader()` now returns loaders of wrapped cursors.
- New `XyzContentValues.notify(boolean)` method to enable/disable notifications.
- New `debugLogsFieldName` attribute to enable/disable debug logging in the generated code.

v1.12.0 (2017-02-06)
------
This is a somewhat big update in the way the tool is used, and there are also a few syntax differences.

- New **Gradle plugin**, and this is now the preferred way to generate the code.
- The config `syntaxVersion` for this release is **4**.  This means you **must** update your `_config.json` file.
- Syntax updates:
    - `projectPackageId` is renamed to `packageName` to avoid confusion and match the term used here: https://developer.android.com/studio/build/application-id.html.
    - `sqliteOpenHelperCallbacksClassName` is now optional. If omitted, `BaseSQLiteOpenHelperCallbacks` is used in the generated code. If present, it must reference an existing class in your project (it will not be generated), that extends `BaseSQLiteOpenHelperCallbacks`.
    - `sqliteOpenHelperClassName`, `enableForeignKeys`, `useAnnotations`, `useSupportLibrary` and `generateBeans` are now optional and will assume default values if omitted.
- The CLI tool still exists but its name has changed (now `acpg-cli-<version>.jar`).
- Other internal changes that as a user, you needn't care about:
    - Use of Gradle instead of Maven.
    - Module separation (lib, cli, gradle-plugin).
    - Use of Jackson to parse the json files.
    - Use of Log4J to output logs.

v1.11.0 (2016-11-12)
------
- Beans generation (if new `generateBeans` boolean parameter in config is true) - fix for issue #43.

v1.10.0 (2016-10-30)
------
- Fix for issue #91.
- Fix for issue #102.
- Fix for issue #762.
- Fix for issue #57.
- Fixed or suppressed lots of warnings from the generated code.
- New `useSupportLibrary` boolean parameter in config, to choose which implementation of `CursorLoader` to use.
- Minor log cleanup.

v1.9.3 (2015-07-18)
------
- Updated content provider template to support ${applicationId} variable that is supported in the
new manifest merger build tool (thanks almilli!).
- Fixed invalid json in sample and readme (thanks mdupierreux!).
- Added methods that take a Context in addition to the ones that take a ContentResolver.
- New `orderByXXX` methods in Selection classes (thanks yargray!).

v1.9.2 (2015-03-05)
------
- Fix for issue #77.

v1.9.1 (2015-02-21)
------
- Fix for issue #73.

v1.9.0 (2015-02-15)
------
- The config `syntaxVersion` for this release is **3**.  This means you **must** update your `_config.json` file.
- Generation of new "Model" interfaces (one per entity)
- New `useAnnotations` boolean parameter in config, to generate annotations from the `support-annotations` library (issue #38)
- A few optimizations in the generated code
- Column names are no longer automatically made lower case, to help using the tool with an existing db (issue #52)
- New `contains`, `startsWith`, `endsWitdh` methods on Selection objects (issue #55)
- The `CREATE_TABLE and CREATE_INDEX` constants are now public to make upgrades easier (issue #59)
- The "id" (single column primary key) can now be specified to be an arbitrary column, instead of automatically being generated as "_id" (issue #56)
- Ability to specify a LIMIT and HAVING clause in queries, via a query parameter (issues #62 and #70)
- Better handling of default values (issue #67)
- Ability to call `notify`, `groupBy`, `limit` and `having` on Selection objects.
