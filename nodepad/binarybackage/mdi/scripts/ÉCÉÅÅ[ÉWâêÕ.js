// イメージタグ<img>を解析してタブロックされた新規タブに列記する
// 画像が多いとかなり時間がかかります。
var mdi = new ActiveXObject("MDIBrowser.API"); 
var aIndex = mdi.ActiveTabIndex(); 
var document = mdi.GetDocumentObject(aIndex); 
var t = "</td><td>"; 
var x = "<html><title>イメージ解析</title><body><table border=1><tr bgcolor=#cccccc><td>alt" + t + "image" + t + "src" + t + "width" + t + "height</td></tr>\n"; 
var atag = document.all.tags("IMG"); 
for(i in atag) 
{ 
if(atag[i].src == undefined) 
{ 
continue; 
} 
var y = "<img src=" + atag[i].src + " width=" + atag[i].width + " height=" + atag[i].height + ">"; 
var z = atag[i].src; 
if(z != null) 
{ 
z = z.link(atag[i].src); 
} 
x = x + "<td>" + atag[i].alt + "<br>" + t + y + t + z + "<br>" + t + atag[i].width + "<br>" + t + atag[i].height + "<br></td></tr>\n" 
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