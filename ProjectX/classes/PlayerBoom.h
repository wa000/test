#ifndef Player_Boom
#define Player_Boom

#include "cocos2d.h"
#include "VisibleRect.h"
USING_NS_CC;

class PlayerBoom : public Sprite
{
public:
	bool init();
	static PlayerBoom* create();
	void addAnimation();
	void playAnimationForever(int index);
	void walkTo(Vec2 pos);
	int getFinished(){return _finished;}
	void removeBoom();
private:
	int _finished;
};

#endif
