//���ڃ����N����Ă���摜��\���i�t���[���p�j

var mdi = new ActiveXObject("MDIBrowser.API");
var docObj = mdi.GetDocumentObject(mdi.ActiveTabIndex());

var doc = docObj;
if (docObj.activeElement.tagName == "FRAME") {
try {
doc = docObj.activeElement.contentWindow.document;
}
catch(e) {
docObj = null;
mdi.ShowMessage("�A�N�e�B�u�t���[���擾�G���[\n" + e.description);
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

//���p���F�X�N���v�g�]�ڃX��