// �����N�^�O<a>����͂��ă^�u���b�N���ꂽ�V�K�^�u�ɗ�L����
// �����N�������Ƃ��Ȃ莞�Ԃ�������܂��B
var mdi = new ActiveXObject("MDIBrowser.API"); 
var aIndex = mdi.ActiveTabIndex(); 
var document = mdi.GetDocumentObject(aIndex); 
var t = "</td><td>"; 
var x = "<html><body><table border=1><tr bgcolor=#cccccc><td>�����N" + t + "href" + t + "hash" + t + "target" + t + "name</td></tr>\n"; 
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

//���p���F�X�N���v�g�]�ڃX��