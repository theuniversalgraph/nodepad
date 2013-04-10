//選択中のテキストをクリップボードにコピーしてGoogle検索 
//MDIBrowserでもできますが登録できる設定に制限があるので
//一旦クリップボードにコピーするのは後でページ内検索したいときに便利だから

var mdi = new ActiveXObject("MDIBrowser.API"); 
if (mdi == null){ 
WScript.Echo("MDIBrowser Object Error."); 
WScript.Quit(); 
} 

var doc = mdi.GetDocumentObject(mdi.ActiveTabIndex()); 

if (doc.activeElement.tagName == "FRAME") { 
try { 
doc = doc.activeElement.contentWindow.document; 
} 
catch(e) { 
mdi.ShowMessage("アクティブなフレームの取得エラーです。\n" + e.description); 
doc = null; 
mdi = null; 
WScript.Quit(); 
} 
} 

if (new String(doc.selection.type).toLowerCase() == "text") { 
doc.selection.createRange().execCommand("Copy"); 
} 

var wind=mdi.GetWindowObject(mdi.ActiveTabIndex);
var Keyword=wind.clipboardData.getData("Text");
if (Keyword){
//下記のPrefixを変更すれば他のサイトに使えます。
var urI= 'http://www.google.co.jp/search?num=100&hl=ja&lr=lang_ja&q=' + Keyword;
mdi.NavigateNew(encodeURI(urI)); 
}
else{
mdi.ShowMessage("検索語がありません。");
}

mdi = null; 