//�y�[�Wzoom
var obj = new ActiveXObject("MDIBrowser.API"); 
var nActiveTab = obj.ActiveTabIndex(); 
if(nActiveTab != -1) { 
var doc = obj.GetDocumentObject(nActiveTab); 
doc.body.style.zoom=2.0;//�����̐�����ύX����Ɣ{�����ς����܂��B 
} 

//���p���F�X�N���v�g�]�ڃX��