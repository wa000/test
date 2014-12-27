#ifndef __PLAYER__
#define __PLAYER__

#include "cocos2d.h"
#include "VisibleRect.h"
USING_NS_CC;

class Player : public Sprite
{
public:
	enum PlayerType
	{
		PLAYER,
		ENEMY1
	};

	enum PlayerAction
	{
		STAND = 0,
		WALKUP,
		WALKRIGHT
	};

	enum EnemyAction
	{
		ENEMYSTAND = 0,
		ENEMYWALKRIGHT,
		ENEMYHIT
	};

	enum Action_Tag
	{
		WALK_TAG = 100
	};

	bool initWithPlayerType(PlayerType type);

	static Player* create(PlayerType type);

	void addAnimation();

	void playAnimationForever(int index);

	void walkTo(Vec2 point);

	int onHit();
	void setOnHit(int i);

private:
	PlayerType _type;
	std::string _name;
	int _animationNum;
	std::vector<int> _animationFrameNum;
	std::vector<std::string> _animationNames;
	EventListenerTouchOneByOne* _listener;
	float _speed;
	//是否正在执行释放技能的动作，如果正在释放技能不能进行其他动作
	int _onHit;
};

#endif
