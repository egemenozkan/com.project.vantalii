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
    of: function (year, month, day) {
        return new Date(year, month-1,day);
    }
}

Date.prototype.addDays = function(days) {
	  var _date = new Date(this.valueOf());
	  _date.setDate(_date.getDate() + days);
	  return _date;
};


/*
 "createDateTime":[2019,3,24,20,22,42]
*/
 Array.prototype.toLocalDate = function () {
     var _array = this.valueOf();
     return LocalDate.of(_array[0], _array[1], _array[2]);
 }

 /** 2019-07-29 **/
 String.prototype.format = function (pattern) {
     if (pattern == "yyyy-MM-dd") {
         var _array = this.valueOf().split('-');
         return LocalDate.of(_array[0], _array[1], _array[2]);
     }
 }
 


