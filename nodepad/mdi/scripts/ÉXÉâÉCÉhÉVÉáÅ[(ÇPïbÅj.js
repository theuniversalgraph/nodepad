//スライドショー(１秒)
var mdi = new ActiveXObject("MDIBrowser.API"); 
var activeId = mdi.GetTabID(mdi.ActiveTabIndex); 

for (var i = 0; i < mdi.GetTabCount(); i++) { 
mdi.ActiveTabIndex = i; 
WScript.Sleep(1000); 
} 
mdi.ActiveTabIndex = mdi.GetTabIndexByID(activeId); 

mdi = null; 

//引用元：スクリプト転載スレ