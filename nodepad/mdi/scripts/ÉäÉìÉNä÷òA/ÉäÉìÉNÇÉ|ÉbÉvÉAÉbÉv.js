//�����N�|�b�v�A�b�v�i�C���Łj
//
//���C���_
//�E�V�t�g���b�N����Ă���ƁA�V�����^�u���J���Ă��܂��̂ŃV�t�g���b�N����������悤�ɂ����B
//�E<a href=hoge.htm><img src=hoge.png></a>�̂悤�ȉ摜�̃����N���\���ł���悤�ɂ����B
//�E<a href=hoge.htm><b>hoge</b></a>�̂悤�ɁA����a�^�O�ň͂܂�Ė��������N�ł��\���ł���悤�ɂ����B
//�E�|�b�v�A�b�v�E�C���h�E����}�E�X���o�Ă������Ƃ��A�E�C���h�E����邩�ǂ����w��ł���悤�ɂ����B
//�@�i���̃J�X�^�}�C�Y�Q�Ɓj
//
//���g����
//�E�����N�̏�ŉE�N���b�N������ƁA�����N����C�����C���t���[���ŕ\�����܂��B
//�E�V�t�g���b�N����Ă���ꍇ�͉�������܂��B�i�V�t�g���b�N����Ă���ƐV�����^�u���J���Ă��܂�����j
//�E�\�����ꂽ�E�C���h�E�́A��ʏ�̓K���ȏꏊ�i�摜�⃊���N�̖����ꏊ�j���N���b�N����Ə����܂��B
//�E�c���[�^�f���Ȃ񂩂Ŏg���ƕ֗��炵���B
//�EGoogle�̌������ʂȂǂŎg���ƁALYCOS�̃v���r���[�@�\�̂悤�Ȋ����ɂȂ�܂��B
//
//���J�X�^�}�C�Y�i"�����N�|�b�v�A�b�v.js"���������ȂǂŊJ���ĕύX�B�j
//�E�|�b�v�A�b�v�E�C���h�E�̑傫���̕ύX
//�@"window.document.body.clientWidth" �́A�E�B���h�E�̓����i�\���̈�j�̉�����\���Ă��܂��B
//�@��F
//�@var wsize = 200;
//�@var hsize = 150;
//�E�|�b�v�A�b�v�E�C���h�E����}�E�X���o�Ă������Ƃ��A�����ŃE�C���h�E����邩�ǂ����̎w��B
//�@var autoClose = 0;���ƕ��Ȃ��B
//�@var autoClose = 1;���ƕ���B
//
//�����낢��Ȗ��_
//�E�t���[���̂���y�[�W�œ��삵�Ȃ��B
//�E<a href=hoge.htm><b><i>hoge</i></b></a>�̂悤��a�^�O�������ɓ�ȏ�̃^�O������Ɠ��삵�Ȃ��B
//�E���x���̂��������N�ihoge.htm#abc�݂����Ȃ́j���E�N���b�N����ƁA���̃y�[�W���X�N���[������B
//�E��������₱�������ƂɂȂ�B
//�E���̑����낢��B�Ƃ肠�����A���G�����ȃy�[�W�ł͓��삵�Ȃ����Ƃ������ł��B


var Obj = new ActiveXObject("MDIBrowser.API");
var index = Obj.ActiveTabIndex();
var window = Obj.GetWindowObject(index);


//�|�b�v�A�b�v�E�C���h�E�̑傫��
var wsize = window.document.body.clientWidth - 50; //��
var hsize = 220; //����

//�|�b�v�A�b�v�E�C���h�E����}�E�X���o�čs�����Ƃ��A�E�C���h�E�����(0=���Ȃ� / 1=����)
var autoClose = 1;


Obj.ShiftLock(index, false);
if(!window.document.all.tags("frameset").length){
	hoge =  '<SCRIPT for=document event=onmousedown>';
	hoge += 'var hsize=' + hsize + ';';
	hoge += 'function rightClick(){return false;}';
	hoge += 'var url;';
	hoge += 'var autoClose=' + autoClose + ';';
	hoge += 'if(event.srcElement.src || !event.srcElement.href){url=event.srcElement.parentElement.href;}';
	hoge += 'else{url=event.srcElement.href;}';
	hoge += 'if(url){';
	hoge += '	document.all.popupWindow.style.display="block";';
	hoge += '	document.oncontextmenu=rightClick;';
	hoge += '	document.ifr.location.href=url;';
	hoge += '	document.all.popupWindow.style.left=20;';
	hoge += '	if(event.clientY > document.body.clientHeight-hsize-20){';
	hoge += '		document.all.popupWindow.style.top=document.body.scrollTop+event.clientY-hsize-20;';
	hoge += '	}else{document.all.popupWindow.style.top=document.body.scrollTop+event.clientY+10;}';
	hoge += '}else{';
	hoge += '	document.oncontextmenu=eval;';
	hoge += '	document.all.popupWindow.style.display="none";';
	hoge += '	document.ifr.location.href="about:blank";';
	hoge += '}';
	hoge += '</SCRIPT>';
	hoge += '<div id=popupWindow style="display:none; top:0px;left:0px; position:absolute;"';
	if(autoClose){hoge += ' onmouseout="document.oncontextmenu=eval;document.all.popupWindow.style.display=\'none\';document.ifr.location.href=\'about:blank\';"';}
	hoge += '><iframe name=ifr src=about:blank style="width:' + wsize + 'px; height:' + hsize + ';"></iframe></div>';
	window.document.body.innerHTML += hoge;
}

//���p���F���A�v���_