/*
  �T���v���X�N���v�g written by TAKETA Kohei 2002.07.09
  
  �T�v�FNavigateNew�ō쐬�����^�u�̃C���f�b�N�X���擾���A
        ���̃h�L�������g�ɒ��ړ��e���������݂܂��B
 */

var obj = new ActiveXObject("MDIBrowser.API");

var nActiveTab = obj.GetActiveTabIndex();
if(nActiveTab != -1)
{
   //�A�N�e�B�u��document�I�u�W�F�N�g��<HTML>�v�f���擾
   var doc = obj.GetDocumentObject(nActiveTab);
   var body = doc.body;
   var html = body.parentElement;

   //�V�����^�u���J���A���̃C���f�b�N�X���擾����
   var newID = obj.NavigateNew("about:blank");
   var nIndex;
   while((nIndex=obj.GetTabIndexByID(newID)) == -1)
   {
      Sleep(500);
   }

   //���̃^�u�̃h�L�������g���e����������
   var newDoc = obj.GetDocumentObject(nIndex);
   newDoc.write(html.outerHTML);
}
