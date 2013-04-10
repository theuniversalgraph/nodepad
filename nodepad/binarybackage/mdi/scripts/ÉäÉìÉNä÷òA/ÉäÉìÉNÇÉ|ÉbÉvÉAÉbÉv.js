//リンクポップアップ（修正版）
//
//○修正点
//・シフトロックされていると、新しいタブが開いてしまうのでシフトロックを解除するようにした。
//・<a href=hoge.htm><img src=hoge.png></a>のような画像のリンクも表示できるようにした。
//・<a href=hoge.htm><b>hoge</b></a>のように、直接aタグで囲まれて無いリンクでも表示できるようにした。
//・ポップアップウインドウからマウスが出ていったとき、ウインドウを閉じるかどうか指定できるようにした。
//　（下のカスタマイズ参照）
//
//○使い方
//・リンクの上で右クリックをすると、リンク先をインラインフレームで表示します。
//・シフトロックされている場合は解除されます。（シフトロックされていると新しいタブが開いてしまうから）
//・表示されたウインドウは、画面上の適当な場所（画像やリンクの無い場所）をクリックすると消えます。
//・ツリー型掲示板なんかで使うと便利らしい。
//・Googleの検索結果などで使うと、LYCOSのプレビュー機能のような感じになります。
//
//○カスタマイズ（"リンクポップアップ.js"をメモ帳などで開いて変更。）
//・ポップアップウインドウの大きさの変更
//　"window.document.body.clientWidth" は、ウィンドウの内側（表示領域）の横幅を表しています。
//　例：
//　var wsize = 200;
//　var hsize = 150;
//・ポップアップウインドウからマウスが出ていったとき、自動でウインドウを閉じるかどうかの指定。
//　var autoClose = 0;だと閉じない。
//　var autoClose = 1;だと閉じる。
//
//○いろいろな問題点
//・フレームのあるページで動作しない。
//・<a href=hoge.htm><b><i>hoge</i></b></a>のようにaタグより内側に二つ以上のタグがあると動作しない。
//・ラベルのついたリンク（hoge.htm#abcみたいなの）を右クリックすると、元のページがスクロールする。
//・履歴がややこしいことになる。
//・その他いろいろ。とりあえず、複雑そうなページでは動作しないことが多いです。


var Obj = new ActiveXObject("MDIBrowser.API");
var index = Obj.ActiveTabIndex();
var window = Obj.GetWindowObject(index);


//ポップアップウインドウの大きさ
var wsize = window.document.body.clientWidth - 50; //幅
var hsize = 220; //高さ

//ポップアップウインドウからマウスが出て行ったとき、ウインドウを閉じる(0=閉じない / 1=閉じる)
var autoClose = 1;


Obj.ShiftLock(index, false);
if(!window.document.all.tags("frameset").length){
	hoge =  '<SCRIPT for=document event=onmousedown>';
	hoge += 'var hsize=' + hsize + ';';
	hoge += 'function rightClick(){return false;}';
	hoge += 'var url;';
	hoge += 'var autoClose=' + autoClose + ';';
	hoge += 'if(event.srcElement.src || !event.srcElement.href){url=event.srcElement.parentElement.href;}';
	hoge += 'else{url=event.srcElement.href;}';
	hoge += 'if(url){';
	hoge += '	document.all.popupWindow.style.display="block";';
	hoge += '	document.oncontextmenu=rightClick;';
	hoge += '	document.ifr.location.href=url;';
	hoge += '	document.all.popupWindow.style.left=20;';
	hoge += '	if(event.clientY > document.body.clientHeight-hsize-20){';
	hoge += '		document.all.popupWindow.style.top=document.body.scrollTop+event.clientY-hsize-20;';
	hoge += '	}else{document.all.popupWindow.style.top=document.body.scrollTop+event.clientY+10;}';
	hoge += '}else{';
	hoge += '	document.oncontextmenu=eval;';
	hoge += '	document.all.popupWindow.style.display="none";';
	hoge += '	document.ifr.location.href="about:blank";';
	hoge += '}';
	hoge += '</SCRIPT>';
	hoge += '<div id=popupWindow style="display:none; top:0px;left:0px; position:absolute;"';
	if(autoClose){hoge += ' onmouseout="document.oncontextmenu=eval;document.all.popupWindow.style.display=\'none\';document.ifr.location.href=\'about:blank\';"';}
	hoge += '><iframe name=ifr src=about:blank style="width:' + wsize + 'px; height:' + hsize + ';"></iframe></div>';
	window.document.body.innerHTML += hoge;
}

//引用元：旧アプロダ