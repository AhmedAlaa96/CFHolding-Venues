package com.ahmed.cfholding_venues.utils

import com.ahmed.cfholding_venues.BuildConfig

object Constants {
    object SharedPreference {
        const val SHARED_PREF_NAME = "my_shared_pref"
    }

    object General {
        const val EMPTY_TEXT = ""
        const val DASH_TEXT = "-"
    }


    object Network {
        const val CONNECT_TIMEOUT = 5L
        const val READ_TIMEOUT = 5L
        const val WRITE_TIMEOUT = 5L
    }

    object URL {
        const val BASE_URL = BuildConfig.BASE_NETWORK_URL
        const val GET_VENUES_LIST = "/v2/venues/search"
    }
    object QueryParams {
        const val LONG_LAT = "ll"
        const val CLIENT_ID = "client_id"
        const val CLIENT_SECRET = "client_secret"
        const val V_SEARCH = "v"
    }

    object QueryDefaults{
        const val CLIENT_ID = "4EQRZPSGKBZGFSERGJY055FRW2OSPJRZYR4C3J0JN2CQQFIV"
        const val CLIENT_SECRET = "AJR4B5LLRONWAJWJJOACHAFLCWS2YJAZMGQNFFZQP0IB3THR"
        const val V_SEARCH: Long = 20180910
    }
    object Headers {
        const val ACCEPT = "accept"
        const val ACCEPT_VALUE = "application/json"
    }

}