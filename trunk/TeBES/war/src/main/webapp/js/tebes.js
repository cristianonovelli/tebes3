if (typeof TeBES !== 'object') {
	TeBES = {};
}


TeBES.Global = (function (jQueryNew) {

jQueryNew(window).load(function(){});


jQueryNew(document).ready(function() {
	
	if(jQueryNew.cookie('login_tab_cookie') == 'login') {
		jQueryNew('#loginTab a[href="#login"]').tab('show');
	} else {
		jQueryNew('#loginTab a[href="#create"]').tab('show');
	}
	
	jQueryNew('#loginTab a[href="#login"]').click(function (e) {
		  e.preventDefault();
		  jQueryNew.cookie('login_tab_cookie', 'login');
		  jQueryNew(this).tab('show');
	});
	
	jQueryNew('#loginTab a[href="#create"]').click(function (e) {
		  e.preventDefault();
		  jQueryNew.cookie('login_tab_cookie', 'create');
		  jQueryNew(this).tab('show');
	});
	
	jQueryNew('#admin_navbar').click(function (e) {
		jQueryNew(this).toggleClass('active');
		jQueryNew('#info_navbar').removeClass('active');
		jQueryNew('#home_navbar').removeClass('active');
	});
	
	jQueryNew('#home_navbar').click(function (e) {
		jQueryNew(this).toggleClass('active');
		jQueryNew('#info_navbar').removeClass('active');
		jQueryNew('#admin_navbar').removeClass('active');
	});

}); // END OF DOCUMENT READY


/* PUBLIC METHODS */
return {

}; // end of return


})(jQuery);