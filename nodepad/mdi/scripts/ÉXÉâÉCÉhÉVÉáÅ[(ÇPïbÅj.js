//�X���C�h�V���[(�P�b)
var mdi = new ActiveXObject("MDIBrowser.API"); 
var activeId = mdi.GetTabID(mdi.ActiveTabIndex); 

for (var i = 0; i < mdi.GetTabCount(); i++) { 
mdi.ActiveTabIndex = i; 
WScript.Sleep(1000); 
} 
mdi.ActiveTabIndex = mdi.GetTabIndexByID(activeId); 

mdi = null; 

//���p���F�X�N���v�g�]�ڃX��