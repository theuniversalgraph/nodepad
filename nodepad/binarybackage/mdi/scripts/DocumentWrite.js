/*
  サンプルスクリプト written by TAKETA Kohei 2002.07.09
  
  概要：NavigateNewで作成したタブのインデックスを取得し、
        そのドキュメントに直接内容を書き込みます。
 */

var obj = new ActiveXObject("MDIBrowser.API");

var nActiveTab = obj.GetActiveTabIndex();
if(nActiveTab != -1)
{
   //アクティブなdocumentオブジェクトの<HTML>要素を取得
   var doc = obj.GetDocumentObject(nActiveTab);
   var body = doc.body;
   var html = body.parentElement;

   //新しいタブを開き、そのインデックスを取得する
   var newID = obj.NavigateNew("about:blank");
   var nIndex;
   while((nIndex=obj.GetTabIndexByID(newID)) == -1)
   {
      Sleep(500);
   }

   //元のタブのドキュメント内容を書き込む
   var newDoc = obj.GetDocumentObject(nIndex);
   newDoc.write(html.outerHTML);
}
