#include "Player.h"

/**
* 函数介绍: 创建一个Player类的指针,是参考CREATE_FUNC函数实现的。
* 作者：wa000
* 日期：2014
*/ 
Player* Player::create(PlayerType type)
{
	Player* player = new Player();
	if(player && player->initWithPlayerType(type))
	{
		player->autorelease();
		return player;
	}
	else
	{
		delete player;
		player = NULL;
		return NULL;
	}
}

/**
* 函数介绍: 根据类型进行初始化的方法
* 作者：wa000
* 日期：2014
*/ 
bool Player::initWithPlayerType(PlayerType type)
{
	_speed = VisibleRect::getVisibleRect().size.width / 5;
	_onHit = 0;

	switch (type)
	{
	case Player::PLAYER:
		{
			_name = "Player(60,100)";
			_animationNum = 3;
			//用来拼图片名称的数组
			int animationFrameNum[3] = {1, 2, 4};
			_animationFrameNum.assign(animationFrameNum, animationFrameNum+3);
			std::string animationNames[3] = {"stand","walkup","walkright"};
			_animationNames.assign(animationNames, animationNames+3);
			this->initWithSpriteFrameName("Player(60,100)-stand-1.png");
			break;
		}
	case Player::ENEMY1:
		{
			_name = "enemy(80,140)";
			_animationNum = 3;
			int animationFrameNum[3] = {1, 2, 3};
			_animationFrameNum.assign(animationFrameNum, animationFrameNum+3);
			std::string animationNames[3] = {"stand", "walkright", "hit"};
			_animationNames.assign(animationNames, animationNames+3);
			this->initWithSpriteFrameName("enemy(80,140)-stand-1.png");
			break;
		}
	default:
		break;
	}
	
	this->addAnimation();
	this->playAnimationForever(Player::PlayerAction::STAND);

	return true;
}

/**
* 函数介绍: 将角色动作加入缓存
* 作者：wa000
* 日期：2014
*/ 
void Player::addAnimation()
{
	//判断是否已经加载图片资源
	auto animation = AnimationCache::getInstance()->getAnimation(
		cocos2d::String::createWithFormat("%s-%s", _name.c_str(), _animationNames[0].c_str())->getCString());
	if(animation)
	{
		return;
	}

	//加载图片资源
	for(int i = 0; i < _animationNum; i++)
	{
		auto animation = Animation::create();
		//设置每帧间隔时间
		animation->setDelayPerUnit(0.2f);
		//加载每一帧
		for(int j = 0; j<_animationFrameNum[i]; j++)
		{
			auto sfName = cocos2d::String::createWithFormat("%s-%s-%d.png", _name.c_str(), _animationNames[i].c_str(), j+1)->getCString();
			animation->addSpriteFrame(SpriteFrameCache::getInstance()->getSpriteFrameByName(sfName));
		}
		AnimationCache::getInstance()->addAnimation(
			animation, String::createWithFormat("%s-%s", _name.c_str(), _animationNames[i].c_str())->getCString());
	}
}

/**
* 函数介绍: 执行角色的动作
* 作者：wa000
* 日期：2014
*/ 
void Player::playAnimationForever(int index)
{
	//判断当前是否在执行需要执行的动作，如果正在执行就直接返回。
	auto act = this->getActionByTag(index);
	if(act)
	{
		return;
	}

	//把以前的动作清除
	for(int i = 0; i<_animationNum; i++)
	{
		this->stopActionByTag(i);
	}

	if(index < 0 || index >= _animationNum)
	{
		return;
	}

	auto str = String::createWithFormat("%s-%s", _name.c_str(), _animationNames[index].c_str())->getCString();
	auto animation = AnimationCache::getInstance()->getAnimation(str);
	auto animate = RepeatForever::create(Animate::create(animation));
	animate->setTag(index);
	this->runAction(animate);
}

/**
* 函数介绍: point：需要移动到的坐标
* 作者：wa000
* 日期：2014
*/ 
void Player::walkTo(Vec2 point)
{
	this->stopActionByTag(WALK_TAG);
	auto curPos = this->getPosition();

	//判断要到达的位置在当前位置的左边还是右边，如果是在左边就将精灵翻转，来达到
	//转身的效果。
	if(point.x > curPos.x)
	{
		this->setFlippedX(false);
	}
	else
	{
		this->setFlippedX(true);
	}
	
	auto diff = point - curPos;
	//计算前进时间
	auto time = diff.getLength() / _speed;
	auto move = MoveTo::create(time, point);
	//设置执行完MoveTo动作后的回调函数
	auto func = [&]()
	{
		this->stopActionByTag(WALK_TAG);
		this->playAnimationForever(Player::PlayerAction::STAND);
	};
	auto callback = CallFunc::create(func);
	auto seq = Sequence::create(move, callback, nullptr);
	seq->setTag(WALK_TAG);
	this->runAction(seq);
	//如果要走到的位置为当前位置的上方，就执行向上走的动作。
	if(_name == "Player(60,100)")
	{
		if(std::fabs(curPos.x - point.x) < 100)
		{
			this->playAnimationForever(Player::PlayerAction::WALKUP);
		}
		else
		{
			this->playAnimationForever(Player::PlayerAction::WALKRIGHT);
		}
	}
	if(_name == "enemy(80,140)")
	{
		this->playAnimationForever(Player::EnemyAction::ENEMYWALKRIGHT);
	}
}

int Player::onHit()
{
	return _onHit;
}

void Player::setOnHit(int i)
{
	_onHit = i;
}