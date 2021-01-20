package com.pjatk.pawelkuklinski.miniprojekt1


data class Place(var id:String?, var description: String, var name:String, var radius: Long, var isFav: Boolean, var latitude: Double, var longitude: Double){
    override fun toString(): String {
        return "Place($id, $description, $name, $radius, $isFav, $latitude, $longitude)"
    }

    fun toMap(): MutableMap<String, Any> {
        val place = hashMapOf<String, Any>()
        place["description"] = description
        place["name"] = name
        place["radius"] = radius
        place["isFav"] = isFav
        place["longitude"] = longitude
        place["latitude"] = latitude
        return place
    }
}