package com.example.myminiapp.data

//enum class ApiEndPoints(val url: String) {
//    BASE_URL("https://api.thecatapi.com/v1"),
//    RANDOM_FACT("${BASE_URL.url}/facts/random"),
//    RANDOM_IMAGE("${BASE_URL.url}/images/search")
//}

enum class ApiEndPoints(val url: String) {
    BASE_URL("https://api.artic.edu/api/v1"),
    ARTWORK("${BASE_URL.url}/artworks"),
    FIELDS("${ARTWORK.url}?fields=id,title,image_id")
}