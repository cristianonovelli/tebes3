if (typeof TeBES !== 'object') {
	TeBES = {};
}


TeBES.Global = (function ($) {

$(window).load(function(){});


$(document).ready(function() {
	
	console.log($.cookie('login_tab_cookie'));
	if($.cookie('login_tab_cookie') == 'login') {
		console.log('login tab');
		$('#loginTab a[href="#login"]').tab('show');
	} else {
		console.log('create tab');
		$('#loginTab a[href="#create"]').tab('show');
	}
	
	$('#loginTab a[href="#login"]').click(function (e) {
		  e.preventDefault();
		  $.cookie('login_tab_cookie', 'login');
		  $(this).tab('show');
		});
	
	$('#loginTab a[href="#create"]').click(function (e) {
		  e.preventDefault();
		  $.cookie('login_tab_cookie', 'create');
		  $(this).tab('show');
		});

}); // END OF DOCUMENT READY


/* PUBLIC METHODS */
return {

}; // end of return


})(jQuery);