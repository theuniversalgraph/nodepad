//エキサイトのWEBページ翻訳を利用した日本語ページの英訳。
//変数lpを変更すると英語ページの和訳も出来ます。

//MDIB の公開する API オブジェクトを作成
var Obj;
Obj = new ActiveXObject("MDIBrowser.API");

//アクティブなタブのインデックスを取得
var index = Obj.ActiveTabIndex();

if (index != -1){
  //アクティブなタブがあれば、インデックスを元に Window, WebBrowserオブジェクトを作成
  var window = Obj.GetWindowObject(index);
  var wb = Obj.GetWebBrowserObject(index);
  //オプションの設定
  var lp = 'JAEN'; //ココを「ENJA」にすると和→英に。デフォルトは「英→和」。
  var dis = 2;     //ココを3にすると「訳文と原文」に。デフォルトは「訳文のみ」。
  //翻訳ページを新しいタブに表示
  var url = 'http://www.excite.co.jp/world/url/body/?wb_url=' + window.document.location + '&wb_lp=' + lp + '&wb_dis=' + dis + '&wb_co=excitejapan';
  Obj.NavigateNew(url);
}
else{//アクティブなタブが無いときは警告(警告ダイアログを抑止していても表示されます)
  Obj.ShowMessage("アクティブなタブがありません。");
}

//引用元：スクリプト転載スレ