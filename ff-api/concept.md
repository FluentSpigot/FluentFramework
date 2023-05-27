

FluentApiExtension
 

 * Load Config
 * Scan and load classes from plugin Jar
 * Create Dependecy Injection Container builder

 - `OnBeforeConfiguration`
- - Find and run `ConfigMigration` classes that are under for each extension package
 - `OnConfiguration`


 * Build Dependecy Injection Container

  - `OnBeforeEnable`
  - -  Cast config to classes with `@YamlSection` annotation 
  - `OnEnable`


 
  - OnDisable 
    - - Is triggered manually or by `PluginDisableEvent`