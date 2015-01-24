Android ContentProvider Generator Changelog
===========================================

v1.9.0 (2015-??-??)
------
- The config `syntaxVersion` for this release is **3**.  This means you **must** update your `_config.json` file.
- Generation of new "Model" interfaces (one per entity)
- New `useAnnotations` boolean parameter in config, to generate annotations from the `support-annotations` library (issue #38)
- A few optimizations in the generated code
- Column names are no longer automatically made lower case, to help using the tool with an existing db (issue #52)
- New contains, startsWith, endsWitdh methods on Selection objects (issue #55)
- The CREATE_TABLE and CREATE_INDEX constants are now public to make upgrades easier (issue #59)
- The "id" (single column primary key) can now be specified to be an arbitrary column, instead of automatically being generated as "_id" (issue #56)