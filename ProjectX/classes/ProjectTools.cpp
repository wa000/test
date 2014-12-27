#include "ProjectTools.h"

/**
 * ��������: ���ñ���ͼΪ���ʵ�ǰ��Ļ�ֱ��ʵĺ���
 * ���ߣ�wa000
 * ���ڣ�2014
 */ 
void ProjectTool::setBackgroundScale(Node* node, Vec2 size)
{
	Rect screen = VisibleRect::getVisibleRect();
	node->setScaleX(screen.size.width / size.x);
	node->setScaleY(screen.size.height / size.y);
}

/**
* ��������: ���ݷֱ������þ����С�ĺ�����fMul�����������Ļ�ߵı�ֵ
*           size:����Ĵ�С��
* ���ߣ�wa000
* ���ڣ�2014
*/ 
void ProjectTool::setSpriteScale(Node* node, float fMul, Vec2 size)
{
	Rect screen = VisibleRect::getVisibleRect();
	node->setScale(screen.size.height * fMul / size.y);
}