//リンク先を直接表示（フレーム対応）
//アクティブフレームのリンクの横にリンク先のURLを追加します
//リンクの多いページでは処理完了までに時間がかかるので注意。 

var obj = new ActiveXObject("MDIBrowser.API"); 
var docObj = obj.GetDocumentObject(obj.ActiveTabIndex); 

var doc = docObj;
if (docObj.activeElement.tagName == "FRAME") {
try {
doc = docObj.activeElement.contentWindow.document;
}
catch(e) {
docObj = null;
obj.ShowMessage("アクティブフレーム取得エラー\n" + e.description);
obj = null;
WScript.Quit();
}
}

var atag = doc.links;
var i;
for (i in atag){
var x = atag[i].href;
if(x==null){continue;}
atag[i].insertAdjacentHTML("AfterEnd","<b><small>("+x+")</small></b>");
} 

docObj = null;
obj = null;