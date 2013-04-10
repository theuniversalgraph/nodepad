//----------------------------------------------
//
//	ページ中の同URL上のリンクを強調表示
//
//		CopyRight(C) 2002 SATO
//
//----------------------------------------------

//	MDIBrowserのObjectを取得
var mdObj;
mdObj = new ActiveXObject("MDIBrowser.API");

//	アクティブなwindowの取得
var index = mdObj.ActiveTabIndex();

if (index != -1) 
{
	var window;
	window = mdObj.GetWindowObject(index);
	var obc = window.document.all.tags("A");		//	アンカーをすべて読み出す
	
	//	ベースのURLを作成
	var re2 = new RegExp("(^.*/)[^/]*$","im");
	re2.exec( window.document.location );
	var baseURL = RegExp.$1;

	
	if (obc.length>0)
	{
		var bFind = false;		//	発見フラグ
		var strLnk = "";
		var cnt = 0;
		var re = new RegExp( "^"+baseURL+".*","im");
		var rej = new RegExp( ".+http.+","i");

		//	リンク先をチェック
		for (i=0;i<obc.length;i++)	{
			var obj = obc[i];
			
			if (re.test( obj.href ))
			{
				//	強調表示
				var newText = "<TABLE style=\"border-width:3pt 3pt 3pt 3pt;border-style:solid;border-color=red\"><TR><TD>"+obj.outerHTML+"</TD></TR></TABLE>"
				obj.outerHTML = newText;

				bFind = true;

				strLnk += "<TR><TD><A HREF="+obj.href+">"+obj.href+"</A></TD></TR>";
				cnt++;
			}
			else if(obj.onclick != null & !rej.test( obj.onclick))
			{
				//	強調表示
				var newText = "<TABLE style=\"border-width:3pt 3pt 3pt 3pt;border-style:solid;border-color=blue\"><TR><TD>"+obj.outerHTML+"</TD></TR></TABLE>"
				obj.outerHTML = newText;

				bFind = true;
				
				strLnk += "<TR><TD><A HREF="+obj.onclick+">"+obj.onclick+"</A>…JavaScript</TD></TR>";
				cnt++;
			}
		}
		
		
		//	BODY直下のタグを取得
		var obnavi;
		var len = window.document.all.length;
		for (i=0;i<len;i++)
		{
			obnavi = window.document.all( i );
			if (obnavi.tagName == "BODY" ) {
				obnavi = window.document.all( i+1 );
				break;
			}
		}
			
		if (bFind)
		{
			//	検索結果表示
			obnavi.outerHTML = "<TABLE STYLE=\"border-width:1pt 3pt 1pt 3pt;border-style:solid;border-color:#000000;color:#A0B0C0;background-color:#404040\;font-size:10.5pt\">"
									+"<TR><TD><B>検索結果( "+cnt+" )</B></TD></TR>"
									+strLnk
									+"</TABLE>"
									+ obnavi.outerHTML;
			
		}
		else
		{
			obnavi.outerHTML = "<TABLE STYLE=\"border-width:1pt 3pt 1pt 3pt;border-style:solid:border-color:black\">"
									+"<TR><TD><B>検索結果( 0 )</B></TD></TR>"
									+"<TR><TD>該当無し</TD></TR>"
									+"</TABLE>"
									+ obnavi.outerHTML;
		}
	}
	else
	{
		window.alert( "ページの取得に失敗しました。");
	}
}

mdObj = null;

//引用元：旧アプロダ