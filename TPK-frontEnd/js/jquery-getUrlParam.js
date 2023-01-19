/**
 * <p>获取链url连接上的参数，该方法给予Jquery实现，使用时需导入Jquery/p> 
 *	@param {name} 
 *  @return {string} 
 */

(function ($) {
	$.getUrlParam = function (name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) 
			return unescape(r[2]);
		return null;
	}
})(jQuery);