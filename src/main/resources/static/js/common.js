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


// When the user scrolls the page, execute myFunction
window.onscroll = function() {
	stickyHeader()
};

// Get the header
var header = document.getElementById("v-sticky-header");

// Get the offset position of the navbar
var sticky = header.offsetTop;

function stickyHeader() {
	if (window.pageYOffset > sticky) {
		header.classList.add("js-sticky");
	} else {
		header.classList.remove("js-sticky");
	}
}

$(function() {
	$(".v-collapse .v-collapse_header a").click(function() {
		$(this).parent().parent().find(".v-collapse_body").toggle();
	});

	$(".btn-menu").click(function() {
		$("#v-nav_menu").toggleClass("show");
		if ($("#v-nav_menu").hasClass("show")) {
			$(this).find("i").html("close");
		} else {
			$(this).find("i").html("menu");
		}
	})
})