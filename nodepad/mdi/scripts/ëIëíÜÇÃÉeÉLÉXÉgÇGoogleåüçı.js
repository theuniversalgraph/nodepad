//�I�𒆂̃e�L�X�g���N���b�v�{�[�h�ɃR�s�[����Google���� 
//MDIBrowser�ł��ł��܂����o�^�ł���ݒ�ɐ���������̂�
//��U�N���b�v�{�[�h�ɃR�s�[����̂͌�Ńy�[�W�������������Ƃ��ɕ֗�������

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
mdi.ShowMessage("�A�N�e�B�u�ȃt���[���̎擾�G���[�ł��B\n" + e.description); 
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
//���L��Prefix��ύX����Α��̃T�C�g�Ɏg���܂��B
var urI= 'http://www.google.co.jp/search?num=100&hl=ja&lr=lang_ja&q=' + Keyword;
mdi.NavigateNew(encodeURI(urI)); 
}
else{
mdi.ShowMessage("�����ꂪ����܂���B");
}

mdi = null; 