/*
 * �쐬��: 2004/05/30
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
package enclosing.awt;

import java.awt.Container;

/**
 * @author Administrator
 *
 * ���̐������ꂽ�R�����g�̑}�������e���v���[�g��ύX���邽��
 * �E�B���h�E > �ݒ� > Java > �R�[�h���� > �R�[�h�ƃR�����g
 */
public class YesNoCancelDialog extends FlatDialog {
	public YesNoCancelDialog(Container parent){
		super("�ύX������܂����A�ۑ����܂����H",new String[]{"yes","no","cancel"},parent);

	}
}
