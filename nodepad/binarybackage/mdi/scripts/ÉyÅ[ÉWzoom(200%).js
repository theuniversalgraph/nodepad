//ページzoom
var obj = new ActiveXObject("MDIBrowser.API"); 
var nActiveTab = obj.ActiveTabIndex(); 
if(nActiveTab != -1) { 
var doc = obj.GetDocumentObject(nActiveTab); 
doc.body.style.zoom=2.0;//ここの数字を変更すると倍率が変えられます。 
} 

//引用元：スクリプト転載スレ