"use strict";$(document).on("change",'input[name="header-theme"]',function(){var a=$('input[name="header-theme"]:checked').val();$(".app").removeClass(function(c,b){return(b.match(/(^|\s)header-\S+/g)||[]).join(" ")}).addClass("header-"+a)});$(document).on("change",'input[name="side-nav-color"]',function(){var a=$('input[name="side-nav-color"]:checked').val();$(".app").removeClass("side-nav-dark side-nav-default");$(".app").addClass("side-nav-"+a)});$("#rtl-toogle").on("change",function(a){$(".app").toggleClass("rtl");a.preventDefault()});