//スライドショー(秒数指定）
//複数のタブを順に表示させます。

var mdi = new ActiveXObject("MDIBrowser.API"); 
var window = mdi.GetWindowObject(mdi.ActiveTabIndex()); 
var activeId = mdi.GetTabID(mdi.ActiveTabIndex); 

if (mdi.ActiveTabIndex() != -1){
var secon = window.prompt("秒数を指定してください。","1000")
for (var i = 0; i < mdi.GetTabCount(); i++) { 
mdi.ActiveTabIndex = i; 
WScript.Sleep(secon); 
} 
mdi.ActiveTabIndex = mdi.GetTabIndexByID(activeId); 
}
else{
mdi.ShowMessage("アクティブなタブがありません。");
}

mdi = null; 
