export var LocalDate = {
    today : function () {
        return new Date(Date.UTC(this.now().getUTCFullYear(), this.now().getUTCMonth(), this.now().getUTCDate()));
    },
    now : function () {
        return new Date();
    },
    of: function (year, month, day) {
        return new Date(year, month-1,day);
    }
}


export var LocalDateTime = {
    today : function () {
        return new Date(Date.UTC(this.now().getUTCFullYear(), this.now().getUTCMonth(), this.now().getUTCDate()));
    },
    now : function () {
        return new Date();
    },
    of: function (year, month, day, hours, minutes) {
        return new Date(year, month-1,day, hours, minutes);
    }
}

Date.prototype.addDays = function(days) {
	  var _date = new Date(this.valueOf());
	  _date.setDate(_date.getDate() + days);
	  return _date;
};
var e3 = ["Paz", "Pzt", "Sal", "Çar", "Prş", "Cum", "Cmt"];

var e4 = ["Pazar", "Pazartesi", "Salı", "Çarşamba", "Perşembe", "Cuma", "Cumartesi"];

/*
 * "createDateTime":[2019,3,24,20,22,42]
 */
 Array.prototype.toLocalDate = function () {
     var _array = this.valueOf();
     return LocalDate.of(_array[0], _array[1], _array[2]);
 }
 
 Array.prototype.toLocalDateTime = function () {
     var _array = this.valueOf();
     return LocalDate.of(_array[0], _array[1], _array[2], _array[3], _array[4]);
 }

 /** parse: 2019-07-29 * */
 String.prototype.format = function (pattern) {
     if (pattern == "yyyy-MM-dd") {
         var _array = this.valueOf().split('-');
         return LocalDate.of(_array[0], _array[1], _array[2]);
     }
 }
 /** return: 2019-07-29 * */
 Date.prototype.format = function (pattern) {
	 var result;
	 var _date = this.valueOf();
	 _date = new Date(_date);
     if (pattern == "yyyy-MM-dd") {
    	 result = _date.getFullYear() + "-"  + ("0" + (_date.getMonth() + 1)).slice(-2) + "-" + ("0" + _date.getDate()).slice(-2);
     } else if (pattern == "dd.MM.yyyy") {
    	 result =  ("0" + _date.getDate()).slice(-2) + "."  + ("0" + (_date.getMonth() + 1)).slice(-2) + "." + _date.getFullYear();
     } else if (pattern == "EEE") {
    	 result =  e3[_date.getDay()];
     } else if (pattern == "EEEE") {
    	 result =  e4[_date.getDay()]
     } else {
    	 console.log("Undefined Pattern");
    	 return undefined;
     }
     return result.toString();
     
 }
 
 
 


