package com.eveexite.demoarch.coffeemaker_ft.data.firebase.exception

class FirebaseDataCastException : Exception {

    constructor() {}

    constructor(detailMessage: String) : super(detailMessage) {}

    constructor(
        detailMessage: String,
        throwable: Throwable
    ) : super(detailMessage, throwable) {
    }

    constructor(
        throwable: Throwable
    ) : super(throwable) {
    }
}