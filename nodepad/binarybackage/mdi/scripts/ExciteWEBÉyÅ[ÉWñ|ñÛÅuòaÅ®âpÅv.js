//�G�L�T�C�g��WEB�y�[�W�|��𗘗p�������{��y�[�W�̉p��B
//�ϐ�lp��ύX����Ɖp��y�[�W�̘a����o���܂��B

//MDIB �̌��J���� API �I�u�W�F�N�g���쐬
var Obj;
Obj = new ActiveXObject("MDIBrowser.API");

//�A�N�e�B�u�ȃ^�u�̃C���f�b�N�X���擾
var index = Obj.ActiveTabIndex();

if (index != -1){
  //�A�N�e�B�u�ȃ^�u������΁A�C���f�b�N�X������ Window, WebBrowser�I�u�W�F�N�g���쐬
  var window = Obj.GetWindowObject(index);
  var wb = Obj.GetWebBrowserObject(index);
  //�I�v�V�����̐ݒ�
  var lp = 'JAEN'; //�R�R���uENJA�v�ɂ���Ƙa���p�ɁB�f�t�H���g�́u�p���a�v�B
  var dis = 2;     //�R�R��3�ɂ���Ɓu�󕶂ƌ����v�ɁB�f�t�H���g�́u�󕶂̂݁v�B
  //�|��y�[�W��V�����^�u�ɕ\��
  var url = 'http://www.excite.co.jp/world/url/body/?wb_url=' + window.document.location + '&wb_lp=' + lp + '&wb_dis=' + dis + '&wb_co=excitejapan';
  Obj.NavigateNew(url);
}
else{//�A�N�e�B�u�ȃ^�u�������Ƃ��͌x��(�x���_�C�A���O��}�~���Ă��Ă��\������܂�)
  Obj.ShowMessage("�A�N�e�B�u�ȃ^�u������܂���B");
}

//���p���F�X�N���v�g�]�ڃX��