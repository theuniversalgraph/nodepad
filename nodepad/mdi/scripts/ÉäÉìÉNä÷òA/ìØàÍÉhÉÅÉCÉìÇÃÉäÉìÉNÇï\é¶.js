//----------------------------------------------
//
//	�y�[�W���̓�URL��̃����N�������\��
//
//		CopyRight(C) 2002 SATO
//
//----------------------------------------------

//	MDIBrowser��Object���擾
var mdObj;
mdObj = new ActiveXObject("MDIBrowser.API");

//	�A�N�e�B�u��window�̎擾
var index = mdObj.ActiveTabIndex();

if (index != -1) 
{
	var window;
	window = mdObj.GetWindowObject(index);
	var obc = window.document.all.tags("A");		//	�A���J�[�����ׂēǂݏo��
	
	//	�x�[�X��URL���쐬
	var re2 = new RegExp("(^.*/)[^/]*$","im");
	re2.exec( window.document.location );
	var baseURL = RegExp.$1;

	
	if (obc.length>0)
	{
		var bFind = false;		//	�����t���O
		var strLnk = "";
		var cnt = 0;
		var re = new RegExp( "^"+baseURL+".*","im");
		var rej = new RegExp( ".+http.+","i");

		//	�����N����`�F�b�N
		for (i=0;i<obc.length;i++)	{
			var obj = obc[i];
			
			if (re.test( obj.href ))
			{
				//	�����\��
				var newText = "<TABLE style=\"border-width:3pt 3pt 3pt 3pt;border-style:solid;border-color=red\"><TR><TD>"+obj.outerHTML+"</TD></TR></TABLE>"
				obj.outerHTML = newText;

				bFind = true;

				strLnk += "<TR><TD><A HREF="+obj.href+">"+obj.href+"</A></TD></TR>";
				cnt++;
			}
			else if(obj.onclick != null & !rej.test( obj.onclick))
			{
				//	�����\��
				var newText = "<TABLE style=\"border-width:3pt 3pt 3pt 3pt;border-style:solid;border-color=blue\"><TR><TD>"+obj.outerHTML+"</TD></TR></TABLE>"
				obj.outerHTML = newText;

				bFind = true;
				
				strLnk += "<TR><TD><A HREF="+obj.onclick+">"+obj.onclick+"</A>�cJavaScript</TD></TR>";
				cnt++;
			}
		}
		
		
		//	BODY�����̃^�O���擾
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
			//	�������ʕ\��
			obnavi.outerHTML = "<TABLE STYLE=\"border-width:1pt 3pt 1pt 3pt;border-style:solid;border-color:#000000;color:#A0B0C0;background-color:#404040\;font-size:10.5pt\">"
									+"<TR><TD><B>��������( "+cnt+" )</B></TD></TR>"
									+strLnk
									+"</TABLE>"
									+ obnavi.outerHTML;
			
		}
		else
		{
			obnavi.outerHTML = "<TABLE STYLE=\"border-width:1pt 3pt 1pt 3pt;border-style:solid:border-color:black\">"
									+"<TR><TD><B>��������( 0 )</B></TD></TR>"
									+"<TR><TD>�Y������</TD></TR>"
									+"</TABLE>"
									+ obnavi.outerHTML;
		}
	}
	else
	{
		window.alert( "�y�[�W�̎擾�Ɏ��s���܂����B");
	}
}

mdObj = null;

//���p���F���A�v���_