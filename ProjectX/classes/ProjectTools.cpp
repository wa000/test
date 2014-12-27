#include "ProjectTools.h"

/**
 * 函数介绍: 设置背景图为合适当前屏幕分辨率的函数
 * 作者：wa000
 * 日期：2014
 */ 
void ProjectTool::setBackgroundScale(Node* node, Vec2 size)
{
	Rect screen = VisibleRect::getVisibleRect();
	node->setScaleX(screen.size.width / size.x);
	node->setScaleY(screen.size.height / size.y);
}

/**
* 函数介绍: 根据分辨率设置精灵大小的函数，fMul：精灵高与屏幕高的比值
*           size:精灵的大小。
* 作者：wa000
* 日期：2014
*/ 
void ProjectTool::setSpriteScale(Node* node, float fMul, Vec2 size)
{
	Rect screen = VisibleRect::getVisibleRect();
	node->setScale(screen.size.height * fMul / size.y);
}