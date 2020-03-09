package com.example.getfit

class equipment(var name:String,var desc:String,var imgurl:String) {
}

class trainermodel(var name:String,var desc:String,var exp:String,var gen:String,var imgurl:String){

}
data class fitnessM(var name:String,var imgurl:String)

class fitnessitems(var name:String,var price:String,var desc:String,var imgurl:String){

}
class gympackage(var name:String,var price:String,var desc:String){

}
class modelnames{
    companion object{
        final var trainer="TRAINER"
        final var eqip="EQUIPMENTS"
        final var fitm="FITNESS MERCHANDISE"
        final var fitmitem="FITNESS ITEM"
        final var gympack="GYM PACKAGES"
    }
}