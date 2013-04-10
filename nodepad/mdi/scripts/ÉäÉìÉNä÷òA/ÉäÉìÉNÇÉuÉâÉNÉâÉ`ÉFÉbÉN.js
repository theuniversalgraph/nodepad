//右クリックブラクラチェッカー
//
//○使い方
//・リンクの上で右クリックをすると、リンク先をブラクラチェッカーにかけて、インラインフレームで表示します。
//・リンクではない文字列（"ttp://～"など）でもURLを反転選択して右クリックすれば同じように動作します。
//・シフトロックされている場合は解除されます。（シフトロックされていると新しいタブが開いてしまうから）
//・閉じるボタンを押すまでウインドウは消えません。
//
//○ブラクラチェッカーのURLの変更
//LinkPouup.jsの10行目あたりを変更。
//デフォルトでは"http://www.jah.ne.jp/~fild/cgi-bin/LBCC/lbcc.cgi?url="になっています。
//↓にするとリンク先のソースを見ることができます。
//var bcChecker = "view-source:";
//
//○ポップアップウインドウの大きさの変更
//LinkPopup.jsを開いて、6行目あたりを変更することで変えられます。
//"window.document.body.clientWidth" は、ウィンドウの内側（表示領域）の横幅です。
//例
//var wsize = 200;
//var hsize = 150;
//
//○いろいろな問題点
//・フレームのあるページで動作しない。
//・<a href=hoge.htm><b>hoge</b></a>のように直接aタグで囲まれていないものは動作しない。
//・<a href=hoge.htm><img src=hoge.png></a>のような画像のリンクでも動作しない。
//・ラベルのついたリンク（hoge.htm#abcみたいなの）を右クリックすると、元のページがスクロールする。
//・履歴がややこしいことになる。
//・その他いろいろ。とりあえず、複雑そうなページでは動作しないことが多いです。


var Obj = new ActiveXObject("MDIBrowser.API");
var index = Obj.ActiveTabIndex();
var window = Obj.GetWindowObject(index);

//ポップアップウインドウの大きさ
var wsize = window.document.body.clientWidth - 50; //幅
var hsize = 250; //高さ

//ブラクラチェッカーのURL("view-source:"にするとソースを表示させることができます。)
var bcChecker = "http://www.jah.ne.jp/~fild/cgi-bin/LBCC/lbcc.cgi?url=";

Obj.ShiftLock(index, false);
if(!window.document.all.tags("frameset").length){
	hoge =  '<SCRIPT for=document event=onmousedown>';
	hoge += 'var hsize=' + hsize + ';';
	hoge += 'var bcChecker="' + bcChecker + '";';
	hoge += 'var rng=document.selection.createRange();';
	hoge += 'var selectedText=rng.text;';
	hoge += 'var url;';
	hoge += 'function rightClick(){return false;}';
	hoge += 'function clear(){}';
	hoge += 'function popup(){';
	hoge += '	document.all.popupWindow.style.display="block";';
	hoge += '	document.oncontextmenu=rightClick;';
	hoge += '	document.ifr.location.href=url;';
	hoge += '	document.all.popupWindow.style.left=20;';
	hoge += '	if(event.clientY > document.body.clientHeight-hsize-20){';
	hoge += '		document.all.popupWindow.style.top=document.body.scrollTop+event.clientY-hsize-30;';
	hoge += '	}else{document.all.popupWindow.style.top=document.body.scrollTop+event.clientY+10;}';
	hoge += '}';
	hoge += 'if(event.srcElement.href){';
	hoge += '	url=bcChecker+event.srcElement.href;';
	hoge += '	popup();';
	hoge += '}else{';
	hoge += '	if(selectedText){';
	hoge += '		url=bcChecker+selectedText;';
	hoge += '		document.execCommand("Unselect");';
	hoge += '		popup();';
	hoge += '	}else{';
	hoge += '		document.oncontextmenu=eval;';
	hoge += '	}';
	hoge += '}';
	hoge += '</SCRIPT>';
	hoge += '<div id=popupWindow style="display:none; top:0px;left:0px; position:absolute;" align=right><input type=button onclick="document.all.popupWindow.style.display=\'none\';" value="このウインドウを閉じる" style="width:' + wsize + ';background-color:#99ccff;"><iframe name=ifr src=about:blank style="width:' + wsize + 'px; height:' + hsize + ';"></iframe></div>';
	window.document.body.innerHTML += hoge;
}

//引用元：旧アプロダ