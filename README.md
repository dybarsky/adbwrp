Adb Wrapper
-----------------

Kotlin native based wrapper around adb.
Provides high-level operations like start, uninstall, login.


### Config
Create `~/.adbwrp` folder. This is default app home directory. Alternative path can be set with `--home` cli argument.
In app home directory create two files: `config` and `credentials`.
The `config` file should have next content:
```
android_home=/opt/google/android-sdk
app_package=de.gymondo.app.gymondo.test
launcher_activity=com.gymondo.presentation.features.startup.StartupActivity
```
The `credential` file should have pair of username and password separated by space, one pair per line:
```
maksym+1@gymondo.de 12345678
maksym+2@gymondo.de qwery123
```


### Usage

```
adbwrp [options] command

Options:
    --home      Set home folder
    --version   Prints version
    --help      Prints help

Commands:
    config  Show detailed information about configuration
    start   Launch application
    purge   Uninstall application
    reset   Clean application's data
    creds   Select active credentials from list
    login   Emulate user actions to login with active credentials
```
