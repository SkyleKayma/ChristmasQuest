package fr.skyle.christmasquest.model

data class Player(
    var id: String? = null,
    var name: String = "",
    var password: String = "",
    var achievements: HashMap<String, Double> = hashMapOf()
)