if (typeof TeBES !== 'object') {
	TeBES = {};
}


TeBES.Global = (function ($) {

$(window).load(function(){});


$(document).ready(function() {
	
	if($.cookie('login_tab_cookie') == 'login') {
		$('#loginTab a[href="#login"]').tab('show');
	} else {
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
	
	$('#admin_navbar').click(function (e) {
		$(this).toggleClass('active');
		$('#info_navbar').removeClass('active');
		$('#home_navbar').removeClass('active');
	});
	
	$('#home_navbar').click(function (e) {
		$(this).toggleClass('active');
		$('#info_navbar').removeClass('active');
		$('#admin_navbar').removeClass('active');
	});

}); // END OF DOCUMENT READY


/* PUBLIC METHODS */
return {

}; // end of return


})(jQuery);