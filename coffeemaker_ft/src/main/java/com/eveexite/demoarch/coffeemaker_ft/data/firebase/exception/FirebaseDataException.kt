package com.eveexite.demoarch.coffeemaker_ft.data.firebase.exception

import com.google.firebase.database.DatabaseError

class FirebaseDataException(error: DatabaseError) : Exception() {

    var error: DatabaseError
        protected set

    init {
        this.error = error
    }

    override fun toString(): String {
        return "FirebaseDataException{" +
                "error=" + error +
                '}'
    }
}