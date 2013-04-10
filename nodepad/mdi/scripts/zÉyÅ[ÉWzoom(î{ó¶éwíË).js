//ページzoom
var obj = new ActiveXObject("MDIBrowser.API"); 
var nActiveTab = obj.ActiveTabIndex(); 
var window = obj.GetWindowObject(nActiveTab); 
var bairi = window.prompt("倍率を指定してください。","1.0")

if(nActiveTab != -1) { 
var zoomID = parseFloat(bairi) 
var doc = obj.GetDocumentObject(nActiveTab); 
doc.body.style.zoom=zoomID; 
} 
obj = null; 
