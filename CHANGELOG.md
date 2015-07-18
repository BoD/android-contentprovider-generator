Android ContentProvider Generator Changelog
===========================================

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
