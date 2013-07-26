if (typeof TeBES !== 'object') {
	TeBES = {};
}


TeBES.Global = (function ($) {

$(window).load(function(){});


$(document).ready(function() {
	
	$('#login a').click(function (e) {
		  e.preventDefault();
		  $(this).tab('show');
		});
	
	$('#create a').click(function (e) {
		  e.preventDefault();
		  $(this).tab('show');
		});

}); // END OF DOCUMENT READY


/* PUBLIC METHODS */
return {

}; // end of return


})(jQuery);