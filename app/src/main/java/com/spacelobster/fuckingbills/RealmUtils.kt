package com.spacelobster.fuckingbills

import com.spacelobster.fuckingbills.enums.CounterType
import com.spacelobster.fuckingbills.models.Counter
import io.realm.Realm


class RealmUtils {
    companion object {
        fun deleteAllStoredStuff(realm: Realm) {
            realm.executeTransaction {
                realm.deleteAll()
            }
        }

        fun createElectricityCounter(realm: Realm) {
            realm.executeTransaction {
                val counter: Counter = Counter()
                counter.name = "Electricity"
                counter.type = CounterType.ELECTRICITY.name
                counter.units = "kV"
                counter.value = 0f
                realm.insert(counter)
            }
        }

        fun createGasCounter(realm: Realm) {
            realm.executeTransaction {
                val counter: Counter = Counter()
                counter.name = "Gas"
                counter.type = CounterType.GAS.name
                counter.units = "cube meters"
                counter.value = 0f
                realm.insert(counter)
            }
        }

        fun createWaterCounter(realm: Realm) {
            realm.executeTransaction {
                val counter: Counter = Counter()
                counter.name = "Water"
                counter.type = CounterType.WATER.name
                counter.units = "cube meters"
                counter.value = 0f
                realm.insert(counter)
            }
        }
    }
}