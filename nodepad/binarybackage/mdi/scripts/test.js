//MDIBrowser�̌��J����API�I�u�W�F�N�g���쐬
var Obj;
Obj = new ActiveXObject("MDIBrowser.API");

//�A�N�e�B�u�ȃ^�u�̃C���f�b�N�X���擾
var index = Obj.GetActiveTabIndex();

//�A�N�e�B�u�ȃ^�u��window�I�u�W�F�N�g�y��WebBrowser�I�u�W�F�N�g���擾
var window;
var wb;

if (index == -1) {
	//�A�N�e�B�u�ȃ^�u���Ȃ���΁A�V�����E�B���h�E���J��
	Obj.NavigateNew("about:blank");
	do {
		WScript.Sleep(500); //�E�F�C�g
		window = Obj.GetWindowObject(0);
	} while (window == null);
	wb = Obj.GetWebBrowserObject(0);
}
else {
	window = Obj.GetWindowObject(index);
	wb = Obj.GetWebBrowserObject(index);
}

//���݂�URL���o�́i�x���_�C�A���O��}�~���Ă���ƕ\������܂���B�j
window.alert(window.document.location);

//������͗}�~���Ă��Ă��\������܂��B
Obj.ShowMessage(window.document.location);

//Google�Ɉړ����܂��B
wb.Navigate2("http://www.google.co.jp");

