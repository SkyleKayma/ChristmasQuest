package fr.skyle.christmasquest.event

import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject

// Data
var eventPlayersLoaded: BehaviorSubject<Boolean> =
    BehaviorSubject.createDefault(false)
var eventAchievementsLoaded: BehaviorSubject<Boolean> =
    BehaviorSubject.createDefault(false)

// Main
var eventPlayerAchievementsChanged: PublishSubject<Unit> =
    PublishSubject.create()

// Home
var eventHomeLoaded: PublishSubject<Unit> =
    PublishSubject.create()