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
   
  
   
window.onscroll = function() {stickyNavbar()};

// Get the header
var navbar = document.getElementById("navbar");
var sticky = navbar.offsetTop;

// Add the sticky class to the header when you reach its scroll position. Remove "sticky" when you leave the scroll position
function stickyNavbar() {
  if (window.pageYOffset > sticky) {
	  navbar.classList.add("navbar-sticky");
  } else {
	  navbar.classList.remove("navbar-sticky");
  }
}