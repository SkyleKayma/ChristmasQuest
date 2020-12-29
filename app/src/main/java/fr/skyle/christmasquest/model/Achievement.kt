package fr.skyle.christmasquest.model

data class Achievement(
    var id: String = "",
    var players: HashMap<String, Boolean> = hashMapOf()
)