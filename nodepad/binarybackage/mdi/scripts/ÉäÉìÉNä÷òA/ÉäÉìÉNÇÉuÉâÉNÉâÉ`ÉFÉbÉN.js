//�E�N���b�N�u���N���`�F�b�J�[
//
//���g����
//�E�����N�̏�ŉE�N���b�N������ƁA�����N����u���N���`�F�b�J�[�ɂ����āA�C�����C���t���[���ŕ\�����܂��B
//�E�����N�ł͂Ȃ�������i"ttp://�`"�Ȃǁj�ł�URL�𔽓]�I�����ĉE�N���b�N����Γ����悤�ɓ��삵�܂��B
//�E�V�t�g���b�N����Ă���ꍇ�͉�������܂��B�i�V�t�g���b�N����Ă���ƐV�����^�u���J���Ă��܂�����j
//�E����{�^���������܂ŃE�C���h�E�͏����܂���B
//
//���u���N���`�F�b�J�[��URL�̕ύX
//LinkPouup.js��10�s�ڂ������ύX�B
//�f�t�H���g�ł�"http://www.jah.ne.jp/~fild/cgi-bin/LBCC/lbcc.cgi?url="�ɂȂ��Ă��܂��B
//���ɂ���ƃ����N��̃\�[�X�����邱�Ƃ��ł��܂��B
//var bcChecker = "view-source:";
//
//���|�b�v�A�b�v�E�C���h�E�̑傫���̕ύX
//LinkPopup.js���J���āA6�s�ڂ������ύX���邱�Ƃŕς����܂��B
//"window.document.body.clientWidth" �́A�E�B���h�E�̓����i�\���̈�j�̉����ł��B
//��
//var wsize = 200;
//var hsize = 150;
//
//�����낢��Ȗ��_
//�E�t���[���̂���y�[�W�œ��삵�Ȃ��B
//�E<a href=hoge.htm><b>hoge</b></a>�̂悤�ɒ���a�^�O�ň͂܂�Ă��Ȃ����͓̂��삵�Ȃ��B
//�E<a href=hoge.htm><img src=hoge.png></a>�̂悤�ȉ摜�̃����N�ł����삵�Ȃ��B
//�E���x���̂��������N�ihoge.htm#abc�݂����Ȃ́j���E�N���b�N����ƁA���̃y�[�W���X�N���[������B
//�E��������₱�������ƂɂȂ�B
//�E���̑����낢��B�Ƃ肠�����A���G�����ȃy�[�W�ł͓��삵�Ȃ����Ƃ������ł��B


var Obj = new ActiveXObject("MDIBrowser.API");
var index = Obj.ActiveTabIndex();
var window = Obj.GetWindowObject(index);

//�|�b�v�A�b�v�E�C���h�E�̑傫��
var wsize = window.document.body.clientWidth - 50; //��
var hsize = 250; //����

//�u���N���`�F�b�J�[��URL("view-source:"�ɂ���ƃ\�[�X��\�������邱�Ƃ��ł��܂��B)
var bcChecker = "http://www.jah.ne.jp/~fild/cgi-bin/LBCC/lbcc.cgi?url=";

Obj.ShiftLock(index, false);
if(!window.document.all.tags("frameset").length){
	hoge =  '<SCRIPT for=document event=onmousedown>';
	hoge += 'var hsize=' + hsize + ';';
	hoge += 'var bcChecker="' + bcChecker + '";';
	hoge += 'var rng=document.selection.createRange();';
	hoge += 'var selectedText=rng.text;';
	hoge += 'var url;';
	hoge += 'function rightClick(){return false;}';
	hoge += 'function clear(){}';
	hoge += 'function popup(){';
	hoge += '	document.all.popupWindow.style.display="block";';
	hoge += '	document.oncontextmenu=rightClick;';
	hoge += '	document.ifr.location.href=url;';
	hoge += '	document.all.popupWindow.style.left=20;';
	hoge += '	if(event.clientY > document.body.clientHeight-hsize-20){';
	hoge += '		document.all.popupWindow.style.top=document.body.scrollTop+event.clientY-hsize-30;';
	hoge += '	}else{document.all.popupWindow.style.top=document.body.scrollTop+event.clientY+10;}';
	hoge += '}';
	hoge += 'if(event.srcElement.href){';
	hoge += '	url=bcChecker+event.srcElement.href;';
	hoge += '	popup();';
	hoge += '}else{';
	hoge += '	if(selectedText){';
	hoge += '		url=bcChecker+selectedText;';
	hoge += '		document.execCommand("Unselect");';
	hoge += '		popup();';
	hoge += '	}else{';
	hoge += '		document.oncontextmenu=eval;';
	hoge += '	}';
	hoge += '}';
	hoge += '</SCRIPT>';
	hoge += '<div id=popupWindow style="display:none; top:0px;left:0px; position:absolute;" align=right><input type=button onclick="document.all.popupWindow.style.display=\'none\';" value="���̃E�C���h�E�����" style="width:' + wsize + ';background-color:#99ccff;"><iframe name=ifr src=about:blank style="width:' + wsize + 'px; height:' + hsize + ';"></iframe></div>';
	window.document.body.innerHTML += hoge;
}

//���p���F���A�v���_