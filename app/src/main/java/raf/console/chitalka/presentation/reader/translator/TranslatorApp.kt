package raf.console.chitalka.presentation.reader.translator

enum class TranslatorApp(val displayName: String, val baseUrl: String) {
    GOOGLE("Google Translate", "https://translate.google.com/?sl=auto&tl=auto&text="),
    YANDEX("Yandex Translate", "https://translate.yandex.com/?text="),
    DEEPL("DeepL", "https://www.deepl.com/translator#auto/en/"),
    PROMT("PROMT", "https://www.online-translator.com/translation/text/")
}