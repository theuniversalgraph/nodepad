//MDIBrowserの公開するAPIオブジェクトを作成
var Obj;
Obj = new ActiveXObject("MDIBrowser.API");

//アクティブなタブのインデックスを取得
var index = Obj.GetActiveTabIndex();

//アクティブなタブのwindowオブジェクト及びWebBrowserオブジェクトを取得
var window;
var wb;

if (index == -1) {
	//アクティブなタブがなければ、新しくウィンドウを開く
	Obj.NavigateNew("about:blank");
	do {
		WScript.Sleep(500); //ウェイト
		window = Obj.GetWindowObject(0);
	} while (window == null);
	wb = Obj.GetWebBrowserObject(0);
}
else {
	window = Obj.GetWindowObject(index);
	wb = Obj.GetWebBrowserObject(index);
}

//現在のURLを出力（警告ダイアログを抑止していると表示されません。）
window.alert(window.document.location);

//こちらは抑止していても表示されます。
Obj.ShowMessage(window.document.location);

//Googleに移動します。
wb.Navigate2("http://www.google.co.jp");

