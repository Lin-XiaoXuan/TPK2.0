"use strict";+function(a,c){var b={};b.init=function(){var d=a(".masonry-grid").imagesLoaded(function(){d.masonry({itemSelector:".masonry-brick",percentPosition:true})});var e=function(h){var p=function(l){var y=l.childNodes,w=y.length,u=[],r,v,x,t;for(var s=0;s<w;s++){r=y[s];if(r.nodeType!==1){continue}v=r.children[0];x=v.getAttribute("data-size").split("x");t={src:v.getAttribute("href"),w:parseInt(x[0],10),h:parseInt(x[1],10)};if(r.children.length>1){t.title=r.children[1].innerHTML}if(v.children.length>0){t.msrc=v.children[0].getAttribute("src")}t.el=r;u.push(t)}return u};var f=function f(i,l){return i&&(l(i)?i:f(i.parentNode,l))};var n=function(t){t=t||c.event;t.preventDefault?t.preventDefault():t.returnValue=false;var u=t.target||t.srcElement;var s=f(u,function(i){return(i.tagName&&i.tagName.toUpperCase()==="FIGURE")});if(!s){return}var r=s.parentNode,l=s.parentNode.childNodes,y=l.length,x=0,w;for(var v=0;v<y;v++){if(l[v].nodeType!==1){continue}if(l[v]===s){w=x;break}x++}if(w>=0){o(w,r)}return false};var q=function(){var l=c.location.hash.substring(1),t={};if(l.length<5){return t}var u=l.split("&");for(var r=0;r<u.length;r++){if(!u[r]){continue}var s=u[r].split("=");if(s.length<2){continue}t[s[0]]=s[1]}if(t.gid){t.gid=parseInt(t.gid,10)}return t};var o=function(t,s,i,l){var x=document.querySelectorAll(".pswp")[0],r,w,u;u=p(s);w={galleryUID:s.getAttribute("data-pswp-uid"),getThumbBoundsFn:function(y){var B=u[y].el.getElementsByTagName("img")[0],z=c.pageYOffset||document.documentElement.scrollTop,A=B.getBoundingClientRect();return{x:A.left,y:A.top+z,w:A.width}}};if(l){if(w.galleryPIDs){for(var v=0;v<u.length;v++){if(u[v].pid==t){w.index=v;break}}}else{w.index=parseInt(t,10)-1}}else{w.index=parseInt(t,10)}if(isNaN(w.index)){return}if(i){w.showAnimationDuration=0}r=new PhotoSwipe(x,PhotoSwipeUI_Default,u,w);r.init()};var g=document.querySelectorAll(h);for(var k=0,m=g.length;k<m;k++){g[k].setAttribute("data-pswp-uid",k+1);g[k].onclick=n}var j=q();if(j.pid&&j.gid){o(j.pid,g[j.gid-1],true,true)}};e(".masonry-grid")};c.gallery=b}(jQuery,window);+function(a){gallery.init()}(jQuery);