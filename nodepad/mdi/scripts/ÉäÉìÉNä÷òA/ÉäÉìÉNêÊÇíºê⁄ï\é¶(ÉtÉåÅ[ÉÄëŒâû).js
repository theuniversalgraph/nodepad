//�����N��𒼐ڕ\���i�t���[���Ή��j
//�A�N�e�B�u�t���[���̃����N�̉��Ƀ����N���URL��ǉ����܂�
//�����N�̑����y�[�W�ł͏��������܂łɎ��Ԃ�������̂Œ��ӁB 

var obj = new ActiveXObject("MDIBrowser.API"); 
var docObj = obj.GetDocumentObject(obj.ActiveTabIndex); 

var doc = docObj;
if (docObj.activeElement.tagName == "FRAME") {
try {
doc = docObj.activeElement.contentWindow.document;
}
catch(e) {
docObj = null;
obj.ShowMessage("�A�N�e�B�u�t���[���擾�G���[\n" + e.description);
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