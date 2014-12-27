#ifndef __PROJECTTOOLS__
#define __PROJECTTOOLS__
#include "cocos2d.h"
#include "VisibleRect.h"
USING_NS_CC;

class ProjectTool 
{
public:
	static void setBackgroundScale(Node* node, Vec2 size);
	static void setSpriteScale(Node* node, float fMul, Vec2 size);
};

#endif