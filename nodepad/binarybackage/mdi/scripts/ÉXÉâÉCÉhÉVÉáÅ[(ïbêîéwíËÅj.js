//�X���C�h�V���[(�b���w��j
//�����̃^�u�����ɕ\�������܂��B

var mdi = new ActiveXObject("MDIBrowser.API"); 
var window = mdi.GetWindowObject(mdi.ActiveTabIndex()); 
var activeId = mdi.GetTabID(mdi.ActiveTabIndex); 

if (mdi.ActiveTabIndex() != -1){
var secon = window.prompt("�b�����w�肵�Ă��������B","1000")
for (var i = 0; i < mdi.GetTabCount(); i++) { 
mdi.ActiveTabIndex = i; 
WScript.Sleep(secon); 
} 
mdi.ActiveTabIndex = mdi.GetTabIndexByID(activeId); 
}
else{
mdi.ShowMessage("�A�N�e�B�u�ȃ^�u������܂���B");
}

mdi = null; 
