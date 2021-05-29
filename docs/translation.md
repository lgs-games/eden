# Translations workflow

Translation are made using ``properties`` files. Simply create
a property file in ``src/main/resources/``
called ``i18n_locale`` where `locale` is a value like

* ``en`` for english
* ``fr`` for french

The syntax is ``key=value``. Put your translation as value. The key
will be used in ``.fxml`` files. Instead of writing some text, we
will write ``%key``.

When loading a view, ``Utility.loadView`` method check for current locale, then
load property file and translate everything ! Locale must be registered
in ``Language`` class trough.