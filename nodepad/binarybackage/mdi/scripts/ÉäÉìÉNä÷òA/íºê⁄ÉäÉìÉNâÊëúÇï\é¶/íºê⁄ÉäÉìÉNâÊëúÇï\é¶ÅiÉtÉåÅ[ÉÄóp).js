//直接リンクされている画像を表示（フレーム用）

var mdi = new ActiveXObject("MDIBrowser.API");
var docObj = mdi.GetDocumentObject(mdi.ActiveTabIndex());

var doc = docObj;
if (docObj.activeElement.tagName == "FRAME") {
try {
doc = docObj.activeElement.contentWindow.document;
}
catch(e) {
docObj = null;
mdi.ShowMessage("アクティブフレーム取得エラー\n" + e.description);
mdi = null;
WScript.Quit();
}
}

var i;
for (i = 0; i < doc.links.length; i++) {
var anchor = doc.links.item(i);
if (anchor.href.match(/http.*(\.gif|\.jpg|\.png|\.bmp)$/i)) {
var img = doc.createElement("IMG");
img.src = anchor.href;
img.alt = "";
anchor.appendChild(img);
}
}

doc = null;
mdi = null;

//引用元：スクリプト転載スレ