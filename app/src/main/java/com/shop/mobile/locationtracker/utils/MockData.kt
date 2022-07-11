package com.shop.mobile.locationtracker.utils

import com.shop.mobile.locationtracker.data.Person
import com.shop.mobile.locationtracker.data.PersonDao

object MockData {

    fun getPersonData() : ArrayList<Person>{
        var personlist = ArrayList<Person>()
        var person1 =Person("Chennai",System.currentTimeMillis().toString(),50);
        var person2 =Person("Bangalore",System.currentTimeMillis().toString(),35);
        personlist.add(person1)
        personlist.add(person2)
        return personlist
    }
}

