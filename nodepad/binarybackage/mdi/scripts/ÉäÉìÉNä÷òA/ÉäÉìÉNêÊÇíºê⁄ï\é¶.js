//�����N��𒼐ڕ\��
//�����N�̉��Ƀ����N���URL��ǉ����܂�
//�����N�̑����y�[�W�ł͏��������܂łɎ��Ԃ�������̂Œ��ӁB 

var obj = new ActiveXObject("MDIBrowser.API"); 
var doc = obj.GetDocumentObject(obj.ActiveTabIndex); 
var atag = doc.links;
for (i in atag){
var x = atag[i].href;
if(x==null){continue;}
atag[i].insertAdjacentHTML("AfterEnd","<b><small>("+x+")</small></b>");
} 

doc = null;
obj = null;