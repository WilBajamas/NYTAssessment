package com.example.sicpanyt.model

sealed class Section {
    class SectionTitle(val title: String) : Section()
    class SectionItem(val identifier: SectionItemIdentifier, val title: String) : Section()
}

enum class SectionItemIdentifier {
    SearchArticles,
    MostViewed,
    MostShared,
    MostEmailed
}