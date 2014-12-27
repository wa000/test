#include "PlayerBoom.h"

/**
* 函数介绍: create
* 作者：wa000
* 日期：2014
*/ 
PlayerBoom* PlayerBoom::create()
{
	PlayerBoom* player = new PlayerBoom();
	if(player && player->init())
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
* 函数介绍: init
* 作者：wa000
* 日期：2014
*/ 
bool PlayerBoom::init()
{
	addAnimation();
	this->initWithSpriteFrameName("boom(400,400)-run-1.png");
	_finished = 0;
	return true;
}

/**
* 函数介绍: addAnimation
* 作者：wa000
* 日期：2014
*/ 
void PlayerBoom::addAnimation()
{
	auto animationRun = AnimationCache::getInstance()->getAnimation("boom-run");

	if(!animationRun)
	{
		Animation* a = Animation::create();
		a->setDelayPerUnit(0.2f);
		a->addSpriteFrame(SpriteFrameCache::getInstance()->getSpriteFrameByName("boom(400,400)-run-1.png"));
		a->addSpriteFrame(SpriteFrameCache::getInstance()->getSpriteFrameByName("boom(400,400)-run-2.png"));
		a->addSpriteFrame(SpriteFrameCache::getInstance()->getSpriteFrameByName("boom(400,400)-run-3.png"));
		AnimationCache::getInstance()->addAnimation(a, "boom-run");
	}

	auto animationBoom = AnimationCache::getInstance()->getAnimation("boom-boom");
	if(!animationBoom)
	{
		Animation* a = Animation::create();
		a->setDelayPerUnit(0.2f);
		a->addSpriteFrame(SpriteFrameCache::getInstance()->getSpriteFrameByName("boom(400,400)-boom-1.png"));
		a->addSpriteFrame(SpriteFrameCache::getInstance()->getSpriteFrameByName("boom(400,400)-boom-2.png"));
		a->addSpriteFrame(SpriteFrameCache::getInstance()->getSpriteFrameByName("boom(400,400)-boom-3.png"));
		AnimationCache::getInstance()->addAnimation(a, "boom-boom");
	}
}

/**
* 函数介绍: playAnimationForever 只有2个动作1001,1002
* 作者：wa000
* 日期：2014
*/ 
void PlayerBoom::playAnimationForever(int index)
{
	auto act = this->getActionByTag(index);
	if(act)
	{
		return;
	}

	this->stopActionByTag(1001);
	this->stopActionByTag(1002);

	if(index == 1001)
	{
		auto animation = AnimationCache::getInstance()->getAnimation("boom-run");
		auto animate = RepeatForever::create(Animate::create(animation));
		animate->setTag(index);
		this->runAction(animate);
	}
	if(index == 1002)
	{
		auto animation = AnimationCache::getInstance()->getAnimation("boom-boom");
		auto animate = RepeatForever::create(Animate::create(animation));
		animate->setTag(index);
		this->runAction(animate);
	}
}

/**
* 函数介绍: walkTo
* 作者：wa000
* 日期：2014
*/ 
void PlayerBoom::walkTo(Vec2 pos)
{
	auto curPos = this->getPosition();
	if(curPos.x > pos.x)
	{
		this->setFlippedX(true);
	}
	auto diff = pos - curPos;
	auto time = diff.getLength() / 100;
	auto move = MoveTo::create(time, pos);
	auto func = [&]
	{
		this->stopActionByTag(1001);
		auto animate = Animate::create(AnimationCache::getInstance()->getAnimation("boom-boom"));
		auto seq1 = Sequence::create(animate, CallFunc::create(CC_CALLBACK_0(PlayerBoom::removeBoom, this)), nullptr);
		this->runAction(seq1);
	};
	auto callback = CallFunc::create(func);
	auto seq = Sequence::create(move, callback, nullptr);
	seq->setTag(1003);
	this->runAction(seq);
}

void PlayerBoom::removeBoom()
{
	_finished = 1;
}