// リンクタグ<a>を解析してタブロックされた新規タブに列記する
// リンクが多いとかなり時間がかかります。
var mdi = new ActiveXObject("MDIBrowser.API"); 
var aIndex = mdi.ActiveTabIndex(); 
var document = mdi.GetDocumentObject(aIndex); 
var t = "</td><td>"; 
var x = "<html><body><table border=1><tr bgcolor=#cccccc><td>リンク" + t + "href" + t + "hash" + t + "target" + t + "name</td></tr>\n"; 
var atag = document.all.tags("A"); 
for(i in atag) 
{ 
if(atag[i].href == undefined) 
{ 
continue; 
} 
var y = atag[i].href; 
if(y != null) 
{ 
y = y.link(atag[i].href); 
} 
x = x + "<td>" + atag[i].innerText + "<br>" + t + y + "<br>" + t+atag[i].hash + "<br>" + t + atag[i].target + "<br>" + t + atag[i].name + "<br></td></tr>\n" 
}; 
x = x + "</table></html>"; 

var newID = mdi.NavigateNew("about:blank"); 
var nIndex; 
while((nIndex=mdi.GetTabIndexByID(newID)) == -1) 
{ 
Sleep(50); 
} 
var window = mdi.GetWindowObject(nIndex); 
window.document.write(x); 
mdi.ShiftLock(nIndex,true); 

//引用元：スクリプト転載スレ