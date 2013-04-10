//直接リンク画像を表示（倍率指定）
var mdi = new ActiveXObject("MDIBrowser.API"); 
var TabNo = mdi.ActiveTabIndex(); 
var document = mdi.GetDocumentObject(TabNo); 
var window = mdi.GetWindowObject(TabNo); 
var bairi = window.prompt("倍率を指定してください。","1.0")

for(i=document.links.length ; i>=0 ; i--) 
{ 
var zoomID = parseFloat(bairi)   
var j = document.links[i] + ""; 
if(j.match(/http.*(\.gif|\.jpg|\.jpeg|\.gif|\.png|\.bmp)$/i)) 
document.links[i].outerHTML = '<a href="' + j +'target="_brank"><img src="' + j + '" style="zoom:zoomID;" alt=\"1\" onerror=\"this.onabort()\" onabort=\"this.alt=eval(this.alt)+1;this.outerHTML=this.outerHTML\"></a>'; 
} 
//引用元：スクリプト転載スレ