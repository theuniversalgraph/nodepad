//リンク先を直接表示
//リンクの横にリンク先のURLを追加します
//リンクの多いページでは処理完了までに時間がかかるので注意。 

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