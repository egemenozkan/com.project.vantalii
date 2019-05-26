console.log("test");
import 'bootstrap';
window.jQuery = $;
window.$ = $;
function sliceDate(param) {
    return ('0' + param).slice(-2);
 }  
   
 Array.prototype.dateAsString = function() {
     var dateArray = this.valueOf();
     return sliceDate(dateArray[2]) + '.' + sliceDate(dateArray[1]) + '.' + dateArray[0]
     + "  " + sliceDate(dateArray[3]) + ':' + sliceDate(dateArray[4]) + ':' + sliceDate(dateArray[5]);
 }   
import {LocalDate} from '../js/mydate';

 $(function () {
     'use strict'

     $('[data-toggle="offcanvas"]').on('click', function () {
       $('.offcanvas-collapse').toggleClass('open')
     })
   })
   
  